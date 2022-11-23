<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入员工号" v-model="inputNum"> </el-input>
      <el-select placeholder="请选择记录类型" v-model="inputType">
        <el-option label="新增员工" value="0"></el-option>
        <el-option label="职位变动" value="1"></el-option>
        <el-option label="员工离职" value="2"></el-option>
      </el-select>
      <el-button type="primary" icon="el-icon-search" @click="searchPos()">搜索</el-button>
    </div>
    <common-table :tableData="tableData ? tableData.slice((config.page - 1) * config.size, config.page * config.size) : []" :tableLabel="tableLabel" :config="config" @changePage="getList" @changeSize="handleSizeChange" :op="false"></common-table>
  </div>
</template>

<script>
import CommonTable from '@/components/common/CommonTable.vue'
import { getChangeData, findChangeData } from '@/api/admin.js'
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Position',
  data() {
    return {
      options: [],
      inputNum: '',
      inputType: '',
      config: { total: 2, page: 1, size: 10 },
      tableLabel: [
        { prop: 'id', label: '记录id', width: '150px' },
        { prop: 'person', label: '员工号', width: '150px' },
        { prop: 'icon', label: '记录类型', width: '120px' },
        { prop: 'description', label: '详细记录' },
      ],
      tableData: [],
      addDialogFormVisible: false,
      editDialogFormVisible: false,
      form: {
        name: '',
        department: '',
        remark: '',
        pos_num: '',
      },
      formLabelWidth: '70px',
      alert_detail: '',
      showAlert: false,
      editRow: 0,
      value: '',
    }
  },
  components: {
    CommonTable,
  },
  computed: {},
  created() {
    this.getChangeData()
  },
  methods: {
    getChangeData() {
      getChangeData().then((res) => {
        this.tableData = res
        this.config.total = res.length
        this.addIcon(this.tableData)
        // console.log(this.tableData)
      })
    },
    addIcon(data) {
      data.forEach((item) => {
        if (item.changecode === 0) {
          item.icon = 'add'
        } else if (item.changecode === 1) {
          item.icon = 'exchange'
        } else {
          item.icon = 'sub'
        }
      })
    },
    getList(page) {
      this.config.page = page
    },
    handleSizeChange(newSize) {
      this.config.size = newSize
    },
    handleCurrentChange(newPage) {
      this.pagenum = newPage
    },
    resetForm() {
      for (const key in this.form) {
        this.form[key] = ''
      }
      this.addDialogFormVisible = true
    },
    hasEmpty() {
      if (this.form.pos_num && this.form.department && this.form.name) {
        const obj = {
          pos_num: this.form.pos_num,
          name: this.form.name,
          department: this.form.department,
          remark: this.form.remark,
          time: this.dataFormat(),
        }
        this.axios({
          url: 'http://127.0.0.1:8081/data/add/position',
          method: 'POST',
          data: obj,
        }).then((res) => {
          this.getInfo()
          this.addDialogFormVisible = false
          this.showAlert = false
        })
      }
    },
    handleEdit(index, row) {
      this.editDialogFormVisible = true
      this.dialogFormVisible = true
      this.form.pos_num = row.pos_num
      this.form.department = row.department
      this.form.name = row.name
      this.form.remark = row.remark
      // console.log(this.pagesize, this.pagenum)
      this.editRow = index + this.pagesize * (this.pagenum - 1)
      // console.log(this.editRow)
    },
    submitEdit() {
      // eslint-disable-next-line camelcase
      const pos_num = this.tableData[this.editRow].pos_num
      this.axios({
        url: 'http://127.0.0.1:8081/data/update/position',
        method: 'POST',
        data: {
          pos_num,
          info: {
            pos_num: this.form.pos_num,
            name: this.form.name,
            department: this.form.department,
            remark: this.form.remark,
            time: this.dataFormat(),
          },
        },
      }).then((res) => {
        this.tableData = res.data.data
      })
      this.editDialogFormVisible = false
    },
    handleDel(index) {
      index = index + this.pagesize * (this.pagenum - 1)
      // eslint-disable-next-line camelcase
      const pos_num = this.tableData[index].pos_num
      const data = { pos_num }
      this.axios({
        url: 'http://127.0.0.1:8081/data/delete/position',
        method: 'DELETE',
        data: data,
      }).then((res) => {
        this.tableData = res.data.data
      })
    },
    dataFormat() {
      const dt = new Date()
      const y = dt.getFullYear()
      const m = this.padZero(dt.getMonth() + 1)
      const d = this.padZero(dt.getDate())
      const hh = this.padZero(dt.getHours())
      const mm = this.padZero(dt.getMinutes())
      const ss = this.padZero(dt.getSeconds())
      return `${y}-${m}-${d} ${hh}:${mm}:${ss}`
    },
    padZero(str) {
      return str > 9 ? str : '0' + str
    },
    searchPos(formName) {
      const person = (this.inputNum + '').trim()
      const changecode = this.inputType
      const data = {}
      if (person !== '') {
        data.person = person
      }
      if (changecode !== '') {
        data.changecode = changecode
      }
      findChangeData(data).then((res) => {
        this.tableData = res || []
        this.config.total = res.length
        this.config.page = 1
        this.addIcon(this.tableData)
      })
    },
  },
}
</script>

<style scoped>
.search {
  /* width: 200px; */
  display: flex;
  margin-bottom: 30px;
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
.search > .el-input {
  width: 15%;
  margin-right: 20px;
}
.search .el-select > .el-input {
  width: 100%;
}
.search > .el-select {
  margin-right: 20px;
}
.el-carousel__container {
  width: 200px;
  height: 200px !important;
}
</style>
