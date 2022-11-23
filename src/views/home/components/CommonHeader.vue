<template>
  <div class="outerDiv">
    <div class="header">
      <span class="title"><router-link to="/home">人事管理系统</router-link></span>
      <span>欢迎您！<span v-if="authority">管理员：</span>{{ name }}</span>
      <el-badge class="item" @click.native="viewMessage" ref="badge" :value="messageValue" :hidden="messageValue === 0">
        <i class="share-button el-icon-bell" style="font-size: 22px; backgroud-color: '#fff'"></i>
      </el-badge>
      <div class="settings">
        <el-dropdown :hide-on-click="false">
          <span class="el-dropdown-link">
            <span class="avatar"><img :src="avatar" :alt="name ? name : '某'" class="avatarImg" /></span>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><router-link to="/userinfo">个人信息</router-link></el-dropdown-item>
            <el-dropdown-item> <label for="load_img" style="font-weight: 400; cursor: pointer">更换头像</label><input id="load_img" type="file" @change="loadImg" style="display: none" /></el-dropdown-item>
            <el-dropdown-item @click.native="editPassword">修改密码</el-dropdown-item>
            <el-dropdown-item @click.native="logout" divided>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <!-- <i class="exit" @click="exitHome">退出</i> -->
      </div>
      <el-dialog title="修改密码" :visible.sync="dialogFormVisible">
        <el-form :model="editPsw" :rules="rules" ref="editPsw" label-width="150px" class="demo-ruleForm">
          <el-form-item label="输入原始密码" prop="old">
            <el-input v-model="editPsw.old" type="password"></el-input>
          </el-form-item>
          <el-form-item label="输入新密码" :label-width="formLabelWidth" prop="new">
            <el-input v-model="editPsw.new" autocomplete="off" type="password"></el-input>
          </el-form-item>
          <el-form-item label="再次确定新密码" :label-width="formLabelWidth" prop="confirm">
            <el-input v-model="editPsw.confirm" autocomplete="off" type="password" @blur="isEquel"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('editPsw')">提交</el-button>
            <el-button @click="resetForm('dynamicValidateForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import store from '@/store'
