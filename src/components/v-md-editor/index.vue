<template>
  <div id="editorStyle">
    <v-md-editor v-model="editorValue" :disabled-menus="[]" @upload-image="handleUploadImage" height="calc(100vh - 150px)"></v-md-editor>
  </div>
</template>

<script>
import { getImgUrl } from '@/api/article.js'
// import qs from 'qs'
export default {
  name: 'mdEditor',
  data() {
    return {
      editorValue: this.content,
    }
  },
  props: {
    // 接收值父组件传递值
    content: String,
  },
  methods: {
    // v-md-editor 文件上传
    handleUploadImage(event, insertImage, files) {
      // console.log('files[0]:', files[0])
      // 上传
      const formData = new FormData()
      formData.append('file', files[0])
      getImgUrl(formData).then((res) => {
        // 获取返回数据
        // console.log(res)
        insertImage({
          url: res,
          desc: files[0].name,
        })
      })
    },
  },
  watch: {
    editorValue: function (newNum, oldNum) {
      // 修改调用者传入的值
      this.$emit('updateContent', newNum)
    },
  },
}
</script>

<style>
#editorStyle {
  border: 1px solid #ccc;
}
</style>
