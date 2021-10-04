<template>
  <el-card shadow="hover">
    <el-row
        :draggable="true"
        @dragover="dragover($event)"
        @dragenter="dragenterCategory(key,$event)"
        @dragend="dragendCategory(key)"
        :gutter="18"
        v-for="(value, key) in websiteMap" v-bind:key="key">
      <div class="panel-title">{{ key }}</div>
      <el-col
          :span="4"
          v-for="item in value" v-bind:key="item.id"
          :draggable="true"
          @dragstart="dragstart(key,item)"
          @dragenter="dragenter(key,item,$event)"
          @dragend="dragend(key)"
          @dragover="dragover($event)">
        <a :href="item.url" target="_blank">
          <img :src="item.iconUrl" :alt="item.description"/>
          <span>{{ item.name }}</span>
        </a>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import {service} from '@/plugin/axios'

export default {
  name: 'website',
  data() {
    return {
      websiteMap: {},
      // 开始排序时按住的旧数据
      oldData: {},
      // 拖拽过程的数据
      newData: {},
      oldKey: {},
      newKey: {}
    }
  },
  created() {
    service({
      method: 'get',
      url: 'weblink/getAllByCategory',
    }).then(res => {
      this.websiteMap = res.data;
    });
  },

  methods: {
    dragstart(key, item) {
      this.oldKey = key;
      this.oldData = item;
    },
    dragenter(key, item, e) {
      this.newKey = key;
      this.newData = item;
      e.preventDefault();
    },
    dragenterCategory(key, e) {
      this.newKey = key;
      e.preventDefault();
    },
    dragendCategory() {

    },
    dragover(e) {
      e.preventDefault()
    },
    dragend(key) {
      console.log("oldKey:" + this.oldKey + " newKey:" + this.newKey)
      console.log("oldData:" + this.oldData.name + " newData:" + this.newData.name)
      if (this.oldKey !== this.newKey) {
        //拖拽到不同的分类
        let oldIndex = this.websiteMap[this.oldKey].indexOf(this.oldData)
        let oldItems = [...this.websiteMap[this.oldKey]]
        // 删除老的节点
        oldItems.splice(oldIndex, 1)
        this.websiteMap[this.oldKey] = [...oldItems]

        let newIndex = this.websiteMap[this.newKey].indexOf(this.newData)
        let newItems = [...this.websiteMap[this.newKey]]
        //在列表中目标位置增加新的节点
        console.log("newIndex:" + newIndex + " length:" + newItems.length);
        if (newIndex === newItems.length - 1) {
          //如果拖动到集合的最后一个数据上，则排在最后
          newItems.splice(newIndex + 1, 0, this.oldData)
        } else {
          //如果拖动到集合的第一个数据上，则排在最前
          newItems.splice(newIndex, 0, this.oldData)
        }
        this.websiteMap[this.newKey] = [...newItems]
      } else {
        //拖拽到相同的分类
        let oldIndex = this.websiteMap[key].indexOf(this.oldData)
        let newIndex = this.websiteMap[key].indexOf(this.newData)
        let newItems = [...this.websiteMap[key]]
        // 删除老的节点
        newItems.splice(oldIndex, 1)
        // 在列表中目标位置增加新的节点
        newItems.splice(newIndex, 0, this.oldData)
        this.websiteMap[key] = [...newItems]
      }
      //保存排列数据
      service({
        method: 'post',
        url: 'weblink/saveWeblinkSort',
        data: this.websiteMap
      }).then(res => {
        console.log(res)
      });
    }
  },
  components: {}
}
</script>
<style scoped>

.panel-title {
  margin-top: 8px
}

.el-row {
  display: flex;
  flex-wrap: wrap;
}

a {
  display: block;
  padding: 0.5rem 0 0.5rem 1rem;
  margin-bottom: 1rem;
  color: #444d58;
}

a:-webkit-any-link {
  color: none;
  cursor: pointer;
  text-decoration: none;
}

a:hover {
  box-shadow: 0 2px 6px rgba(92, 88, 88, 0.175);
}

a,
a:focus,
a:hover {
  text-decoration: none;
}

img {
  width: 1.2rem;
  margin: 0 0.2rem -0.1rem 0;
}
</style>
