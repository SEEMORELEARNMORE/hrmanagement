<template>
  <div class="userinfo">
    <el-card class="box-card">
      <div class="left">
        <p>个人信息</p>
        <div class="avatar_info">
          <img :src="info.avatar" alt="" class="avatar" />
          <p class="username">{{ info.name }}</p>
          <p class="email">{{ info.email }}</p>
        </div>
      </div>
      <div class="right">
        <p class="identity_info">身份信息</p>
        <el-divider></el-divider>
        <ul>
          <li>
            <span>所在部门：{{ info.department }} </span>
          </li>
          <li>
            <span>职务： {{ info.job }}</span>
          </li>
          <li>
            <span>在职状态：{{ info.state === 'F' ? '正式员工' : '实习员工' }} </span>
          </li>
        </ul>
        <p class="pravite_info">个人信息</p>
        <el-divider></el-divider>
        <ul>
          <li>
            <span>性别： {{ info.sex }}</span>
          </li>
          <li>
            <span>电话： {{ info.tel }}</span>
          </li>
          <li>
            <span>受教育程度： {{ info.edu_level }}</span>
          </li>
          <li>
            <span>生日： {{ info.birthday }}</span>
          </li>
          <li>
            <span>住址：{{ info.address }} </span>
          </li>
          <li>
            <span>职业技能：{{ info.spcialty }} </span>
          </li>
        </ul>
      </div>
    </el-card>
  </div>
</template>

<script>
import store from '@/store'
import { getInfo } from '@/api/user'
export default {
  name: 'user',
  data() {
    return {
      info: {},
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      getInfo(store.getters.id).then((res) => {
        this.info = res
      })
    },
  },
}
</script>

<style lang="less">
li {
  list-style: none;
}
.userinfo {
  width: 740px;
  margin-top: 20px;
  .el-card__body {
    display: flex;
    padding: 0;
    height: 400px;

    // background-color: #ccc;
    .left {
      position: relative;
      height: 100%;
      flex: 1;
      background-color: rgb(237, 237, 237);
      text-align: center;
      p {
        margin: 15px 0;
      }
      .avatar_info {
        position: absolute;
        top: 40%;
        left: 50%;
        transform: translateX(-50%);
      }
      .avatar {
        width: 60px;
        height: 60px;
        border-radius: 50%;
      }
      .username{
        font-weight: 700;
      }
      .email{
        font-size: 12px;
        color: #666;
      }
    }
    .right {
      flex: 1;
      padding: 20px;
      .el-divider--horizontal {
        margin: 15px 0;
      }
      p {
        font-size: 14px;
        color: rgb(44, 42, 42);
      }
      ul li {
        font-size: 12px;
        margin: 15px;
        color: #999;
      }
      .pravite_info {
        margin-top: 10px;
      }
    }
  }
}
</style>
