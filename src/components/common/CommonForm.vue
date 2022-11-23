<!-- eslint-disable vue/no-mutating-props -->
<template>
  <el-form ref="formName" label-width="96px" :model="form" :inline="inline" :rules="rules ? rules : null">
    <el-col :span="spanDivide" v-for="item in formLabel" :key="item.label">
      <el-form-item :label="item.noLabel ? '' : item.label + ':'" :prop="item.model">
        <el-input v-if="item.type === 'input' || item.type === 'textarea'" :type="item.type === 'textarea' ? 'textarea' : ''" :placeholder="'请输入' + item.label" v-model="form[item.model]" :width="100"> </el-input>
        <el-switch v-if="item.type === 'switch'" v-model="form[item.model]"></el-switch>
        <el-date-picker v-if="item.type === 'date'" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="form[item.model]" :picker-options="expireTimeOption"></el-date-picker>
        <el-date-picker v-if="item.type === 'datetime'" type="datetime" :placeholder="'请选择' + item.label" default-time="12:00:00" v-model="form[item.model]" :picker-options="expireTimeOption"> </el-date-picker>
        <el-select v-if="item.type === 'select'" :placeholder="'请选择' + item.label" v-model="form[item.model]">
          <el-option v-for="op in item.opts" :key="op.value" :label="op.label" :value="op.value"></el-option>
        </el-select>
        <el-radio-group v-model="form[item.model]" v-if="item.type === 'radio'">
          <el-radio v-for="op in item.opts" :key="op.value" :label="op.label" :value="op.value"></el-radio>
        </el-radio-group>
      </el-form-item>
    </el-col>

    <el-col :span="spanDivide">
      <el-form-item class="selectOp" v-if="isDialog">
        <el-button @click="cancelEdit('formName')">取消</el-button>
        <el-button type="primary" @click="submitForm('formName')">提交</el-button>
      </el-form-item>
      <el-form-item class="selectOp" v-if="!isDialog">
        <el-button @click="reset">重置</el-button>
        <el-button type="primary" @click="handlerSearch('formName')">查询</el-button>
      </el-form-item>
    </el-col>
  </el-form>
</template>

<script>
export default {
  name: 'CommonForm',
  props: {
    formLabel: Array,
    form: Object,
    inline: Boolean,
    rules: Object,
    formName: String,
    isDialog: Boolean,
    spanDivide: Number,
  },
  data() {
    return {
      model: '',
      expireTimeOption: {
        disabledDate(date) {
          // disabledDate 文档上：设置禁用状态，参数为当前日期，要求返回 Boolean
          return date.getTime() >= Date.now()
        },
      },
    }
  },
  methods: {
    cancelEdit(formName) {
      this.$nextTick(() => {
        this.$refs[formName].resetFields() // this.$refs.form.resetFields();
      })
      this.$emit('cancelEdit', formName)
    },
    submitForm(formName) {
      // console.log(this.$refs[formName])
      this.$refs[formName].validate((valid) => {
        if (!valid) {
          console.log('error submit!!')
          return false
        } else {
          this.$emit('confirm', formName)
        }
      })
    },
    reset() {
      this.$emit('resetData')
    },
    handlerSearch(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].validate((valid) => {
          if (!valid) {
            console.log('error submit!!')
            return false
          } else {
            this.$emit('handlerSearch')
          }
        })
      } else {
        this.$emit('handlerSearch')
      }
    },
  },
  created() {},
}
</script>

<style lang="less" scoped>
.el-form--inline {
  width: 100%;
  overflow: hidden;
}
.el-form--inline .el-form-item__content .el-input,
.el-form--inline .el-form-item__content .el-select,
.el-form--inline .el-form-item__content .el-textarea {
  width: 172px !important;
}
// .el-form--inline .el-form-item__content .el-date-editor {
//   width: 192px !important;
// }
.el-dialog .el-dialog__body {
  padding: 10px 20px !important;
}
.el-form .selectOp {
  width: 100%;
}
</style>
