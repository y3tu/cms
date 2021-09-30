package com.y3tu.cms.book.crawl;

import com.y3tu.cms.book.entity.Book;
import com.y3tu.cms.book.entity.BookContent;
import com.y3tu.cms.book.entity.BookIndex;
import com.y3tu.tools.kit.base.IdUtil;
import com.y3tu.tools.kit.concurrent.thread.ThreadUtil;
import com.y3tu.tools.kit.regex.PatternPool;
import com.y3tu.tools.kit.text.StrUtil;
import com.y3tu.tools.kit.time.DateUtil;
import com.y3tu.tools.web.http.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 爬虫解析器
 *
 * @author y3tu
 */
@Slf4j
public class CrawlParser {

    /**
     * 爬取小说http请求中无效的内容长度
     */
    public static final int INVALID_HTML_LENGTH = 1500;

    /**
     * 爬取小说http请求失败重试次数
     */
    public static final Integer HTTP_FAIL_RETRY_COUNT = 5;

    /**
     * 本地图片保存前缀
     */
    public static final String LOCAL_PIC_PREFIX = "/localPic/";

    /**
     * 重试次数
     */
    private static final ThreadLocal<Integer> retryCount = new ThreadLocal<>();


    /**
     * 解析小说基本信息
     *
     * @param crawlBookId 源站小说ID
     * @param ruleBean    规则
     * @param handler     处理
     */
    public static void parseBook(String crawlBookId, RuleBean ruleBean, CrawlBookHandler handler) {
        Book book = new Book();
        //小说详情页面url
        String bookDetailUrl = ruleBean.getBookDetailUrl().replace("{bookId}", crawlBookId);
        //获取详情页面内容
        String bookDetailHtml = getHtmlContent(bookDetailUrl);
        if (StrUtil.isNotEmpty(bookDetailHtml)) {
            //小说名正则
            Pattern bookNamePatten = compile(ruleBean.getBookNamePatten());
            Matcher bookNameMatch = bookNamePatten.matcher(bookDetailHtml);
            boolean isFindBookName = bookNameMatch.find();
            if (isFindBookName) {
                String bookName = bookNameMatch.group(1);
                //设置小说名
                book.setBookName(bookName);
                Pattern authorNamePatten = compile(ruleBean.getAuthorNamePatten());
                Matcher authorNameMatch = authorNamePatten.matcher(bookDetailHtml);
                boolean isFindAuthorName = authorNameMatch.find();
                if (isFindAuthorName) {
                    String authorName = authorNameMatch.group(1);
                    //设置作者名
                    book.setAuthorName(authorName);
                    //小说封面
                    if (StrUtil.isNotBlank(ruleBean.getPicUrlPatten())) {
                        //如果配置的封面url正则
                        Pattern picUrlPatten = compile(ruleBean.getPicUrlPatten());
                        Matcher picUrlMatch = picUrlPatten.matcher(bookDetailHtml);
                        boolean isFindPicUrl = picUrlMatch.find();
                        if (isFindPicUrl) {
                            String picUrl = picUrlMatch.group(1);
                            if (StrUtil.isNotBlank(picUrl) && StrUtil.isNotBlank(ruleBean.getPicUrlPrefix())) {
                                picUrl = ruleBean.getPicUrlPrefix() + picUrl;
                            }
                            //设置封面图片路径
                            book.setPicUrl(picUrl);
                        }
                    }

                    //小说评分
                    if (StrUtil.isNotBlank(ruleBean.getScorePatten())) {
                        Pattern scorePatten = compile(ruleBean.getScorePatten());
                        Matcher scoreMatch = scorePatten.matcher(bookDetailHtml);
                        boolean isFindScore = scoreMatch.find();
                        if (isFindScore) {
                            String score = scoreMatch.group(1);
                            //设置评分
                            book.setScore(Double.parseDouble(score));
                        }
                    }

                    //小说访问次数
                    if (StrUtil.isNotBlank(ruleBean.getVisitCountPatten())) {
                        Pattern visitCountPatten = compile(ruleBean.getVisitCountPatten());
                        Matcher visitCountMatch = visitCountPatten.matcher(bookDetailHtml);
                        boolean isFindVisitCount = visitCountMatch.find();
                        if (isFindVisitCount) {
                            String visitCount = visitCountMatch.group(1);
                            //设置访问次数
                            book.setVisitCount(Long.parseLong(visitCount));
                        }
                    }
                    //小说简介
                    String desc = bookDetailHtml.substring(bookDetailHtml.indexOf(ruleBean.getDescStart()) + ruleBean.getDescStart().length());
                    desc = desc.substring(0, desc.indexOf(ruleBean.getDescEnd()));
                    //过滤掉简介中的特殊标签
                    desc = desc.replaceAll("<a[^<]+</a>", "")
                            .replaceAll("<font[^<]+</font>", "")
                            .replaceAll("<p>\\s*</p>", "")
                            .replaceAll("<p>", "")
                            .replaceAll("</p>", "<br/>");
                    //设置书籍简介
                    book.setBookDesc(desc);

                    //小说更新状态
                    if (StrUtil.isNotBlank(ruleBean.getStatusPatten())) {
                        Pattern bookStatusPatten = compile(ruleBean.getStatusPatten());
                        Matcher bookStatusMatch = bookStatusPatten.matcher(bookDetailHtml);
                        boolean isFindBookStatus = bookStatusMatch.find();
                        if (isFindBookStatus) {
                            String bookStatus = bookStatusMatch.group(1);
                            if (ruleBean.getBookStatusRule().get(bookStatus) != null) {
                                //设置更新状态
                                book.setBookStatus(ruleBean.getBookStatusRule().get(bookStatus));
                            }
                        }
                    }

                    //小说更新时间
                    if (StrUtil.isNotBlank(ruleBean.getUpdateTimePatten()) && StrUtil.isNotBlank(ruleBean.getUpdateTimeFormatPatten())) {
                        Pattern updateTimePatten = compile(ruleBean.getUpdateTimePatten());
                        Matcher updateTimeMatch = updateTimePatten.matcher(bookDetailHtml);
                        boolean isFindUpdateTime = updateTimeMatch.find();
                        if (isFindUpdateTime) {
                            String updateTime = updateTimeMatch.group(1);
                            //设置更新时间
                            book.setLastIndexUpdateTime(DateUtil.strToDate(updateTime, ruleBean.getUpdateTimeFormatPatten()));
                        }
                    }

                }

                if (book.getVisitCount() == null) {
                    book.setVisitCount(0L);
                }
                if (book.getScore() == null) {
                    book.setScore(0d);
                }
            }
        }
        handler.handle(book);
    }

