<template>
    <el-container>
        <div class="login-background"></div>
        <el-header class="home-header">
            <el-row>
                <el-col :span="8">
                    <el-menu
                        :default-active="activeIndex"
                        class="el-menu-demo"
                        mode="horizontal"
                        background-color="#393d49"
                        text-color="#fff"
                        active-text-color="#ffd04b">
                        <el-menu-item index="1" @click="routeHome"><i class="el-icon-s-home"></i>首页</el-menu-item>
                        <el-menu-item index="2">历史版本</el-menu-item>
                        <el-menu-item index="3">当前版本新增功能</el-menu-item>
                        <el-menu-item index="4">计划新增功能</el-menu-item>
                    </el-menu>
                </el-col>
                <el-col :span="8">
                    <el-input v-model="search" placeholder="请输入搜索的内容">
                        <template #append>
                            <el-button icon="el-icon-search" @click="routeHome"></el-button>
                        </template>
                    </el-input>
                </el-col>
                <el-col :span="8">
                    <div class="menu-person">
                        <el-menu
                            mode="horizontal"
                            background-color="#393d49"
                            text-color="#fff"
                            active-text-color="#ffd04b"
                            v-if="currentUser.nickname !== undefined">
                            <el-sub-menu index="1">
                                <template #title>
                                    <el-avatar :src="currentUser.avatar"></el-avatar>
                                    {{currentUser.nickname}}
                                </template>
                                <el-menu-item index="1-1" @click="routeBackstage">个人中心</el-menu-item>
                                <el-menu-item index="1-2" @click="userQutit">退出登录</el-menu-item>
                            </el-sub-menu>
                        </el-menu>
                        <el-menu
                            mode="horizontal"
                            background-color="#393d49"
                            text-color="#fff"
                            active-text-color="#ffd04b"
                            v-else
                        >
                            <el-menu-item index="2-1" @click="routeLogin">登录</el-menu-item>
                            <el-menu-item index="2-2" @click="routeRegister">注册</el-menu-item>
                        </el-menu>
                    </div>
                </el-col>
            </el-row>
        </el-header>
        <el-main>
            <el-row>
                <el-col :xl="6" :lg="7">
                    <left-aside>
                    </left-aside>
                </el-col>
                <el-col :xl="12" :lg="12" class="minWidth">
                    <router-view></router-view>
                </el-col>
                <el-col :xl="6">
                </el-col>
            </el-row>
        </el-main>
        <el-footer height="30px">
            <span>
                Copyright © 2021
                <el-link :underline="false" type="primary" href="">
                    网站名
                </el-link>|
            </span>
            <span>
                网站已存活{{days}}天{{hours}}小时{{minutes}}分钟{{seconds}}秒|
            </span>
            <span>
                <el-link :underline="false" type="primary" href="https://beian.miit.gov.cn/">
                    备案号
                </el-link>
            </span>
        </el-footer>
    </el-container>
</template>

<script>
import { reactive, ref, provide, onBeforeMount, toRefs, watch } from "vue"
import router from "../router/index"
import axios from "axios"
import { ElMessage } from "element-plus"
import LeftAside from '../views/HomeLeftAside.vue'

export default {
    name: 'Home',
    components: { LeftAside },
    setup() {
        const route = router.currentRoute.value
        // 顶部导航栏
        const activeIndex = ref('1')
        const search = ref(route.query.search)
        const currentUser = reactive({})
        // 记录网站存活时间
        const startTime = ref(1632541678743)
        const currentTime = ref(new Date().getTime())
        const time = reactive({
            days: 0,
            hours: 0,
            minutes: 0,
            seconds: 0
        })
        // 每过1秒更新一下当前时间
        setInterval(() => {
            currentTime.value += 1000
        }, 1000)

        // 监听当前时间的变化,根据当前时间计算出网站存活的天数、小时数等等
        watch(currentTime, (newValue, oldValue) => {
            time.days = parseInt((newValue - startTime.value) / (1000 * 60 * 60 * 24)),
            time.hours = parseInt(((newValue - startTime.value) / (1000 * 60 * 60)) % 24),
            time.minutes = parseInt(((newValue - startTime.value) / (1000 * 60)) % 60),
            time.seconds = parseInt(((newValue - startTime.value) / 1000) % 60)
        })


        /**
         * 路由跳转
         *  */ 
        function routeHome() {
            router.push({
                name: 'Home',
                query: {
                    search: search.value === '' ? undefined : search.value
                }
            });
        }
        function routeLogin() {
            router.push({ name: 'Login' })
        }
        function routeRegister() {
            router.push({ name: 'Register' })
        }
        function routeBackstage() {
            router.push({ name: 'Backstage' })
        }

        // 获取当前用户
        function getCurrentUser() {
            axios({
                method: 'GET',
                url: '/web/user/currentUser'
            }).then(function(response) {
                if(response.data.code === 200) {
                    Object.assign(currentUser, response.data.data)
                }
            })
        }
        // 退出登录
        function userQutit() {
            axios({
                method: 'GET',
                url: '/web/user/quit'
            }).then(function(response) {
                if(response.data.code === 200) {
                    ElMessage({
                        type: 'success',
                        message: '退出成功!',
                        duration: 1000,
                        onClose: () => {
                            currentUser.uid = undefined
                            currentUser.nickname = undefined
                        }
                    })
                }
            })
        }

        onBeforeMount(() => {
            getCurrentUser()
            provide('currentUser', currentUser)
        })
        

        return {
            activeIndex, search, currentUser, ...toRefs(time),
            userQutit, routeBackstage,
            routeHome, routeLogin, routeRegister
        }
    }
}
</script>

<style scoped>
.el-container {
    height: 100%;
}
.el-header {
    background-color: #393d49;
    color: #fff;
    text-align: center;
    line-height: 60px;
}
.minWidth {
    min-width: 650px;
}

.login-background {
    width: 100vw;
    height: 100vh;
    background-image: url(/api/img/download/index.jpg);
    background-size: cover;
    background-position: right bottom;
    background-repeat: no-repeat;
    position: fixed;
    z-index: -1;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}
.home-header ul {
    border: 0;
}
.el-footer {
    padding: 0;
    background-color: #393d49;
    color: #fff;
    text-align: center;
}
.el-footer span {
    line-height: 30px;
    margin-right: 10px;
}
.el-footer a {
    font-size: 16px;
    vertical-align: baseline;
}
</style>

<style lang="less">
.el-popper {
    border: 0 !important;
}
.menu-person {
    text-align: right;
}
.menu-person ul {
    width: 240px;
    right: 0;
    position: absolute;
}
</style>
