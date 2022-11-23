<script>
export default {
  name: 'webSocket',
  mounted() {
    const vm = this
    vm.uid = store.getters.id
    vm.conWebSocket(-1)
  },
  methods: {
    conWebSocket(noticeid) {
      const vm = this
      if (window.WebSocket) {
        vm.socket = new WebSocket(`ws://localhost:8083/websocket/${vm.uid}`)
        const socket = vm.socket

        // clearInterval(vm.wsTimer)
        // vm.wsTimer = setInterval(() => {
        //   if (vm.socket.readyState === 1) {
        //     vm.socket.send('1')
        //     clearInterval(this.wsTimer)
        //   } else {
        //     console.log('ws建立连接失败')
        //     vm.wsInit()
        //   }
        // }, 3000)
        socket.onopen = function (e) {
          console.log('连接服务器成功')
          vm.$message({ type: 'success', message: '连接服务器成功' })
        }
        socket.onclose = function (e) {
          console.log('服务器关闭')
        }
        socket.onerror = function () {
          console.log('连接出错')
        }
        // 接收服务器的消息
        socket.onmessage = function (e) {
          const message = JSON.parse(e.data)
          console.log('服务器通过websocket传过来的数据', message)
          this.messageValue = message.length
        }
      }
    },
  },
}
</script>