import { updatePsw, uploadAvatar } from '@/api/user'
import { messageTotal } from '@/api/article'
export default {
  name: 'commonHeader',
  data() {
    const validateConfirm = (rule, value, callback) => {
      if (this.editPsw.confirm !== this.editPsw.new) {
        callback(new Error('确定密码有误！'))
      } else {
        callback()
      }
    }
    return {
      dialogFormVisible: false,
      formLabelWidth: '150px',
      news: this.new,
      authority: 0,
      editPsw: {
        old: '',
        new: '',
        confirm: '',
      },
      rules: {
        old: [
          { required: true, message: '请输入原始密码', trigger: 'blur' },
          { required: true, trigger: 'blur' },
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
    }
  },
  created() {
    this.messageTotal()
    this.authority = store.getters.authority
  },
  mounted() {
    const vm = this
    vm.uid = store.getters.id
    vm.conWebSocket(-1)
    window.onbeforeunload = function (e) {
      vm.socket.close()
    }
  },

  methods: {
    editPassword() {
      this.dialogFormVisible = true
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    changeavatar() {},
    isEquel() {},
    submitForm(editPsw) {
      // console.log(this.$refs)
      const id = store.getters.id
      const data = {
        id: id,
        passwd: this.editPsw.new,
      }
      // console.log(data)
      this.$refs.editPsw.validate((valid) => {
        if (valid) {
          alert('submit!')
          updatePsw(data).then((res) => {
            if (res.data === 1) {
              this.dialogFormVisible = false
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
    loadImg() {
      // const that = this
      if (!this.checkImage($('#load_img')[0].files)) {
        return
      }
      const imgFile = $('#load_img')[0].files[0]
      // 本地预览
      const imgSrc = window.URL.createObjectURL(imgFile)
      // console.log('imgSrc的值为：', imgSrc)
      const reader = new FileReader()
      const that = this
      reader.onload = (function () {
        return function () {
          console.info(this.result) // 这个就是base64的数据了
          // that.$store.commit('saveCover', this.result)
          // console.log('id:', store.getters.id)
          uploadAvatar({ id: store.getters.id, avatar: this.result }).then((res) => {
            if (res === 1) {
              that.$message({
                type: 'success',
                message: '修改成功',
              })
              return $('.avatarImg').attr('src', imgSrc)
            }
            that.$message({
              type: 'warning',
              message: '上传失败，请稍后再尝试',
            })
          })
        }
      })(imgFile)
      reader.readAsDataURL(imgFile)
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
    viewMessage() {
      this.$router.push('/messagelist')
    },
    conWebSocket(noticeid) {
      const vm = this
      if (window.WebSocket) {
        vm.socket = new WebSocket(`ws://localhost:8083/websocket/${vm.uid}`)
        const socket = vm.socket

        // clearInterval(vm.wsTimer)
        // vm.wsTimer = setInterval(() => {
        //   if (vm.socket.readyState === 1) {
        //     vm.socket.send('1')
        //     clearInterval(this.wsTimer)
        //   } else {
        //     console.log('ws建立连接失败')
        //     vm.wsInit()
        //   }
        // }, 3000)
        socket.onopen = function (e) {
          console.log('连接服务器成功')
          vm.$message({ type: 'success', message: '连接服务器成功' })
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
          console.log('服务器通过websocket传过来的数据', message)
          store.commit('user/setUnread', message)
        }
      }
    },
    messageTotal() {
      const id = store.getters.id
      messageTotal(id).then((res) => {
        this.messageValue = res
      })
    },
  },
  computed: {
    avatar() {
      return store.getters.avatar
    },
    name() {
      return store.getters.name
    },
    messageValue: {
      get() {
        console.log('get中的unread：', store.getters.unread)
        return store.getters.unread.length
      },
      set(val) {
        store.commit('user/setUnread', val)
      },
    },
  },
  watch: {
    new(newVal) {
      document.querySelector('.is-dot').style.display = newVal ? 'block' : 'none'
      console.log(document.querySelector('.is-dot'))
      console.log(newVal)
    },
    immediate: true,
  },
}
</script>

<style scoped lang="less">
// .outerDiv{
//   padding:0 10px;
//   width: 100%;
// }
.header {
  background-color: #008a89;
  height: 75px;
  line-height: 75px;
  width: 100%;
  color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: fixed;
  z-index: 1000;
  margin-top: 0;
  padding: 0px;
}

.header .title {
  padding-left: 20px;
  a {
    color: #fff;
  }
}
.header i {
  padding-right: 20px;
  font-style: normal;
  cursor: pointer;
}
.item {
  margin-top: 10px;
  margin-right: -400px;
}
.settings {
  display: flex;
  align-items: center;
  height: 50px;
  width: 50px;
  margin-right: 5px;
  z-index: 10000;
  .el-dropdown {
    width: 100%;
    height: 100%;
    text-align: center;
  }
  .avatar {
    width: 100%;
    height: 100%;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
    }
  }
  img.avatarImg {
    display: inline-block;
    --s: var(--size, 3rem);
    width: var(--s);
    height: var(--s);
    border-radius: 50%;
    white-space: normal;
    overflow: hidden;
  }

  img.avatarImg::before {
    content: attr(alt);
    display: flex;
    font-size: calc(var(--s) / 2);
    width: 100%;
    height: 100%;
    background-color: bisque;
    line-height: var(--s);
    letter-spacing: var(--s);
    text-indent: var(--s);
    justify-content: center;
    text-align: center;
    color: inherit;
    line-break: anywhere;
  }
}
</style>
<style lang="less">
.header .item .el-badge__content {
  right: 26px !important;
  top: 25px !important;
}
</style>
