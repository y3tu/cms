<template>
  <div>
    <div v-loading="pageInfo.pageLoading" style="display: flex;flex-wrap: wrap;">

      <div v-for="(item,index) in pageInfo.records"
           :key="index"
           class="excel-view-item"
           @mouseover="item.editable=true"
           @mouseout="item.editable=false">

        <!--缩略图-->
        <div class="thumb">
          <img :src="item.picUrl"/>
        </div>

        <!--底部-->
        <div class="item-footer">
          <span class="item-name">{{ item.bookName }}</span>
          <div style="margin-left: 10%;">
            <a class="opt-show">
              <el-tooltip content="预览报表" placement="top">
                <i @click="preview(item)" class="el-icon-view"
                   style="font-size: 16px"></i>
              </el-tooltip>
            </a>
            <a class="opt-show">
              <el-tooltip content="删除报表" placement="top">
                <i @click="del(item)" class="el-icon-delete"
                   style="font-size: 16px"></i>
              </el-tooltip>
            </a>
            <a class="opt-show">
              <el-tooltip content="报表跳转" placement="top">
                <i @click="goPublish(item)" class="el-icon-s-promotion" style="font-size: 16px"></i>
              </el-tooltip>
            </a>
            <a class="opt-show">
              <el-tooltip content="复制链接" placement="top">
                <i @click="copyLink(item)" class="el-icon-copy-document" style="font-size: 16px"></i>
              </el-tooltip>
            </a>
          </div>
        </div>

      </div>
    </div>

    <el-pagination
        class="page"
        @size-change="sizeChange"
        @current-change="pageChange"
        :current-page="pageInfo.current"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pageInfo.total">
    </el-pagination>
  </div>
</template>

<script>
import {page} from './api'

export default {
  data() {
    return {
      pageInfo: {
        entity: {
          name: ''
        },
        pageLoading: false,
        current: 1,
        total: 0,
        pageSize: 10,
        records: [],
      },
    }
  },
  created() {
    this.$nextTick(() => {
      this.query();
    })
  },
  methods: {
    query() {
      this.pageInfo.pageLoading = true
      page(this.pageInfo).then(res => {
        this.pageInfo.pageLoading = false
        this.pageInfo = res.data;
      }).catch(() => {
        this.pageInfo.pageLoading = false
      })
    },
    reset() {
      this.pageInfo.entity.name = '';
    },
    sizeChange(e) {
      this.pageInfo.current = 1;
      this.pageInfo.size = e;
      this.query()
    },
    pageChange(e) {
      this.pageInfo.current = e;
      this.query()
    },
  }
}
</script>

<style>
.page {
  display: flex;
  justify-content: center;
  -webkit-box-pack: center;
}

.excel-view-item {
  position: relative;
  margin: 16px;
  display: flex;
  flex-direction: column;
  width: 260px;
  height: 170px;
  border: 1px solid #3a4659;
  overflow: hidden;
}

.excel-view-item:hover {
  box-shadow: 0 0 20px 0 #000;
  border: 1px solid #00baff;
}

.excel-view-item .thumb {
  position: relative;
  height: calc(100% - 36px);
}

.excel-view-item .thumb img {
  width: 100%;
  height: 100%
}

.excel-view-item .item-footer {
  font-size: 12px;
  width: 100%;
  height: 36px;
  display: flex;
  align-items: center;
  position: absolute;
  bottom: 0;
  justify-content: space-between;
  background: #1d262e;
  box-sizing: border-box;
  padding: 0 10px;
  color: #bcc9d4;
}

.excel-view-item .item-name {
  width: 100px;
  padding: 0 5px;
  line-height: 28px;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  border: 1px solid transparent;
}

.opt-show {
  color: #bcc9d4;
  margin-right: 10px;
  cursor: pointer;
}
</style>