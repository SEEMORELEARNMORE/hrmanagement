<template>
  <div>
    <common-header></common-header>
    <div :class="classObj" class="app-wrapper">
      <sidebar class="sidebar-container" v-if="isMessagePage" />
      <div class="hasTagsView main-container">
        <div v-if="isMessagePage">
          <navbar />
          <tags-view />
        </div>
        <div class="home-page">
          <app-main v-if="isHome !== '/home'" />
          <div v-else class="homgbg">
            <img src="@/assets/images/home-1.gif" alt="" />
            <img src="@/assets/images/home-2.gif" alt="" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { getUnRead } from '@/api/article.js'
import store from '@/store'
// import Nav from '@/components/Nav/Nav.vue'
import { AppMain, Navbar, Sidebar, TagsView, CommonHeader } from './components'
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Home',
  components: {
    // Nav,
    Sidebar,
    AppMain,
    Navbar,
    TagsView,
    CommonHeader,
  },
  computed: {
    ...mapState({
      sidebar: (state) => state.app.sidebar,
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile',
      }
    },
    isHome() {
      return this.$route.path
    },
    isMessagePage() {
      // console.log(this.$route.path.search('messagelist'))
      return this.$route.path.search('messagelist') === -1 && this.$route.path.search('detail') === -1 && this.$route.path.search('userinfo') === -1
    },
    cWidth() {
      return this.sidebar.opened ? 'calc(100% - 200px)' : 'calc(100% - 70px)'
    },
  },
  data() {
    return {
      isCollapse: true,
      avatar: '',
      authority: '',
      news: false,
    }
  },
  created() {
    this.avatar = store.getters.avatar
    this.authority = store.getters.authority
    this.name = store.getters.name
    this.unread()
  },
  methods: {
    switchNav(e) {
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {
        // console.log(this)
      }
    },
    exitHome() {
      this.$router.push({ path: '/login' })
    },
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    },
    viewMessage() {
      // console.log('有新消息了？')
      this.news = !this.news
    },
    unread() {
      const id = store.getters.id
      getUnRead(id).then((res) => {
        this.news = res
        // console.log('根据请求得到的news：', res)
      })
    },
  },
}
</script>
<style scoped lang="less">
.el-header {
  display: flex;
  align-items: center;
  & > span {
    margin-right: 20px;
  }
}
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
<style>
/* element滚动条组件 隐藏水平滚动条 */
.sidebar-wrapper .el-scrollbar__wrap {
  overflow-x: hidden !important;
}
.is-horizontal {
  display: none;
}
.el-scrollbar__bar.is-vertical {
  display: none;
}
</style>
<style scoped>
.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
<style>
/* .el-submenu__title span {
  margin-right: 20px;
} */
.el-dialog__body .el-form .el-form-item {
  margin-bottom: 20px;
}
</style>
<style scoped>
.search {
  /* width: 200px; */
  display: flex;
}

.operate {
  margin: 30px 0;
}
.block {
  margin-top: 30px;
}
</style>
<style>
.el-form-item__content {
  display: flex;
}
.el-alert {
  width: 180px !important;
  height: 40px;
}
/* .search .el-input__inner{
  width: 260px;
  margin-right: 20px;
} */
.search .el-input {
  width: 15%;
  margin-right: 20px;
}
</style>
<style lang="less" scoped>
.app-wrapper {
  display: flex;
  position: absolute;
  top: 75px;
  .clearfix {
    &:after {
      visibility: hidden;
      display: block;
      font-size: 0;
      content: ' ';
      clear: both;
      height: 0;
    }
  }
  position: relative;
  height: 100%;
  width: 100%;
}
.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - 210px);
  transition: width 0.28s;
}

.hideSidebar .fixed-header {
  width: calc(100% - 54px);
}

.mobile .fixed-header {
  width: 100%;
}
.main-container {
  // width: calc(100% - $s);
}
.homgbg {
  margin-top: 30px;
  width: 100%;
  height: 400px;
  display: flex;
  img {
    flex: 1;
    width: 50%;
  }
}
</style>
