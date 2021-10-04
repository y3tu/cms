<template>
  <div>
    <el-row type="flex" class="row-bg" justify="center">
      <el-col :span="10" id="col-search">
        <component-search></component-search>
      </el-col>
    </el-row>

    <el-row type="flex" class="row-bg" justify="center" id="row-linkbox">
      <el-col :span="16">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="导航" name="navigation">
            <component-weblink></component-weblink>
          </el-tab-pane>
          <el-tab-pane label="小说" name="book">
            <Book/>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>

  </div>
</template>

<script>

import Search from "@/components/search";
import Weblink from "@/components/weblink"
import Book from '../book'

export default {
  name: 'home',
  components: {
    // 绑定组件标签
    "component-search": Search,
    "component-weblink": Weblink,
    "Book": Book
  },
  data() {
    return {
      activeName: 'navigation',
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event)
    },
  },
};

</script>

<style>
#col-search {
  margin-top: 5%;
}
</style>