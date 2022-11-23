<template>
  <div class="user-salary">
    <el-date-picker v-model="monthData" type="month" placeholder="选择月" :default-time="'2022-11'"> </el-date-picker>
    工资情况如下：
    <userAttendencePie :data="monthDatas" />
  </div>
</template>

<script>
import userAttendencePie from '@/components/echartsComponents/userAttendencePie.vue'
export default {
  name: 'userSalary',
  components: { userAttendencePie },
  data() {
    return {
      monthData: '2022-11',
      monthDatas: [
        { value: 1048, name: '基本工资' },
        { value: 735, name: '效绩工资' },
        { value: 580, name: '五险一金' },
        { value: 484, name: '奖励补贴' },
        { value: 300, name: '其他扣除' },
        { value: 4054, name: '总计' },
      ],
    }
  },
}
</script>

<style></style>
