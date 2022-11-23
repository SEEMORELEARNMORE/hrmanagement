<template>
  <div>
    <common-form :formLabel="searchFormLabel" :form="searchForm" :inline="true" :isDialog="false" :rules="searchrules" :spanDivide="4.1" @resetData="resetData" @handlerSearch="handlerSearch"></common-form>
    <common-table :tableData="tableData ? tableData.slice((config.page - 1) * config.size, config.page * config.size) : []" :tableLabel="tableLabel" :config="config" @changePage="getList" @changeSize="handleSizeChange"></common-table>
  </div>
</template>

<script>
import CommonForm from '@/components/common/CommonForm.vue'
import CommonTable from '@/components/common/CommonTable.vue'
import { getData, getSearchData } from '@/api/article'
export default {
  name: 'browse',
  components: {
    CommonTable,
    CommonForm,
  },
  data() {
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
      tableData: [],
      tableLabel: [
        { prop: 'id', label: 'ID', width: '120', sortable: true },
        { prop: 'author', label: 'Author', width: '240' },
        { prop: 'title', label: 'Title', width: '240' },
        { prop: 'time', label: 'Date', width: '140' },
      ],
      config: { total: 5, page: 1, size: 10 },
      searchFormLabel: [
        { model: 'id', label: '文章ID', type: 'input', noLabel: true },
        { model: 'author', label: '作者', type: 'input', noLabel: true },
        { model: 'title', label: '标题', type: 'input', noLabel: true },
        { model: 'startTime', label: '开始时间', type: 'datetime', noLabel: true },
        { model: 'endTime', label: '结束时间', type: 'datetime', noLabel: true },
      ],
      searchForm: {},
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
      getData().then((res) => {
        this.tableData = res
        this.config.total = this.tableData.length
        this.config.page = 1
      })
    },
    getList(page) {
      this.config.page = page
    },
    handleSizeChange(newSize) {
      this.config.size = newSize
    },
    editUser(row) {
      // console.log('一行的数据：', row)
      // this.operateType = 'edit'
      // this.isShow = true
      // this.operateForm = row
    },
    delUser(row) {
      this.$confirm('此操作将永久删除该组件，是否继续?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        // const id = row.id
        // this.$axios
        //   .get('user/del', {
        //     params: { id },
        //   })
        //   .then(() => {
        //     this.$message({
        //       type: 'success',
        //       message: '删除成功',
        //     })
        //     this.getList()
        // })
      })
    },
    resetData() {
      for (const item in this.searchForm) {
        this.searchForm[item] = ''
      }
    },
    getDate(date) {
      const pattern = 'yyyy-MM-dd hh:mm:ss'
      date = new Date(date).format(pattern)
      return date
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
      getSearchData(data).then((res) => {
        this.tableData = res
        this.config.total = res.length
        this.config.page = 1
      })
    },
  },
}
</script>

<style></style>
