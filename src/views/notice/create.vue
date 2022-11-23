<template>
  <div>
    <div class="articleTitle">
      <label for="title">请输入文章标题:</label>
      <input v-model="form.title" id="title" />
      <el-button type="primary" @click="handlePublish">发布</el-button>
    </div>
    <md-editor :content="form.article" @updateContent="getContent"></md-editor>
  </div>
</template>

<script>
import store from '@/store'
import VueMarkdownEditor, { xss } from '@kangc/v-md-editor'
import { postArticle } from '@/api/article'
export default {
  name: 'create',
  data() {
    return {
      form: {
        title: '',
        article: '',
      },
    }
  },
  mounted() {
    const vm = this
    vm.uid = store.getters.id
    vm.conWebSocket(-1)
  },
  methods: {
    handlePublish() {
      if (this.form.title.trim() === '') {
        return this.$message({
          message: '标题不能为空',
          type: 'warning',
        })
      }
      this.form.article = xss.process(VueMarkdownEditor.themeConfig.markdownParser.render(this.form.article))
      // console.log(this.form.article)
      const vm = this
      // 发送请求
      postArticle({ title: this.form.title, body: this.form.article, author: store.getters.name, author_id: store.getters.id, time: this.getDate(new Date()) }).then((res) => {
        const noticeid = res
        vm.uid = store.getters.id
        vm.conWebSocket(noticeid)
      })

      this.$message({
        message: '发布成功',
        type: 'success',
      })
    },
    getContent(val) {
      this.form.article = val
    },
    getDate(date) {
      const pattern = 'yyyy-MM-dd hh:mm:ss'
      date = new Date(date).format(pattern)
      return date
    },
    conWebSocket(noticeid) {
      const vm = this
      if (window.WebSocket) {
        vm.socket = new WebSocket(`ws://localhost:8083/websocket/${vm.uid}`)
        const socket = vm.socket

        socket.onopen = function (e) {
          console.log('连接服务器成功')
          vm.$message({ type: 'success', message: '连接服务器成功' })
          if (vm.uid && noticeid !== -1) {
            vm.socket.send(noticeid)
          }
        }
        socket.onclose = function (e) {
          console.log('服务器关闭')
        }
        socket.onerror = function () {
          console.log('连接出错')
        }
        // 接收服务器的消息
        socket.onmessage = function (e) {
          const message = JSON.parse(e.data)
          console.log(message)
        }
      }
    },
  },
}
</script>

<style lang="less" scoped>
.articleTitle {
  font-size: 20px;
  // margin: 30px 0;
  margin-bottom: 20px;
  input {
    width: 400px;
    height: 40px;
    margin-left: 20px;
    background: none;
    outline: none;
    border: 1px solid #ccc;
    padding-left: 8px;
    border-radius: 8px;
    font-size: 20px;
    font-weight: 500;
    vertical-align: middle;
  }
  button {
    float: right;
  }
}
</style>
