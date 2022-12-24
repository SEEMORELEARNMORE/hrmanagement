import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import '@/styles/index.css'
import '@/icons'
import * as echarts from 'echarts'
// 需要挂载到Vue原型上
import i18n from './lang'
import ElementLocale from 'element-ui/lib/locale'
import './permission'
// 引入axios
import axios from 'axios'
import '@/styles/index.scss'
import {
  Button,
  ButtonGroup,
  Form,
  FormItem,
  Select,
  Option,
  OptionGroup,
  Menu,
  Submenu,
  MenuItem,
  MenuItemGroup,
  Radio,
  RadioGroup,
  Row,
  Col,
  Container,
  Header,
  Aside,
  Main,
  Footer,
  Breadcrumb,
  BreadcrumbItem,
  RadioButton,
  Scrollbar,
  Dropdown,
  DropdownMenu,
  DropdownItem,
  Table,
  TableColumn,
  Pagination,
  Input,
  Dialog,
  Alert,
  Carousel,
  CarouselItem,
  DatePicker,
  Loading,
  Divider,
  MessageBox,
  Message,
  Card,
  Badge,
} from 'element-ui'
import VueMarkdownEditor from '@kangc/v-md-editor'
import '@kangc/v-md-editor/lib/style/base-editor.css'
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js'
import '@kangc/v-md-editor/lib/theme/style/vuepress.css'
import Prism from 'prismjs'
/* 2、v-md-editor 代码块关键字高亮  */
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js'
import '@kangc/v-md-editor/lib/theme/style/github.css'
// 引入所有语言包
import hljs from 'highlight.js'

/* 3、v-md-editor 二次封装  */
import mdEditor from '@/components/v-md-editor'
VueMarkdownEditor.use(vuepressTheme, { Prism })
Vue.use(VueMarkdownEditor)
VueMarkdownEditor.use(githubTheme, {
  Hljs: hljs,
})
Vue.use(VueMarkdownEditor)
Vue.component('MdEditor', mdEditor)

// 挂载到vue原型链上
Vue.prototype.$echarts = echarts
Vue.prototype.axios = axios
// eslint-disable-next-line no-extend-native
Date.prototype.format = function (fmt) {
  const o = {
    'M+': this.getMonth() + 1, // 月份
    'd+': this.getDate(), // 日
    'h+': this.getHours(), // 小时
    'm+': this.getMinutes(), // 分
    's+': this.getSeconds(), // 秒
    'q+': Math.floor((this.getMonth() + 3) / 3), // 季度
    S: this.getMilliseconds(), // 毫秒
  }
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  for (const k in o) {
    if (new RegExp('(' + k + ')').test(fmt)) {
      fmt = fmt.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length))
    }
  }
  return fmt
}
Vue.use(Button)
Vue.use(ButtonGroup)
Vue.use(Select)
Vue.use(Menu)
Vue.use(Submenu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Radio)
Vue.use(RadioGroup)
Vue.use(RadioButton)
Vue.use(Row)
Vue.use(Col)
Vue.use(Container)
Vue.use(Header)
Vue.use(Aside)
Vue.use(Main)
Vue.use(Footer)
Vue.use(Breadcrumb)
Vue.use(BreadcrumbItem)
Vue.use(Scrollbar)
Vue.use(Input)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Pagination)
Vue.use(Dialog)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Option)
Vue.use(OptionGroup)
Vue.use(Alert)
Vue.use(Carousel)
Vue.use(CarouselItem)
Vue.use(DatePicker)
Vue.use(Dropdown)
Vue.use(DropdownMenu)
Vue.use(DropdownItem)
Vue.use(Divider)
Vue.use(Loading.directive)
Vue.use(Card)
Vue.use(Badge)

Vue.prototype.$alert = MessageBox.alert
Vue.prototype.$loading = Loading.service
Vue.prototype.$msgbox = MessageBox
Vue.prototype.$confirm = MessageBox.confirm
Vue.prototype.$message = Message
ElementLocale.i18n((key, value) => i18n.t(key, value))

VueMarkdownEditor.use(vuepressTheme)

Vue.use(VueMarkdownEditor)
Vue.config.productionTip = false
new Vue({
  router,
  store,
  i18n,
  render: (h) => h(App),
}).$mount('#app')
