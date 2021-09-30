package com.y3tu.cms.book.runner;

import com.y3tu.cms.book.crawl.CrawlParser;
import com.y3tu.cms.book.crawl.RuleBean;
import com.y3tu.cms.book.entity.Book;
import com.y3tu.cms.book.entity.BookCrawlSingleTask;
import com.y3tu.cms.book.entity.BookCrawlSource;
import com.y3tu.cms.book.entity.BookIndex;
import com.y3tu.cms.book.service.BookCrawlSingleTaskService;
import com.y3tu.cms.book.service.BookCrawlSourceService;
import com.y3tu.cms.book.service.BookIndexService;
import com.y3tu.cms.book.service.BookService;
import com.y3tu.tools.kit.base.JsonUtil;
import com.y3tu.tools.kit.concurrent.thread.ThreadUtil;
import com.y3tu.tools.kit.time.DateUnit;
import com.y3tu.tools.kit.time.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 程序启动后执行常驻线程
 *
 * @author y3tu
 */
@Component
@Slf4j
public class BookRunner implements ApplicationRunner {

    @Autowired
    BookService bookService;

    @Autowired
    BookIndexService bookIndexService;

    @Autowired
    BookCrawlSourceService bookCrawlSourceService;

    @Autowired
    BookCrawlSingleTaskService bookCrawlSingleTaskService;

    @Value("${cms.book.threadCount:10}")
    private int threadCount;

    @Override
    public void run(ApplicationArguments args) {

        ExecutorService executorService = ThreadUtil.newFixedExecutor(threadCount, ThreadUtil.newNamedThreadFactory("小说后台线程池", true));

        Thread updateThread = ThreadUtil.newThread(() -> {
            log.info("程序启动,开始执行自动更新线程...");

            while (true) {
                //查询最新目录更新时间在一个月之内的前10条需要更新的数据
                //时间偏移上一个月
                Date startDate = DateUtil.offset(new Date(), -1, DateUnit.MONTH);
                List<Book> bookList;
                synchronized (this) {
                    bookList = bookService.queryNeedUpdateBook(startDate, 10);
                    if (bookList.size() > 0) {
                        //更新抓取时间为当前时间
                        bookService.updateCrawlLastTime(bookList, new Date());
                    }
                }

                for (Book needUpdateBook : bookList) {
                    //获取小说的爬虫源
                    BookCrawlSource bookCrawlSource = bookCrawlSourceService.getById(needUpdateBook.getCrawlSourceId());
                    RuleBean ruleBean = JsonUtil.parseObject(bookCrawlSource.getCrawlRule(), RuleBean.class);
                    //解析小说基本信息
                    log.info("小说【{}】开始更新", needUpdateBook.getBookName());
                    try {
                        CrawlParser.parseBook(needUpdateBook.getCrawlBookId(), ruleBean, book -> {
                            //这里只做老书更新
                            book.setId(needUpdateBook.getId());
                            book.setWordCount(needUpdateBook.getWordCount());
                            if (needUpdateBook.getPicUrl() != null && needUpdateBook.getPicUrl().contains(CrawlParser.LOCAL_PIC_PREFIX)) {
                                //本地图片则不更新
                                book.setPicUrl(null);
                            }
                            //查询已存在的章节
                            Map<Integer, BookIndex> existBookIndexMap = bookIndexService.queryExistBookIndexMap(needUpdateBook.getId());
                            //解析章节目录
                            CrawlParser.parseBookIndexAndContent(needUpdateBook.getCrawlBookId(), book, ruleBean, existBookIndexMap, chapter -> {
                                bookService.updateBookAndIndexAndContent(book, chapter.getBookIndexList(), chapter.getBookContentList(), existBookIndexMap);
                            });
                            log.info("小说【{}】更新完成", needUpdateBook.getBookName());
                        });
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        log.info("小说【{}】更新异常，{}", needUpdateBook.getBookName(), e.getMessage());
                    }

                }

                log.info("更新线程开始休眠，预计10分钟...");
                //休眠10分钟
                ThreadUtil.sleep(10 * DateUnit.MINUTE.getMillis());
            }
        }, "小说更新线程");


        Thread collectionThread = ThreadUtil.newThread(() -> {
            log.info("程序启动,开始执行单本采集任务线程...");

            while (true) {
                List<BookCrawlSingleTask> taskList = null;
                try {
                    //获取采集任务 按照时间顺序排序状态不是成功并且执行次数没达到5次的任务才执行
                    taskList = bookCrawlSingleTaskService.lambdaQuery()
                            .notIn(BookCrawlSingleTask::getTaskStatus, BookCrawlSingleTask.TASK_STATUS_SUCCESS)
                            .lt(BookCrawlSingleTask::getExcCount, 5)
                            .orderByAsc(BookCrawlSingleTask::getCreateTime)
                            .list();

                    if (taskList.size() > 0) {
                        //查询爬虫规则
                        BookCrawlSingleTask task = taskList.get(0);
                        log.info("单本采集任务开始采集，小说名:{}", task.getBookName());
                        BookCrawlSource source = bookCrawlSourceService.getById(task.getSourceId());
                        RuleBean ruleBean = JsonUtil.parseObject(source.getCrawlRule(), RuleBean.class);

                        if (bookCrawlSingleTaskService.parseBookAndSave(task.getSourceId(), task.getSourceBookId(), task.getCatId(), ruleBean)) {
                            //采集成功
                            log.info("单本采集任务采集成功，小说名:{}", task.getBookName());
                            task.setTaskStatus(BookCrawlSingleTask.TASK_STATUS_SUCCESS);
                            task.setExcCount(1);
                        } else {
                            log.info("单本采集任务采集失败，小说名:{}", task.getBookName());
                            task.setErrorMsg("采集失败！");
                            task.setTaskStatus(BookCrawlSingleTask.TASK_STATUS_ERROR);
                            //调用次数+1
                            task.setExcCount(task.getExcCount() + 1);
                        }
                        bookCrawlSingleTaskService.updateById(task);
                    }

                    log.info("单本采集任务线程开始休眠，预计1分钟...");
                    //休眠1分钟
                    ThreadUtil.sleep(DateUnit.MINUTE.getMillis());

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    if (taskList.size() > 0) {
                        BookCrawlSingleTask task = taskList.get(0);
                        log.info("单本采集任务采集失败，小说名{}", task.getBookName());
                        task.setErrorMsg(e.getMessage());
                        task.setTaskStatus(BookCrawlSingleTask.TASK_STATUS_ERROR);
                        //调用次数+1
                        task.setExcCount(task.getExcCount() + 1);
                        bookCrawlSingleTaskService.updateById(task);
                    }
                }
            }
        }, "单本采集任务线程");

        executorService.execute(updateThread);
        executorService.execute(collectionThread);

    }

}
