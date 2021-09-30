<template>
  <el-form method="get" target="_blank" :action="searchData.url" id="search-component">
    <el-form-item>
      <el-input placeholder="请输入搜索内容" :name="searchData.key" v-model="searchText" clearable>
        <template #prepend>
          <el-dropdown
              trigger="click"
              placement="bottom"
              @command="changeData">
                    <span class="el-dropdown-link">
                        <img :src="searchData.icon" alt="scdata.title"/>
                        <i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item
                    v-for="st in searchTypeList"
                    :key="st"
                    :command="st">
                  <img :src="searchList[st].icon" :alt="st"/>
                  {{ searchList[st].title }}
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>

        <template #append>
          <el-button icon="el-icon-search" native-type="submit"></el-button>
        </template>


      </el-input>
    </el-form-item>
  </el-form>
</template>

<script>
import {mapState} from 'vuex'

export default {
  data() {
    return {
      // 从store里面读取数据并使用
      searchType: "",
      searchData: {},
      searchTypeList: [],
      searchText: "",
    };
  },
  created() {
    // 设置默认值
    var last_type = sessionStorage.getItem("last_type");
    var default_type = last_type
        ? last_type
        : this.$store.getters.searchTypes[0];
    this.searchType = default_type;
    this.searchTypeList = this.$store.getters.searchTypes;
    this.searchData = this.$store.state.base.searchList[default_type];
  },

  computed: {
    ...mapState({
      searchList: state => state.base.searchList
    })
  },

  methods: {
    // 选择表单变化的时候同步数据，同时添加到session
    changeData: function (command) {
      this.sctype = command;
      sessionStorage.setItem("last_type", command);
      this.searchData = this.$store.state.base.searchList[command];
    }
  },

};
</script>

<style scoped>
img {
  width: 1.2rem;
  margin: 0 0.2rem -0.2rem 0;
}
</style>
