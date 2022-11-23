<template>
  <nav>
    <!-- <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">
      <el-radio-button :label="false">展开</el-radio-button>
      <el-radio-button :label="true">收起</el-radio-button>
    </el-radio-group> -->
    <el-menu default-active="1-4-1" class="el-menu-vertical-demo" @open.native="handleOpen" @close.native="handleClose" :collapse="isCollapse">
      <el-submenu index="1">
        <template slot="title">
          <i class="iconfont icon-webicon207"></i>
          <span slot="title">系统用户</span>
        </template>
        <router-link to="/home/administrator"><el-menu-item index="1-1-1">管理员</el-menu-item></router-link>
      </el-submenu>
      <!-- <el-submenu index="2">
        <template slot="title">
          <i class="iconfont icon-bumenguanli"></i>
          <span slot="title">部门管理</span>
        </template>
        <router-link to="/home/department"><el-menu-item index="2-1-1">部门信息</el-menu-item> </router-link>
      </el-submenu> -->
      <el-submenu index="3">
        <template slot="title">
          <i class="iconfont icon-zhiweiguanli"></i>
          <span slot="title">人事变动</span>
        </template>
        <router-link to="/home/change"><el-menu-item index="3-1-1">人事变动记录</el-menu-item></router-link>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <i class="iconfont icon-yuangongguanli"></i>
          <span slot="title">员工管理</span>
        </template>
       <router-link to="/home/staff/formal"> <el-menu-item index="4-1-1">正式员工</el-menu-item> </router-link>
       <router-link to="/home/staff/prictice"> <el-menu-item index="4-1-1">试用员工</el-menu-item> </router-link>
       <router-link to="/home/staff/resign"> <el-menu-item index="4-1-1">离职员工</el-menu-item> </router-link>
      </el-submenu>
      <!-- <el-submenu index="5">
        <template slot="title">
          <i class="iconfont icon-gongzi"></i>
          <span slot="title">薪酬管理</span>
        </template>
        <router-link to="/home/salary"><el-menu-item index="5-1-1">工资信息</el-menu-item></router-link>
      </el-submenu> -->
    </el-menu>
  </nav>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Nav',
  props: ['isCollapse'],
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath)
    },
  },
}
</script>

<style>
.el-menu-vertical-demo i {
  font-size: 25px;
}
</style>
