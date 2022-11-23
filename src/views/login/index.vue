<template>
  <section class="forms-section">
    <h1 class="section-title">{{ $t('login.title') }}</h1>
    <lang-select class="set-language" />
    <div class="forms">
      <div class="form-wrapper is-active">
        <button type="button" class="switcher switcher-login" @click="switchState()">
          {{ $t('login.login') }}
          <span class="underline"></span>
        </button>
        <form class="form form-login">
          <fieldset>
            <div class="input-block">
              <label for="login-email">{{ $t('login.account') }}</label>
              <input id="login-email" required v-model="loginForm.id" />
            </div>
            <div class="input-block">
              <label for="login-password">{{ $t('login.password') }}</label>
              <input id="login-password" type="password" required v-model="loginForm.passwd" />
            </div>
          </fieldset>

          <el-button type="sub" class="btn-login" @click.prevent="login" :loading="loading">{{ $t('login.login') }}</el-button>
        </form>
      </div>
      <div class="form-wrapper">
        <button type="button" class="switcher switcher-signup" @click="switchState()">
          {{ $t('login.register') }}
          <span class="underline"></span>
        </button>
        <form class="form form-signup">
          <fieldset>
            <legend>Please, enter your email, password and password confirmation for sign up.</legend>
            <div class="input-block">
              <label for="signup-email">{{ $t('login.email') }}</label>
              <input id="signup-email" type="email" required v-model="emailS" @blur="isExit" />
              <el-alert title="已注册过账号" type="warning" v-show="isPass2"> </el-alert>
            </div>
            <div class="input-block">
              <label for="signup-password">{{ $t('login.password') }}</label>
              <input id="signup-password" type="password" required v-model="passwordS" />
            </div>
            <div class="input-block">
              <label for="signup-password-confirm">{{ $t('login.confirm_password') }}</label>
              <input id="signup-password-confirm" type="password" required />
            </div>
          </fieldset>
          <button type="sub" class="btn-signup" @click="goToLogin">{{ $t('login.login') }}</button>
        </form>
      </div>
    </div>
  </section>
</template>

<script>
import LangSelect from '@/components/LangSelect'
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Login',
  created() {
    this.getInfo()
  },
  components: {
    LangSelect,
  },
  data() {
    return {
      tableData: [],
      loading: false,
      emailS: '',
      loginForm: {
        id: '',
        passwd: '',
      },
      passwordS: '',
      isPass2: false,
      redirect: undefined,
      otherQuery: {},
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        const query = route.query
        // console.log('登录时的route：', route)
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true,
    },
  },

  methods: {
    getInfo() {},
    switchState() {
      const switchers = [...document.querySelectorAll('.switcher')]

      switchers.forEach((item) => {
        item.addEventListener('click', function () {
          switchers.forEach((item) => item.parentElement.classList.remove('is-active'))
          this.parentElement.classList.add('is-active')
        })
      })
    },
    login() {
      // console.log(this.id, this.passwordL)
      if (this.loginForm.id && this.loginForm.passwd) {
        this.loading = true
        this.$store
          .dispatch('user/login', this.loginForm)
          .then(() => {
            this.$router.push({ path: this.redirect || '/home', query: this.otherQuery })
            this.loading = false
          })
          .catch(() => {
            this.loading = false
          })
      } else {
        this.$message({
          showClose: true,
          message: '账号或密码不能为空',
          type: 'warning',
        })
      }
    },
    isExit() {
      // eslint-disable-next-line no-unused-vars
      let isRight = true
      this.tableData.forEach((item, index) => {
        if (item.email === this.emailS) {
          isRight = false
        }
      })
      if (!isRight) {
        this.isPass2 = true
      } else {
        this.isPass2 = false
      }
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'home') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    },
    goToLogin() {},
  },
}
</script>

<style scoped>
*,
*::before,
*::after {
  box-sizing: border-box;
}
/*
body {
  margin: 0;
  font-family: Roboto, -apple-system, 'Helvetica Neue', 'Segoe UI', Arial, sans-serif;

} */
.set-language {
  width: 20px;
  height: 20px;
  color: #fff;
  position: absolute;
  top: 28px;
  font-size: 15px;
  left: 68%;
  cursor: pointer;
}
.forms-section {
  margin: 0;
  padding-top: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: #008a89;
}

.section-title {
  font-size: 32px;
  letter-spacing: 1px;
  color: #fff;
}

.forms {
  display: flex;
  align-items: flex-start;
  margin-top: 30px;
}

.form-wrapper {
  animation: hideLayer 0.3s ease-out forwards;
}

.form-wrapper.is-active {
  animation: showLayer 0.3s ease-in forwards;
}

@keyframes showLayer {
  50% {
    z-index: 1;
  }
  100% {
    z-index: 1;
  }
}

@keyframes hideLayer {
  0% {
    z-index: 1;
  }
  49.999% {
    z-index: 1;
  }
}

