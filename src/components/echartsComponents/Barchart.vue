<template>
  <div style="width: 50%; height: 280px" class="second"></div>
</template>
<script>
// 通过this.$echarts来使用
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'batchart',
  mounted() {
    // 在通过mounted调用即可
    this.echartsInit()
  },
  methods: {
    // 初始化echarts
    echartsInit() {
      // 3、基于准备好的dom，初始化echarts实例
      const myChart = this.$echarts.init(document.querySelector('.second'))
      myChart.showLoading()
      this.axios({
        url: 'http://localhost:8083/person/dividebydep',
        method: 'GET',
        async: false,
      }).then((res) => {
        const data1 = res.data
          .map((obj, index) => {
            return obj.department
          })
          .join(',')
          .split(',')
        const data2 = res.data
          .map((obj, index) => {
            return obj.count
          })
          .join(',')
          .split(',')
        // const getData = res.data
        myChart.hideLoading() // 隐藏 loading 效果
        myChart.setOption({
          title: {
            text: '部门人数分布',
            // textStyle: {
            //   // 文字颜色
            //   color: '#008a89',
            // },
          },
          // textStyle: {
          //   // 文字颜色
          //   color: '#008a89',
          // },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow',
            },
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true,
          },
          xAxis: [
            {
              type: 'category',
              data: data1,
              axisTick: {
                alignWithLabel: true,
              },
            },
          ],
          yAxis: [
            {
              type: 'value',
            },
          ],
          series: [
            {
              name: '人数',
              type: 'bar',
              barWidth: '60%',
              data: data2,
              itemStyle: {
                normal: {
                  // 这里是重点
                  color: function (params) {
                    // 注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                    const colorList = ['#c23531', '#2f4554', '#61a0a8', '#d48265', '#91c7ae', '#749f83', '#ca8622']
                    return colorList[params.dataIndex]
                  },
                },
              },
            },
          ],
        })
      })
    },
  },
}
</script>
<style scoped>
.second {
  margin-top: 30px;
  /* background-image: linear-gradient(to right, #e4afcb 0%, #b8cbb8 0%, #b8cbb8 0%, #e2c58b 30%, #c2ce9c 64%, #7edbdc 100%); */
  opacity: 0.7;
}
</style>
