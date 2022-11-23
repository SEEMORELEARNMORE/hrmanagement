<template>
  <div class="detail">
    <el-card class="box-card">
      <div class="title">{{ data.title }}</div>
      <div class="about">
        <span class="author">作者：{{ data.author }}</span>
        <span>发表时间：{{ data.time }}</span>
      </div>
      <div class="content" v-html="data.body"></div>
    </el-card>
    <div class="box-card2">
      <img src="@/assets/images/notice-1.jpeg" style="height: 70px" alt="" />
      <img src="@/assets/images/notice-3.jpeg" style="height: 193px" alt="" />
      <img src="@/assets/images/notice-2.jpeg" style="height: 158px" alt="" />
      <img src="@/assets/images/notice-5.jpeg" style="height: 144px" alt="" />
    </div>
  </div>
</template>

<script>
import { getById, readArticle } from '@/api/article'
import store from '@/store'
export default {
  name: 'articleDtail',
  data() {
    return {
      data: {},
      number: [1, 2, 3, 4],
    }
  },
  created() {
    this.getInfo()
    this.readArticle()
  },
  methods: {
    getInfo() {
      const id = Number(this.$route.params.id)
      const unreads = store.getters.unread
      let index = -1
      unreads.forEach((item, i) => {
        if (item.notice_id === id) {
          index = i
        }
      })
      if (index !== -1) {
        unreads.splice(index, 1)
        getById(id).then((res) => {
          this.data = res
        })
      }
      store.commit('user/setUnread', unreads)
    },
    readArticle() {
      const obj = {
        id: store.getters.id,
        notice_id: this.$route.params.id,
      }
      readArticle(obj).then((res) => {
        console.log('已读', res)
      })
    },
  },
}
</script>
<style lang="less" scoped>
.detail {
  display: flex;
  justify-content: space-between;

  .box-card {
    width: 720px;
    height: auto;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
    .about {
      text-align: center;
      font-size: 10px;
      margin: 5px;
      span {
        margin: 10px;
      }
    }
  }
  .title {
    text-align: center;
    font-size: 30px;
    font-weight: 560;
  }
  .box-card2 {
    width: 280px;
    img {
      width: 100%;
      margin-bottom: 20px;
      overflow: hidden;
      object-fit: scale-down;
    }
  }
}
</style>
