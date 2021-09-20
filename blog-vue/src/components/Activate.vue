<template>
    <div>
        验证邮箱页面
    </div>
</template>

<script>
import { ref } from 'vue'
import router from '../router/index'
import axios from 'axios'
import { ElMessage } from 'element-plus'

export default {
    name: "Activate",
    setup() {
        const route = router.currentRoute.value
        const token = ref()
        token.value = route.query.token

        // 发送激活请求
        function activate() {
            if(token.value !== undefined && token.value.length !== 0) {
                axios({
                    method: 'PUT',
                    url: '/web/user/activate',
                    data: {
                        token: token.value
                    }
                }).then(function(response) {
                    if(response.data.code === 200) {
                        ElMessage({
                            type: 'success',
                            message: '邮箱验证成功',
                            duration: 1000,
                            onClose: routeHome()
                        })
                    } else {
                        ElMessage({
                            type: 'error',
                            message: '验证信息已过期',
                            duration: 1000,
                            onClose: routeHome()
                        })
                    }
                })
            } else {
                routeHome()
            }
        }
        activate()

        // 路由跳转
        function routeHome() {
            router.push({ name: 'Home' })
        }
    }
}
</script>

<style>

</style>