<template>
  <div class="app-container">
    <div class="collectivity-panel">
      <el-card class="collectivity-card">
        <span>在职情况</span>
        <div class="card-items">
          <div class="card-item">
            <div>正式员工</div>
            <div>23</div>
          </div>
          <div class="card-item">
            <div>实习员工</div>
            <div>23</div>
          </div>
          <div class="card-item">
            <div>总员工数</div>
            <div>23</div>
          </div>
          <div class="card-item">
            <div>离职员工</div>
            <div>23</div>
          </div>
        </div>
      </el-card>
      <el-card class="collectivity-card">
        <span>出勤情况</span>
        <div class="card-items">
          <div class="card-item">
            <div>正常打卡</div>
            <div>243553</div>
          </div>
          <div class="card-item">
            <div>缺到</div>
            <div>243</div>
          </div>
          <div class="card-item">
            <div>迟到</div>
            <div>223</div>
          </div>
          <div class="card-item">
            <div>早退</div>
            <div>23</div>
          </div>
        </div>
      </el-card>
    </div>
    <div class="echarts">
      <Pie></Pie>
      <Barchart></Barchart>
    </div>
  </div>
</template>

<script>
// import MarkdownEditor from '@/components/MarkdownEditor'
import $ from 'jquery'
import Pie from '@/components/echartsComponents/Pie.vue'
import Barchart from '@/components/echartsComponents/Barchart.vue'
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'administrator',
  components: {
    Pie,
    Barchart,
    // MarkdownEditor,
  },
  data() {
    const validateConfirm = (rule, value, callback) => {
      if (this.editPsw.confirm !== this.editPsw.new) {
        callback(new Error('确定密码有误！'))
      } else {
        callback()
      }
    }
    const validateOld = (rule, value, callback) => {
      // console.log(this.$store.state.psw)
      if (this.editPsw.old !== JSON.parse(sessionStorage.getItem('psw'))) {
        callback(new Error('原始密码有误！'))
      } else {
        callback()
      }
    }
    return {
      dialogFormVisible: false,
      dialogAuthVisible: false,
      editPsw: {
        old: '',
        new: '',
        confirm: '',
      },
      auth: {
        id: '',
      },
      formLabelWidth: '150px',
      rules: {
        old: [
          { required: true, message: '请输入原始密码', trigger: 'blur' },
          { required: true, validator: validateOld, trigger: 'blur' },
        ],
        new: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
        ],
        confirm: [
          { required: true, message: '请确定新密码', trigger: 'blur' },
          { required: true, validator: validateConfirm, trigger: 'blur' },
        ],
      },
      userInfo: {},
    }
  },
  created() {
    // console.log('process的值为：', process)
  },
  methods: {
    submitAuth() {
      this.axios({
        url: 'http://localhost:8083/person/updateauth/',
        method: 'post',
        params: {
          id: this.auth.id,
        },
      }).then((res) => {
        if (res.data === 1) {
          this.dialogAuthVisible = false
        }
      })
      this.dialogAuthVisible = false
    },
    editAuth() {
      this.dialogAuthVisible = true
    },
    editPassword() {
      this.dialogFormVisible = true
    },
    isEquel() {
      // if()
    },
    submitForm(editPsw) {
      // console.log(this.$refs)
      const data = {
        id: JSON.parse(sessionStorage.getItem('id')),
        passwd: this.editPsw.new,
      }
      // console.log(data)
      this.$refs.editPsw.validate((valid) => {
        if (valid) {
          alert('submit!')
          // console.log(this.$store.state.id)
          this.axios({
            url: 'http://localhost:8083/person/updatepsw',
            method: 'POST',
            data,
          }).then((res) => {
            if (res.data === 1) {
              this.dialogFormVisible = false
              sessionStorage.setItem('id', JSON.stringify(data.id))
              sessionStorage.setItem('psw', JSON.stringify(data.passwd))
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    // 验证图片格式
    checkImage(files) {
      for (let i = 0; i < files.length; i++) {
        const fileName = files[i].name
        const fileFormat = fileName.split('.')[1].toLowerCase()
        if (!fileFormat.match(/png|jpg|jpeg|gif/)) {
          // console.log('上传的格式需为png/jpg/jpeg/gif')
          return false
        }
      }
      return true
    },
    loadImg() {
      // const that = this
      if (!this.checkImage($('#load_img')[0].files)) {
        return
      }
      const imgFile = $('#load_img')[0].files[0]
      // console.log('files[0]的值为：', $('#load_img')[0].files[0])
      // console.log($())
      // 本地预览
      const imgSrc = window.URL.createObjectURL(imgFile)
      // console.log('imgSrc的值为：', imgSrc)
      $('.preview_img').attr('src', imgSrc).show()
      const reader = new FileReader()
      reader.onload = (function () {
        return function () {
          console.info(this.result) // 这个就是base64的数据了
          // that.$store.commit('saveCover', this.result)
        }
      })(imgFile)
      reader.readAsDataURL(imgFile)
    },
    release() {},
  },
}
</script>

<style scoped lang="less">
.collectivity-panel {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
.echarts {
  display: flex;
  justify-content: space-between;
}
.collectivity-card {
  width: 48%;
  > span {
    font-size: 16px;
  }
}
.card-items {
  display: flex;
  flex-wrap: wrap;
  margin: 10px;
}
.card-item {
  display: flex;
  flex-direction: column;
  flex-grow: 2;
  div {
    font-size: 18px;
    font-weight: 700;
    text-align: center;
  }
  & > div:nth-child(1) {
    font-size: 12px;
    color: #999;
    font-weight: 400;
    margin-bottom: 10px;
  }
}
</style>
