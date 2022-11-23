<!-- eslint-disable vue/no-duplicate-attributes -->
<!-- eslint-disable vue/no-parsing-error -->

/* eslint-disable no-unused-vars */
<template>
  <div>
    <div class="search">
      <common-form :formLabel="searchFormLabel" :form="searchForm" :inline="true" :isDialog="false" :spanDivide="4.1" @resetData="resetData" @handlerSearch="handlerSearch"></common-form>
    </div>
    <div class="operate">
      <el-button type="primary" @click="addStaff">添加</el-button>
      <export-excel :tHeader="exportConfig.tHeader" :filterVal="exportConfig.filterVal" :filename="exportConfig.filename" :tableData="tableData"></export-excel>
    </div>
    <common-table
      :tableData="tableData ? tableData.slice((config.page - 1) * config.size, config.page * config.size) : []"
      :tableLabel="tableLabel"
      :config="config"
      :op="true"
      @changePage="getList"
      @changeSize="handleSizeChange"
      @toPositive="toPositive"
      @edit="editUser"
      @del="delUser"
    ></common-table>
    <el-dialog :title="operateType === 'add' ? '新增用户' : '更新用户'" :visible.sync="isShow">
      <common-form :formLabel="operateFormLabel" :form="operateForm" :inline="true" :rules="rules" @cancelEdit="cancelEdit" @confirm="confirm" :isDialog="true" :spanDivide="12"></common-form>
    </el-dialog>
  </div>
</template>