.switcher {
  position: relative;
  cursor: pointer;
  display: block;
  margin-right: auto;
  margin-left: auto;
  padding: 0;
  text-transform: uppercase;
  font-family: inherit;
  font-size: 16px;
  letter-spacing: 0.5px;
  color: #999;
  background-color: transparent;
  border: none;
  outline: none;
  transform: translateX(0);
  transition: all 0.3s ease-out;
}

.form-wrapper.is-active .switcher-login {
  color: #fff;
  transform: translateX(90px);
}

.form-wrapper.is-active .switcher-signup {
  color: #fff;
  transform: translateX(-90px);
}

.underline {
  position: absolute;
  bottom: -5px;
  left: 0;
  overflow: hidden;
  pointer-events: none;
  width: 100%;
  height: 2px;
}

.underline::before {
  content: '';
  position: absolute;
  top: 0;
  left: inherit;
  display: block;
  width: inherit;
  height: inherit;
  background-color: currentColor;
  transition: transform 0.2s ease-out;
}

.switcher-login .underline::before {
  transform: translateX(101%);
}

.switcher-signup .underline::before {
  transform: translateX(-101%);
}

.form-wrapper.is-active .underline::before {
  transform: translateX(0);
}

.form {
  overflow: hidden;
  min-width: 260px;
  margin-top: 50px;
  padding: 30px 25px;
  border-radius: 5px;
  transform-origin: top;
}

.form-login {
  animation: hideLogin 0.3s ease-out forwards;
}

.form-wrapper.is-active .form-login {
  animation: showLogin 0.3s ease-in forwards;
}

@keyframes showLogin {
  0% {
    background: #d7e7f1;
    transform: translate(40%, 10px);
  }
  50% {
    transform: translate(0, 0);
  }
  100% {
    background-color: #fff;
    transform: translate(35%, -20px);
  }
}

@keyframes hideLogin {
  0% {
    background-color: #fff;
    transform: translate(35%, -20px);
  }
  50% {
    transform: translate(0, 0);
  }
  100% {
    background: #d7e7f1;
    transform: translate(40%, 10px);
  }
}

.form-signup {
  animation: hideSignup 0.3s ease-out forwards;
}

.form-wrapper.is-active .form-signup {
  animation: showSignup 0.3s ease-in forwards;
}

@keyframes showSignup {
  0% {
    background: #d7e7f1;
    transform: translate(-40%, 10px) scaleY(0.8);
  }
  50% {
    transform: translate(0, 0) scaleY(0.8);
  }
  100% {
    background-color: #fff;
    transform: translate(-35%, -20px) scaleY(1);
  }
}

@keyframes hideSignup {
  0% {
    background-color: #fff;
    transform: translate(-35%, -20px) scaleY(1);
  }
  50% {
    transform: translate(0, 0) scaleY(0.8);
  }
  100% {
    background: #d7e7f1;
    transform: translate(-40%, 10px) scaleY(0.8);
  }
}

.form fieldset {
  position: relative;
  opacity: 0;
  margin: 0;
  padding: 0;
  border: 0;
  transition: all 0.3s ease-out;
}

.form-login fieldset {
  transform: translateX(-50%);
}

.form-signup fieldset {
  transform: translateX(50%);
}

.form-wrapper.is-active fieldset {
  opacity: 1;
  transform: translateX(0);
  transition: opacity 0.4s ease-in, transform 0.35s ease-in;
}

.form legend {
  position: absolute;
  overflow: hidden;
  width: 1px;
  height: 1px;
  clip: rect(0 0 0 0);
}

.input-block {
  margin-bottom: 20px;
}

.input-block label {
  font-size: 14px;
  color: #a1b4b4;
}

.input-block input {
  display: block;
  width: 100%;
  margin-top: 8px;
  padding-right: 15px;
  padding-left: 15px;
  font-size: 16px;
  line-height: 40px;
  color: #3b4465;
  background: #eef9fe;
  border: 1px solid #cddbef;
  border-radius: 2px;
}

.form [type='sub'] {
  opacity: 0;
  display: block;
  min-width: 120px;
  margin: 30px auto 10px;
  font-size: 18px;
  line-height: 40px;
  border-radius: 25px;
  border: none;
  transition: all 0.3s ease-out;
}

.form-wrapper.is-active .form [type='sub'] {
  opacity: 1;
  transform: translateX(0);
  transition: all 0.4s ease-in;
}

.btn-login {
  color: #fbfdff;
  background: #008a89;
  margin: 25px 0px 15px 65px;
}

.btn-signup {
  color: #008a89;
  background: #fbfdff;
  box-shadow: inset 0 0 0 2px #008a89;
  transform: translateX(30%);
}
</style>
<style>
.form-login .el-alert {
  position: absolute;
  top: 61%;
  left: 17%;
  z-index: 100;
}
</style>
