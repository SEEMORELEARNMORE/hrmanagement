<template>
  <div class="user-attendence">
    <div class="month-attendence">
      月度出勤情况（天）
      <userAttendencePie :data="monthData" />
    </div>
    <el-card class="collectivity-card">
      <span>年度出勤情况（天）</span>
      <div class="card-items">
        <div class="card-item">
          <div>迟到</div>
          <div>23</div>
        </div>
        <div class="card-item">
          <div>早退</div>
          <div>23</div>
        </div>
        <div class="card-item">
          <div>旷工</div>
          <div>23</div>
        </div>
        <div class="card-item">
          <div>缺卡</div>
          <div>23</div>
        </div>
        <div class="card-item">
          <div>请假</div>
          <div>23</div>
        </div>
        <div class="card-item">
          <div>公出</div>
          <div>23</div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import userAttendencePie from '@/components/echartsComponents/userAttendencePie.vue'
export default {
  name: 'userAttendence',
  components: { userAttendencePie },
  data() {
    return {
      monthData: [
        { value: 1048, name: '正常出勤' },
        { value: 735, name: '迟到' },
        { value: 580, name: '早退' },
        { value: 484, name: '请假' },
        { value: 300, name: '公出' },
        { value: 40, name: '旷工' },
      ],
      yearData: [
        { value: 104558, name: '正常出勤' },
        { value: 735, name: '迟到' },
        { value: 580, name: '早退' },
        { value: 484, name: '请假' },
        { value: 300, name: '公出' },
        { value: 40, name: '旷工' },
      ],
    }
  },
}
</script>

<style scoped lang="less">
.user-attendence {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
.card-items {
  display: flex;
  flex-wrap: wrap;
  margin: 20px;
  margin-bottom: 10px;
}
.month-attendence {
  width: 68%;
}
.collectivity-card {
  width: 48%;
  height: 100%;
  > span {
    font-size: 16px;
  }
}
.card-item {
  display: flex;
  flex-direction: column;
  width: 30%;
  margin-bottom: 30px;
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
