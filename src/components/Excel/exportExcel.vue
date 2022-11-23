<template>
  <el-button type="primary" @click="exportExcel" icon="el-icon-download">导出</el-button>
</template>

<script>
import { parseTime } from '@/utils'
export default {
  name: 'exportExcel',
  props: {
    tHeader: { type: Array, required: true },
    filterVal: { type: Array, required: true },
    filename: { type: String, required: true },
    tableData: { type: Array, required: true },
  },
  methods: {
    exportExcel() {
      import('@/vender/Export2Excel').then((excel) => {
        const data = this.formatJson(this.filterVal)
        // console.log(this.tHeader, this.filterVal, this.filename, this.tableData)
        excel.export_json_to_excel({
          header: this.tHeader,
          data,
          filename: this.filename,
        })
      })
    },
    formatJson(filterVal) {
      // console.log('转excel的数据格式化', this.tableData, '。传递过来的数据：', filterVal)
      return this.tableData.map((v) =>
        filterVal.map((j) => {
          if (j === 'timestamp') {
            return parseTime(v[j])
          } else {
            return v[j]
          }
        })
      )
    },
  },
}
</script>

<style></style>