    /**
     * 解析章节信息
     *
     * @param crawlBookId       源站小说ID
     * @param book              小说
     * @param ruleBean          规则
     * @param existBookIndexMap 已经存在的目录信息
     * @param handler           章节处理
     */
    public static void parseBookIndexAndContent(String crawlBookId, Book book, RuleBean ruleBean, Map<Integer, BookIndex> existBookIndexMap, CrawlBookChapterHandler handler) {
        Date currentDate = new Date();
        //目录
        List<BookIndex> indexList = new ArrayList<>();
        //小说内容
        List<BookContent> contentList = new ArrayList<>();

        //读取目录
        String indexListUrl = ruleBean.getBookIndexUrl().replace("{bookId}", crawlBookId);
        String indexListHtml = getHtmlContent(indexListUrl);

        if (StrUtil.isNotEmpty(indexListHtml)) {
            if (StrUtil.isNotBlank(ruleBean.getBookIndexStart())) {
                indexListHtml = indexListHtml.substring(indexListHtml.indexOf(ruleBean.getBookIndexStart()) + ruleBean.getBookIndexStart().length());
            }

            Pattern indexIdPatten = compile(ruleBean.getIndexIdPatten());
            Matcher indexIdMatch = indexIdPatten.matcher(indexListHtml);
            Pattern indexNamePatten = compile(ruleBean.getIndexNamePatten());
            Matcher indexNameMatch = indexNamePatten.matcher(indexListHtml);

            boolean isFindIndex = indexIdMatch.find() & indexNameMatch.find();
            int indexNum = 0;
            //总字数
            int totalWordCount = book.getWordCount() == null ? 0 : book.getWordCount();

            while (isFindIndex) {
                //已经存在的目录
                BookIndex hasIndex = existBookIndexMap.get(indexNum);
                String indexName = indexNameMatch.group(1);
                if (hasIndex == null || !StrUtil.cleanBlank(hasIndex.getIndexName()).equals(StrUtil.cleanBlank(indexName))) {
                    //新的目录
                    String sourceIndexId = indexIdMatch.group(1);
                    String bookContentUrl = ruleBean.getBookContentUrl();
                    //分类
                    int calStart = bookContentUrl.indexOf("{cal_");
                    if (calStart != -1) {
                        //存在分类
                        String calStr = bookContentUrl.substring(calStart, calStart + bookContentUrl.substring(calStart).indexOf("}"));
                        String[] calArr = calStr.split("_");
                        int calType = Integer.parseInt(calArr[1]);
                        if (calType == 1) {
                            ///{cal_1_1_3}_{bookId}/{indexId}.html
                            //第一种计算规则，去除第x个参数的最后y个字母
                            int x = Integer.parseInt(calArr[2]);
                            int y = Integer.parseInt(calArr[3]);
                            String calResult;
                            if (x == 1) {
                                calResult = crawlBookId.substring(0, crawlBookId.length() - y);
                            } else {
                                calResult = sourceIndexId.substring(0, crawlBookId.length() - y);
                            }

                            if (calResult.length() == 0) {
                                calResult = "0";

                            }
                            bookContentUrl = bookContentUrl.replace(calStr + "}", calResult);
                        }

                    }

                    String contentUrl = bookContentUrl.replace("{bookId}", crawlBookId).replace("{indexId}", sourceIndexId);
                    //查询章节内容
                    String contentHtml = getHtmlContent(contentUrl);
                    if (contentHtml != null && !contentHtml.contains("正在手打中")) {
                        //小说有内容
                        String content = "";
                        log.info("获取小说中... 小说名:{}，章节:{}", book.getBookName(), indexName);
                        try {
                            content = contentHtml.substring(contentHtml.indexOf(ruleBean.getContentStart()) + ruleBean.getContentStart().length());
                            content = content.substring(0, content.indexOf(ruleBean.getContentEnd()));
                        } catch (Exception e) {
                            log.warn("解析html内容出错！小说名:{},bookId:{},网页地址：{}", book.getBookName(), book.getId(), contentUrl);
                        }
                        //插入章节目录和章节内容
                        BookIndex bookIndex = new BookIndex();
                        bookIndex.setIndexName(indexName);
                        bookIndex.setIndexNum(indexNum);
                        int wordCount = getStrValidWordCount(content);

                        bookIndex.setWordCount(wordCount);
                        indexList.add(bookIndex);

                        BookContent bookContent = new BookContent();
                        bookContent.setContent(content);
                        contentList.add(bookContent);

                        if (hasIndex != null) {
                            //章节更新
                            bookIndex.setId(hasIndex.getId());
                            bookContent.setIndexId(hasIndex.getId());

                            //计算总字数
                            totalWordCount = (totalWordCount + wordCount - hasIndex.getWordCount());
                        } else {
                            //章节插入
                            //设置目录和章节内容
                            Long indexId = IdUtil.nextId();
                            bookIndex.setId(indexId);
                            bookIndex.setBookId(book.getId());

                            bookIndex.setCreateTime(currentDate);

                            bookContent.setIndexId(indexId);

                            //计算总字数
                            totalWordCount += wordCount;
                        }
                        bookIndex.setUpdateTime(currentDate);
                    }
                }
                indexNum++;
                isFindIndex = indexIdMatch.find() & indexNameMatch.find();
            }

            if (indexList.size() > 0) {
                //如果有爬到最新章节，则设置小说主表的最新章节信息
                //获取爬取到的最新章节
                BookIndex lastIndex = indexList.get(indexList.size() - 1);
                book.setLastIndexId(lastIndex.getId());
                book.setLastIndexName(lastIndex.getIndexName());
                book.setLastIndexUpdateTime(currentDate);

            }

            book.setWordCount(totalWordCount);
            book.setUpdateTime(currentDate);


            if (indexList.size() == contentList.size() && indexList.size() > 0) {
                handler.handle(new ChapterBean() {{
                    setBookIndexList(indexList);
                    setBookContentList(contentList);
                }});
                return;
            }
        }

        handler.handle(new ChapterBean() {{
            setBookIndexList(new ArrayList<>(0));
            setBookContentList(new ArrayList<>(0));
        }});
    }

