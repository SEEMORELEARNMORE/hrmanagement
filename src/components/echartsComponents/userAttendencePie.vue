<template>
  <div id="attendence" style="height: 280px"></div>
</template>

<script>
export default {
  name: 'userAttendencePie',
  mounted() {
    this.echartsInit()
  },
  props: ['data', 'num'],
  methods: {
    echartsInit() {
      const myChart = this.$echarts.init(document.querySelector('#attendence'))
      // myChart.showLoading()
      myChart.setOption({
        tooltip: {
          trigger: 'item',
        },
        legend: {
          top: '5%',
          left: 'center',
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2,
            },
            label: {
              show: false,
              position: 'center',
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '40',
                fontWeight: 'bold',
              },
            },
            labelLine: {
              show: false,
            },
            data: this.data,
          },
        ],
      })
    },
  },
}
</script>

<style></style>
