<template>
  <div>
    <el-dialog :title="operateType === 'add' ? '发放工资' : '修改工资记录'" :visible.sync="isShow">
      <common-form :formLabel="operateFormLabel" :form="operateForm" :inline="true" :spanDivide="12" :rules="rules" @cancelEdit="cancelEdit" @confirm="confirm" :isDialog="true"></common-form>
    </el-dialog>
    <div class="search">
      <common-form :formLabel="searchFormLabel" :form="searchForm" :inline="true" :spanDivide="4.5" :rules="searchrules" @resetData="resetData" @handlerSearch="handlerSearch" :isDialog="false"></common-form>
    </div>
    <div class="salary-header">
      <el-button type="primary" @click="addRecord">+ 新增</el-button>
      <export-excel :tHeader="exportConfig.tHeader" :filterVal="exportConfig.filterVal" :filename="exportConfig.filename" :tableData="tableData"></export-excel>
    </div>
    <common-table :tableData="tableData ? tableData.slice((config.page - 1) * config.size, config.page * config.size) : []" :tableLabel="tableLabel" :config="config" :op="true" @changePage="getList" @changeSize="handleSizeChange" @edit="editUser" @del="delUser"></common-table>
  </div>
</template>

<script>
import { getSalaryList, deleteSalary, addSalary, updateSalary, findSalaryData } from '@/api/admin'
import CommonForm from '@/components/common/CommonForm.vue'
import CommonTable from '@/components/common/CommonTable.vue'
import ExportExcel from '@/components/Excel/exportExcel.vue'
export default {
  name: 'allUserSalary',
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
      searchData: {},
      operateType: 'add',
      isShow: false,
      tempid: '',
      exportConfig: {
        tHeader: ['工号', '姓名', '部门', '发放年月', '基本工资', '效绩工资', '五险一金', '奖励补贴', '其他扣除', '总计', '备注'],
        filterVal: ['id', 'name', 'department', 'month', 'basic_wage', 'performance_pay', 'five_one', 'subsidies', 'other_deductions', 'total', 'remark'],
        filename: 'salaryTable',
      },
      // total 和 page 随意写的 created阶段会根据具体数据改变
      config: { total: 2, page: 1, size: 10 },
      tableData: [],
      tableLabel: [
        { prop: 'id', label: '工号', width: '120px' },
        { prop: 'name', label: '姓名', width: '120px' },
        { prop: 'department', label: '部门', width: '120px' },
        { prop: 'month', label: '发放年月', width: '200px' },
        { prop: 'basic_wage', label: '基本工资', width: '120px' },
        { prop: 'performance_pay', label: '效绩工资', width: '120px' },
        { prop: 'five_one', label: '五险一金', width: '120px' },
        { prop: 'subsidies', label: '奖励补贴', width: '120px' },
        { prop: 'other_deductions', label: '其他扣除', width: '120px' },
        { prop: 'total', label: '总计', width: '120px' },
        { prop: 'remark', label: '备注', width: '120px' },
      ],
      operateFormLabel: [
        { model: 'id', label: '工号', type: 'input' },
        { model: 'month', label: '发放年月', type: 'date' },
        { model: 'basic_wage', label: '基本工资', type: 'input' },
        { model: 'performance_pay', label: '效绩工资', type: 'input' },
        { model: 'five_one', label: '五险一金', type: 'input' },
        { model: 'subsidies', label: '奖励补贴', type: 'input' },
        { model: 'other_deductions', label: '其他扣除', type: 'input' },
        { model: 'remark', label: '备注', type: 'textarea' },
      ],
      operateForm: {},
      formLabel: [
        {
          model: 'keyword',
          label: '',
          type: 'input',
        },
      ],
      rules: {
        id: [{ required: true, message: '请输入员工工号', trigger: 'blur' }],
        sex: [{ required: true, message: '请输入性别', trigger: 'blur' }],
        month: [
          { required: true, message: '请输入发放年月', trigger: 'blur' },
          { required: true, message: '请选择日期', trigger: 'change' },
        ],
        basic_wage: [{ required: true, message: '请输入基本工资', trigger: 'blur' }, valid],
        performance_pay: [{ required: true, message: '请输入效绩工资', trigger: 'blur' }, valid],
        five_one: [{ required: true, message: '请输入五险一金', trigger: 'blur' }, valid],
        subsidies: [{ required: true, message: '请输入奖励补贴', trigger: 'blur' }, valid],
        other_deductions: [{ required: true, message: '请输入其他扣除', trigger: 'blur' }, valid],
      },
      searchrules: {
        endTime: [
          {
            trigger: 'blur',
            validator: validateEndTime,
          },
        ],
      },
      searchFormLabel: [
        { model: 'id', label: '工号', type: 'input', noLabel: true },
        { model: 'name', label: '名字', type: 'input', noLabel: true },
        { model: 'startTime', label: '开始时间', type: 'date', noLabel: true },
        { model: 'endTime', label: '结束时间', type: 'date', noLabel: true },
      ],
      searchForm: {},
    }
  },
  created() {
    this.getInfo()
  },
  methods: {
    getInfo() {
      getSalaryList().then((res) => {
        this.tableData = res
        this.config.total = res.length
        // this.tableData.forEach((item) => {
        //   item.month = this.getDate(item.month)
        // })
      })
    },
    addRecord() {
      this.operateForm = {}
      this.isShow = true
      this.operateType = 'add'
    },
    confirm() {
      this.data = Object.assign({}, this.operateForm)
      for (const item in this.data) {
        switch (item) {
          case 'month':
            this.data[item] = new Date(this.data.month)
            break
          case 'basic_wage':
          case 'performance_pay':
          case 'five_one':
          case 'subsidies':
          case 'other_deductions':
            if (typeof this.data[item] === 'string') {
              this.data[item] = Number(this.data[item])
            }
        }
      }
      if (this.operateType === 'add') {
        // console.log('新增数据：', this.operateForm)
        addSalary(this.data).then((res) => {
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
      } else {
        updateSalary(this.data).then((res) => {
          if (res === 1) {
            const index = this.tableData.findIndex((v) => v.id === this.tempid)
            this.data.month = this.getDate(this.data.month)
            this.data.total = this.data.basic_wage + this.data.performance_pay + this.data.five_one + this.data.subsidies - this.data.other_deductions
            this.tableData.splice(index, 1, this.data)
            this.$message({
              type: 'success',
              message: '修改成功',
            })
          } else {
            this.$message({
              type: 'error',
              message: '修改失败，请稍后再试',
            })
          }

          this.isShow = false
        })
      }
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
      this.operateType = 'edit'
      this.isShow = true
      this.operateForm = Object.assign({}, row)
      this.tempid = row.id
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
      const pattern = 'yyyy-MM-dd'
      date = new Date(date).format(pattern)
      return date
    },
    handlerSearch() {
      const data = {}
      // console.log('searchForm:', this.searchForm)
      for (const item in this.searchForm) {
        if ((item === 'name' || item === 'id') && this.searchForm[item].trim() !== '') {
          data[item] = this.searchForm[item].trim()
        } else if ((item === 'startTime' || item === 'endTime') && this.searchForm[item] !== null) {
          data[item] = this.searchForm[item]
        }
      }
      findSalaryData(data).then((res) => {
        this.tableData = res
        this.config.total = res.length
        this.config.page = 1
      })
    },
    resetData() {
      for (const item in this.searchForm) {
        this.searchForm[item] = ''
      }
    },
  },
}
</script>

<style lang="less" scoped>
.salary-header {
  margin: 0px 0 20px 0;
}
</style>
