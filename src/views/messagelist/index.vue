<template>
  <div class="messageList">
    <el-card class="box-card" v-for="item in list" :key="item.id">
      <router-link :to="'/notice/detail/' + item.id" class="link-type">
        <div class="card">
          <el-badge is-dot class="item" ref="dot" :hidden="item.state || item.author_id === id">
            <i class="share-button el-icon-chat-dot-round" style="font-size: 27px; backgroud-color: '#fff'"></i>
          </el-badge>
          <div class="articleinfo">
            <div class="author">
              <span style="color: #4e5969">{{ item.author }}</span>
              <span style="color: #ccc">|</span>
              <span style="color: #86909c">{{ item.time }}</span>
            </div>
            <h3>{{ item.title }}</h3>
            <p v-html="item.body"></p>
          </div>
        </div>
      </router-link>
    </el-card>
  </div>
</template>

<script>
import { getListWithState } from '@/api/article'
import store from '@/store'
export default {
  name: 'messageList',
  data() {
    return {
      list: [],
    }
  },
  computed: {
    id() {
      return store.getters.id
    },
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      const id = store.getters.id
      getListWithState(id).then((res) => {
        this.list = res
        console.log('消息列表：', res)
      })
    },
  },
}
</script>

<style scoped lang="less">
.messageList {
  margin-top: 30px;
  width: 960px;
  .link-type:hover {
    color: currentColor;
  }
  .el-card {
    margin-bottom: 20px;
    height: 145px;
    .card {
      display: flex !important;
      .item {
        margin-right: 20px;
      }
    }
  }
  .articleinfo {
    width: 100%;
    height: 100%;
    .author span {
      margin-right: 5px;
    }
    h3 {
      margin: 10px 0;
    }
    p {
      font-size: 13px;
      color: #86909c;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 3;
    }
  }
}
</style>
