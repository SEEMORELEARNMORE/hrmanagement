<!-- eslint-disable vue/no-parsing-error -->
<!-- eslint-disable vue/no-mutating-props -->
<template>
  <div class="common-table">
    <el-table :data="tableData" style="width: 100%" border :header-cell-style="{ color: '#848484', fontSize: '16px', fontWeight: 700, backgroundColor: '#qua' }">
      <el-table-column show-overflow-tooltip v-for="item in tableLabel" :key="item.prop" :label="item.label" :min-width="item.width ? item.width : defaultWidth" align="center" :sortable="item.sortable ? true : false" :sort-method="sortById">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" v-if="item.prop === 'icon'" />
          <router-link :to="'/notice/detail/' + scope.row.id" class="link-type" v-else-if="item.prop === 'title'">
            <span>{{ scope.row[item.prop] }}</span>
          </router-link>
          <span style="margin-left: 10px" v-else>{{ scope.row[item.prop] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="210" v-if="op" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" round @click="handleToPositive(scope.row.id)" v-if="scope.row.state === '实习员工'">转正</el-button>
          <el-button size="mini" type="info" icon="el-icon-edit" round @click="handleEdit(scope.row)"></el-button>
          <el-button size="mini" type="danger" icon="el-icon-delete" round @click="handleDelete(scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="pager" @size-change="handleSizeChange" @current-change="changePage" :current-page="config.page" :page-sizes="[10, 20, 30, 40]" :page-size="2" layout="total, sizes, prev, pager, next, jumper" :total="config.total"> </el-pagination>
  </div>
</template>

<script>
import SvgIcon from '@/components/SvgIcon'
export default {
  name: 'CommonTable',
  props: {
    tableData: Array,
    tableLabel: Array,
    config: Object,
    op: Boolean,
  },
  data() {
    return {
      defaultWidth: '100%',
    }
  },
  comments: { SvgIcon },
  methods: {
    handleEdit(row) {
      this.$emit('edit', row)
    },
    handleDelete(row) {
      this.$emit('del', row)
    },
    changePage(page) {
      this.$emit('changePage', page)
      // console.log('page:', page)
    },
    handleSizeChange(size) {
      this.$emit('changeSize', size)
      // console.log('size:', size)
    },
    handleToPositive(id) {
      this.$emit('toPositive', id)
    },
    sortById(a, b) {
      return a.id - b.id
    },
  },
}
</script>

<style lang="less" scoped>
.common-table {
  .el-table__body-wrapper {
    height: 100% !important;
  }
}
</style>