    /**
     * 获取网络内容
     *
     * @param url
     * @return
     */
    public static String getHtmlContent(String url) {
        //解决中文乱码问题
        ClientHttpRequestFactory clientHttpRequestFactory = RestTemplateUtil.getOkHttpFactory(10000, 10000, 10000, 100, 100000);
        ResponseEntity<String> responseEntity = RestTemplateUtil.getInstance(clientHttpRequestFactory, "utf-8").get(url, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();
            assert body != null;
            if (body.length() < INVALID_HTML_LENGTH) {
                //如果返回的内容为无效内容
                return retry(url);
            }
            return body;
        } else {
            return StrUtil.EMPTY;
        }
    }

    /**
     * 重试
     *
     * @param url
     * @return
     */
    private static String retry(String url) {
        Integer count = retryCount.get();
        if (count == null) {
            count = 0;
        }
        if (count < HTTP_FAIL_RETRY_COUNT) {
            ThreadUtil.sleep(new Random().nextInt(10 * 1000));
            retryCount.set(++count);
            return getHtmlContent(url);
        }
        return null;
    }

    /**
     * 获取字符串有效字数
     *
     * @param str 字符串
     */
    private static int getStrValidWordCount(String str) {
        return StrUtil.getPatternMatchCount(str, PatternPool.CHINESE) +
                StrUtil.getPatternMatchCount(str, PatternPool.NUMBERS) +
                StrUtil.getPatternMatchCount(str, PatternPool.WORD);
    }
}
