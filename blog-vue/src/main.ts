import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios"
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@element-plus/icons'
import zhCn from 'element-plus/es/locale/lang/zh-cn'


const app = createApp(App)
// 全局配置axios
app.config.globalProperties.$axios = axios
// 设置axios每次发送请求携带cookie
axios.defaults.withCredentials = true
// 全局的 axios 默认值
// axios.defaults.baseURL = 'http://localhost:8081';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
app.use(router)
app.use(ElementPlus, { locale: zhCn })
app.mount('#app')