<script>
import CommonTable from '@/components/common/CommonTable.vue'
import CommonForm from '@/components/common/CommonForm.vue'
import ExportExcel from '@/components/Excel/exportExcel.vue'
import { deleteInfo, addStaff, updateStaff, findByCondition, findAll } from '@/api/staff.js'
import store from '@/store'
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Staff',
  components: {
    CommonTable,
    CommonForm,
    ExportExcel,
  },
  data() {
    const checkEmail = (rule, value, callback) => {
      const regEmail = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      if (regEmail.test(value)) {
        return callback()
      }
      callback(new Error('请输入合法的邮箱号'))
    }
    const validateStartTime = (rule, value, callback) => {
      if (value === undefined) {
        callback(new Error('开始时间不能为空'))
      } else {
        callback()
      }
    }
    return {
      tempid: '',
      data: {},
      tableData: [],
      operateType: 'add',
      isShow: false,
      tableLabel: [
        { prop: 'id', label: '工号', width: '120px' },
        { prop: 'name', label: '姓名', width: '120px' },
        { prop: 'sex', label: '性别', width: '120px' },
        { prop: 'birthday', label: '生日', width: '120px' },
        { prop: 'department', label: '部门', width: '120px' },
        { prop: 'job', label: '职位', width: '120px' },
        { prop: 'edu_level', label: '受教育程度', width: '120px' },
        { prop: 'spcialty', label: '专业技能', width: '120px' },
        { prop: 'state', label: '在职情况', width: '120px' },
        { prop: 'tel', label: '电话', width: '120px' },
        { prop: 'email', label: '邮箱', width: '120px' },
        { prop: 'address', label: '家庭住址', width: '120px' },
        { prop: 'remark', label: '备注', width: '120px' },
      ],
      exportConfig: {
        tHeader: ['工号', '姓名', '性别', '部门', '职位', '受教育程度', '专业技能', '电话', '邮箱', '在职情况', '家庭住址', '备注'],
        filterVal: ['id', 'name', 'sex', 'department', 'job', 'edu_level', 'spcialty', 'tel', 'email', 'state', 'address', 'remark'],
        filename: 'staffTable',
      },
      // total 和 page 随意写的 created阶段会根据具体数据改变
      config: { total: 2, page: 1, size: 10 },
      operateFormLabel: [
        { model: 'name', label: '姓名', type: 'input' },
        {
          model: 'sex',
          label: '性别',
          type: 'select',
          opts: [
            { label: '男', value: '男' },
            { label: '女', value: '女' },
          ],
        },
        { model: 'tel', label: '电话', type: 'input' },
        { model: 'department', label: '部门', type: 'select', opts: [{}] },
        { model: 'job', label: '职位', type: 'select', opts: [{}] },
        { model: 'edu_level', label: '受教育程度', type: 'select', opts: [{}] },
        { model: 'email', label: '邮箱', type: 'input' },
        { model: 'birthday', label: '生日', type: 'date' },
        { model: 'address', label: '家庭住址', type: 'input' },
        { model: 'spcialty', label: '专业技能', type: 'textarea' },
        { model: 'remark', label: '备注', type: 'textarea' },
      ],
      operateForm: {},
      searchFormLabel: [
        { model: 'id', label: '工号', type: 'input', noLabel: true },
        { model: 'name', label: '姓名', type: 'input', noLabel: true },
        { model: 'department', label: '部门', type: 'select', opts: [{}], noLabel: true },
        { model: 'job', label: '职务', type: 'select', opts: [{}], noLabel: true },
        { model: 'state', label: '在职情况', type: 'select', opts: [{}], noLabel: true },
      ],
      searchForm: {},
      rules: {
        name: [{ required: true, message: '请输入员工姓名', trigger: 'blur' }],
        sex: [{ required: true, message: '请输入性别', trigger: 'blur' }],
        tel: [
          {
            required: true,
            pattern: /^1[345789]\d{9}$/, // 可以写正则表达式呦呦呦
            message: '请输入正确的手机号码',
            trigger: 'blur',
          },
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' },
        ],
        department: [{ required: true, message: '请选择部门', trigger: ['blur', 'change'] }],
        job: [{ required: true, message: '请选择职务', trigger: 'change' }],
        edu_level: [{ required: true, message: '请选择受教育程度', trigger: 'change' }],
        birthday: [{ type: 'date', validator: validateStartTime, trigger: 'change' }],
      },
    }
  },
  created() {
    this.getInfo()
    this.insertOpts(this.operateFormLabel)
    this.insertOpts(this.searchFormLabel)
    this.selectStates(this.searchFormLabel)
    const data = {}
  },
  methods: {
    insertOpts(formLabel) {
      formLabel.forEach((item) => {
        if (item.model === 'job' || item.model === 'department' || item.model === 'edu_level') {
          let objVar = []
          if (item.model === 'job') {
            objVar = store.getters.job_options
          } else if (item.model === 'department') {
            objVar = store.getters.department_options
          } else {
            objVar = store.getters.edu_level_options
          }
          // console.log('objVar:', objVar)
          for (let i = 0; i < objVar.length; i++) {
            const obj = { label: '', value: '' }
            obj.label = objVar[i].description
            obj.value = objVar[i].code
            item.opts.push(obj)
          }
        }
      })
      // console.log('插入opts后的label：', this.operateFormLabel)
    },
    selectStates(formLabel) {
      const opts = [
        { label: '正式员工', value: 'F' },
        { label: '实习员工', value: 'P' },
        { label: '离职员工', value: 'R' },
      ]
      formLabel.forEach((item) => {
        if (item.model === 'state') {
          item.opts = opts
        }
      })
    },
    getList(page) {
      this.config.page = page
    },
    getInfo() {
      findAll().then((res) => {
        res.forEach((item) => {
          if (item.state === 'F') {
            item.state = '正式员工'
          } else if (item.state === 'P') {
            item.state = '实习员工'
          } else {
            item.state = '离职员工'
          }
        })
        this.tableData = res
        // console.log(this.tableData)
        this.config.total = res.length
      })
      // console.log('this.config', this.config)
    },
    handleSizeChange(newSize) {
      this.config.size = newSize
      // console.log(this.config)
    },
    editUser(row) {
      this.operateType = 'edit'
      this.isShow = true
      this.operateForm = Object.assign({}, row)
      this.tempid = row.id
    },
    delUser(row) {
      this.$confirm('此操作将让员工变为离职员工，是否继续?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        const id = row.id
        deleteInfo(id).then((res) => {
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
    cancelEdit() {
      this.isShow = false
    },
    confirm() {
      this.data = Object.assign({}, this.operateForm)
      if (this.operateType === 'add') {
        console.log('data:', this.data)
        // console.log('新增数据：', this.operateForm)
        addStaff(this.data).then((res) => {
          if (res === 1) {
            this.getInfo()
            return this.$message({
              type: 'success',
              message: '新增成功',
            })
          }
          this.$message({
            type: 'error',
            message: '添加失败，请稍后再试',
          })
        })
      } else {
        // console.log()

        if (this.data.state === '正式员工') {
          this.data.state = 'F'
        } else if (this.data.state === '实习员工') {
          this.data.state = 'P'
        } else {
          this.data.state = 'R'
        }
        updateStaff(this.data).then((res) => {
          if (res === 1) {
            const jobs = store.getters.job_options
            const deps = store.getters.department_options
            const edus = store.getters.edu_level_options
            let index = jobs.findIndex((value) => value.code === this.data.job)
            this.data.job = jobs[index].description
            index = deps.findIndex((value) => value.code === this.data.department)
            this.data.department = deps[index].description
            index = edus.findIndex((value) => value.code === this.data.edu_level)
            this.data.edu_level = edus[index].description
            index = this.tableData.findIndex((v) => v.id === this.tempid)
            if (this.data.state === 'F') {
              this.data.state = '正式员工'
            } else if (this.data.state === 'P') {
              this.data.state = '实习员工'
            } else {
              this.data.state = '离职员工'
            }
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
    },
    addStaff() {
      this.operateForm = {}
      this.operateType = 'add'
      this.isShow = true
      // console.log('增加中的：', this.operateFormLabel)
    },
    handlerSearch() {
      const data = {}
      for (const item in this.searchForm) {
        if (this.searchForm[item] !== undefined) {
          data[item] = typeof this.searchForm[item] === 'string' ? this.searchForm[item].trim() : this.searchForm[item]
          if (data[item] === '') {
            delete data[item]
          }
        }
      }
      findByCondition(data).then((res) => {
        this.tableData = res
        this.config.total = res.length
      })
    },
    toPositive(id) {
    },
    resetData() {
      for (const item in this.searchForm) {
        this.searchForm[item] = ''
      }
    },
  },
}
</script>

<style scoped>
.search {
  /* width: 200px; */
  display: flex;
}

.operate {
  margin: -5px 0 15px 0;
}
.block {
  margin-top: 30px;
}
</style>
<style>
.el-form-item__content {
  display: flex;
  position: relative;
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
  width: 35%;
  margin-right: 20px;
}
/* .setSex .el-form-item__content .el-form-item__error {
  padding-top: 33px;
} */
</style>
