<template>
  <div>
    <el-dialog title="增加考勤记录" :visible.sync="isShow">
      <common-form :formLabel="operateFormLabel" :form="operateForm" :inline="true" :spanDivide="12" :rules="rules" @cancelEdit="cancelEdit" @confirm="confirm" :isDialog="true"></common-form>
    </el-dialog>
    <div class="search">
      <common-form :formLabel="searchFormLabel" :form="searchForm" :inline="true" :rules="searchrules" :spanDivide="4.5" @resetData="resetData" @handlerSearch="handlerSearch" :isDialog="false"></common-form>
    </div>
    <div class="attendence-header">
      <el-button type="primary" @click="addRecord">+ 新增</el-button>
      <export-excel :tHeader="exportConfig.tHeader" :filterVal="exportConfig.filterVal" :filename="exportConfig.filename" :tableData="tableData"></export-excel>
    </div>
    <common-table :tableData="tableData ? tableData.slice((config.page - 1) * config.size, config.page * config.size) : []" :tableLabel="tableLabel" :config="config" :op="true" @changePage="getList" @changeSize="handleSizeChange" @edit="editUser" @del="delUser"></common-table>
  </div>
</template>

<script>
import { getAttendanceList, addAttendance, updateAttendance, findAttendanceData } from '@/api/admin'
import CommonForm from '@/components/common/CommonForm.vue'
import CommonTable from '@/components/common/CommonTable.vue'
import ExportExcel from '@/components/Excel/exportExcel.vue'

export default {
  name: 'attendence',
  components: {
    CommonForm,
    CommonTable,
    ExportExcel,
  },
  data() {
    const valid = { pattern: /^-?\d+\.?\d*$/, message: '必须为数字', trigger: 'blur' }
    const validateEndTime = (rule, value, callback) => {
      if (value !== '') {
        if (this.searchForm.startTime !== '' && new Date(this.searchForm.startTime).getTime() >= new Date(this.searchForm.endTime).getTime()) {
          callback(new Error('截止时间必须大于起始时间！'))
        } else {
          callback()
        }
      }
    }
    return {
      tempid: '',
      data: {},
      tableData: [],
      isShow: false,
      tableLabel: [
        { prop: 'user_id', label: '工号', width: '120px' },
        { prop: 'name', label: '姓名', width: '120px' },
        { prop: 'finished', label: '是否完成签到', width: '120px' },
        { prop: 'time', label: '签到时间', width: '200px' },
      ],
      operateFormLabel: [
        { model: 'user_id', label: '工号', type: 'input' },
        { model: 'name', label: '姓名', type: 'input' },
        {
          model: 'finished',
          label: '是否签到',
          type: 'select',
          opts: [
            { label: '是', value: 1 },
            { label: '否', value: 0 },
          ],
        },
        { model: 'time', label: '签到时间', type: 'datetime' },
      ],
      exportConfig: {
        tHeader: ['工号', '姓名', '是否完成签到', '签到时间'],
        filterVal: ['user_id', 'name', 'finished', 'time'],
        filename: 'attendanceTable',
      },
      config: { total: 2, page: 1, size: 10 },
      operateForm: {},
      searchFormLabel: [
        { model: 'id', label: '工号', type: 'input', noLabel: true },
        { model: 'name', label: '名字', type: 'input', noLabel: true },
        { model: 'startTime', label: '开始时间', type: 'datetime', noLabel: true },
        { model: 'endTime', label: '结束时间', type: 'datetime', noLabel: true },
      ],
      searchForm: {},
      rules: {},
      searchrules: {
        endTime: [
          {
            trigger: 'blur',
            validator: validateEndTime,
          },
        ],
      },
    }
  },
  created() {
    this.getInfo()
  },
  methods: {
    getInfo() {
      getAttendanceList().then((res) => {
        this.tableData = res
        this.config.total = res.length
        this.config.page = 1
        this.tableData.forEach((item) => {
          item.finished = item.finished ? '是' : '否'
        })
      })
    },
    addRecord() {
      this.operateForm = {}
      this.isShow = true
    },
    confirm() {
      this.data = Object.assign({}, this.operateForm)
      if (this.data.finished === '是') this.data.finished = true
      else if (this.data.finished === '否') this.data.finished = false
      this.data.time = this.getDate(this.data.time)
      // console.log('data:', this.data)
      // console.log('新增数据：', this.operateForm)
      addAttendance(this.data).then((res) => {
        if (res === 1) {
          this.getInfo()
          return this.$message({
            type: 'success',
            message: '添加成功',
          })
        }
        this.$message({
          type: 'error',
          message: '不存在该员工',
        })
      })

      this.isShow = false
    },
    cancelEdit() {
      this.isShow = false
    },
    getList(page) {
      this.config.page = page
    },
    handleSizeChange(newSize) {
      this.config.size = newSize
    },
    editUser(row) {
      // console.log(row)
      const content = row.finished === '是' ? '已经完成签到了，确定要修改签到状态吗？' : '还没有签到哦，确定要修改签到状态吗'
      this.$confirm(content, '修改签到状态', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          const record_id = Number(row.record_id)
          let finished
          if (row.finished === '是') {
            finished = false
          } else {
            finished = true
          }
          updateAttendance({ record_id: record_id, finished: finished }).then((res) => {
            if (res === 1) {
              const index = this.tableData.findIndex((v) => v.record_id === record_id)
              // console.log('finished:', finished)
              this.data = Object.assign({}, row)
              this.data.finished = finished ? '是' : '否'
              this.tableData.splice(index, 1, this.data)
              return this.$message({
                type: 'success',
                message: '修改成功',
              })
            }
            this.$message({
              type: 'error',
              message: '修改失败，请稍后再试',
            })
          })
        })
        .catch(() => {})
    },
    delUser(row) {
      this.$confirm('此操作将让永远删除此记录，是否继续?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        const id = row.id
        const month = row.month
        deleteSalary({ id, month }).then((res) => {
          // console.log('delete中的res：', res)
          if (res !== 1) {
            return this.$message({
              type: 'warning',
              message: '删除失败，请稍后再尝试',
            })
          }
          this.$message({
            type: 'success',
            message: '删除成功',
          })
          this.getList()
        })
      })
    },
    getDate(date) {
      const pattern = 'yyyy-MM-dd hh:mm:ss'
      date = new Date(date).format(pattern)
      return date
    },
    resetData() {
      for (const item in this.searchForm) {
        this.searchForm[item] = ''
      }
    },
    handlerSearch() {
      // console.log('搜索数据：', this.searchForm)
      const data = {}
      for (const item in this.searchForm) {
        if (this.searchForm[item] !== undefined) {
          data[item] = typeof this.searchForm[item] === 'string' ? this.searchForm[item].trim() : this.searchForm[item]
          if (data[item] === '') {
            delete data[item]
          } else if (item === 'startTime' || item === 'endTime') {
            data[item] = this.getDate(data[item])
          }
        }
      }
      // console.log(data)
      findAttendanceData(data).then((res) => {
        this.tableData = res
        this.config.total = res.length
        this.config.page = 1
      })
    },
  },
}
</script>

<style lang="less" scoped>
.attendence-header {
  margin-bottom: 20px;
}
</style>
