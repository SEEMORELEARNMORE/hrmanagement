<template>
  <div style="width: 50%; height: 280px" id="main"></div>
</template>
<script>
// 通过this.$echarts来使用
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'pie',
  mounted() {
    // 在通过mounted调用即可
    this.echartsInit()
  },
  methods: {
    // 初始化echarts
    echartsInit() {
      // 3、基于准备好的dom，初始化echarts实例
      const myChart = this.$echarts.init(document.querySelector('#main'))
      myChart.showLoading()
      const data = [
        { value: 0, name: '小学' },
        { value: 0, name: '初中' },
        { value: 0, name: '高中' },
        { value: 0, name: '职高' },
        { value: 0, name: '大本' },
        { value: 0, name: '大专' },
        { value: 0, name: '硕士' },
        { value: 0, name: '博士' },
      ]
      this.axios({
        url: 'http://localhost:8083/person/dividebyedu',
        method: 'GET',
        // async: false,
      }).then((res) => {
        const getData = res.data
        for (let i = 0; i < getData.length; i++) {
          for (let j = 0; j < data.length; j++) {
            if (getData[i].edu_level === data[j].name) {
              data[j].value = getData[i].count
            }
          }
        }
        myChart.hideLoading() // 隐藏 loading 效果
        myChart.setOption({
          title: {
            text: '员工受教育程度',
            left: 'center',
            top: 20,
            // textStyle: {
            //   color: '#008a89',
            // },
          },
          tooltip: {
            trigger: 'item',
          },
          visualMap: {
            show: false,
            min: 0,
            // 拿到数据后颜色出现全黑或者全白，调节max
            max: 15,
            inRange: {
              colorLightness: [0, 1],
            },
          },

          series: [
            {
              name: '人数',
              type: 'pie',
              radius: '55%',
              center: ['50%', '50%'],

              data: data.sort(function (a, b) {
                return a.value - b.value
              }),

              roseType: 'radius',
              label: {
                color: '#008a89',
              },
              labelLine: {
                lineStyle: {
                  color: '#008a89',
                },
                smooth: 0.2,
                length: 10,
                length2: 20,
              },
              itemStyle: {
                normal: {
                  color: function (colors) {
                    const colorList = ['indigo', 'purple', '#fc8251', '#5470c6', '#91cd77', '#ef6567', '#f9c956', '#75bedc']
                    return colorList[colors.dataIndex]
                  },
                },
                color: '#008a89',
                shadowBlur: 200,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
              animationType: 'scale',
              animationEasing: 'elasticOut',
              animationDelay: function (idx) {
                return Math.random() * 200
              },
            },
          ],
        })
      })

      // // 4、指定图表的配置项和数据
      // const option = {
      //   // backgroundColor: '#2c343c',
      // }
      // // 5、使用刚指定的配置项和数据显示图表。
      // myChart.setOption(option)
    },
  },
}
</script>
<style scoped>
#main {
  /* margin-top: 30px; */
  /* background-image: linear-gradient(to right, #e4afcb 0%, #b8cbb8 0%, #b8cbb8 0%, #e2c58b 30%, #c2ce9c 64%, #7edbdc 100%); */
  opacity: 0.7;
}
</style>
