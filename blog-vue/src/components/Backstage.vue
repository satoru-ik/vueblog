<template>
    <el-container>
        <el-aside :width="asideWidth">
            <el-menu
                default-active="1-4-1"
                class="el-menu-vertical-demo"
                :collapse="isCollapse"
                background-color="#2F4056"
                text-color="#fff"
                active-text-color="#ffd04b"
            >
                <el-menu-item index="0" @click="routeHome">
                    <i class="el-icon-s-home"></i>
                    <template #title>返回首页</template>
                </el-menu-item>
                <el-menu-item index="1" @click="routePersonal">
                    <i class="el-icon-user"></i>
                    <template #title>个人中心</template>
                </el-menu-item>
                <el-menu-item index="2" @click="routeUserList" v-if="currentUser.role === 'admin'">
                    <i class="iconfont icon-users"></i>
                    <template #title>用户管理</template>
                </el-menu-item>
                <el-sub-menu index="3" v-if="currentUser.role === 'admin'">
                    <template #title>
                        <i class="iconfont icon-article"></i>
                        <span>文章管理</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item index="3-1" @click="routeArticleList">文章列表</el-menu-item>
                        <el-menu-item index="3-2" @click="routeArticleTypeList">分类管理</el-menu-item>
                    </el-menu-item-group>
                </el-sub-menu>
                <el-menu-item index="4" @click="routeCommentList" v-if="currentUser.role === 'admin'">
                    <i class="el-icon-chat-dot-square"></i>
                    <template #title>评论管理</template>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <el-container>
            <el-header height="50px" class="main-header">
                <div>
                    <i
                        :class="[
                            'iconfont',
                            'icon',
                            isCollapse
                                ? 'icon-hamburger'
                                : 'icon-hamburger-active',
                        ]"
                        @click="isCollapse = !isCollapse"
                    ></i>
                </div>
                <div class="user-info">
                    <el-avatar :src="currentUser.avatar"></el-avatar>
                    <el-dropdown>
                        <span class="el-dropdown-link">
                            {{ currentUser.nickname }}
                            <i class="el-icon-arrow-down el-icon--right"></i>
                        </span>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item @click="editPassword">修改密码</el-dropdown-item>
                                <el-dropdown-item @click="userQutit"
                                    >退出登录</el-dropdown-item
                                >
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </div>
            </el-header>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import { reactive, ref, watch } from "vue"
import axios from "axios"
import router from '../router/index'
import { ElMessage, ElMessageBox } from 'element-plus';

export default {
    name: "Backstage",
    setup() {
        const isCollapse = ref(false)
        const asideWidth = ref("200px")
        const currentUser = reactive({})

        // 监听侧边栏折叠事件
        watch(isCollapse, (newValue, oldValue) => {
            console.log(newValue, "?");
            if (newValue) {
                asideWidth.value = "null";
            } else {
                asideWidth.value = "200px";
            }
        });

        // 获取当前用户
        function getCurrentUser() {
            axios({
                method: "GET",
                url: "/web/user/currentUser",
            }).then(function (response) {
                if (response.data.code === 200) {
                    Object.assign(currentUser, response.data.data);
                    console.log("Home-currentUser", response);
                }
                if(currentUser.uid === undefined) {
                    router.push({ name: 'Home' })
                }
            });
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
                        onClose: routeHome()
                    })
                }
            })
        }
        // 修改密码
        function editPassword() {
            ElMessageBox.prompt("请输入密码", "提示", {
                confirmButtonText: "修改密码",
                cancelButtonText: "取消",
                inputType: "password",
            })
                .then(({ value }) => {
                    console.log("uid:", currentUser.uid);
                    let password = value;
                    axios({
                        method: "PUT",
                        url: "/web/user/update/password",
                        data: {
                            uid: currentUser.uid,
                            password: password,
                        },
                    }).then(function (response) {
                        if (response.data.code === 200) {
                            ElMessage({
                                type: "success",
                                message: "修改密码成功",
                            });
                        } else {
                            ElMessage({
                                type: "error",
                                message: "修改密码失败",
                            });
                        }
                    });
                })
                .catch(() => {
                    ElMessage({
                        type: "info",
                        message: "取消输入",
                    });
                });
        }

        getCurrentUser();

        /**
         * 路由跳转
         * */ 
        function routeHome() {
            router.push({ name: 'Home'})
        }
        function routePersonal() {
            router.push({ name: 'Personal' })
        }
        function routeUserList() {
            router.push({ name: 'UserList' })
        }
        function routeArticleList() {
            router.push({ name: 'ArticleList' })
        }
        function routeArticleTypeList() {
            router.push({ name: 'ArticleTypeList' })
        }
        function routeCommentList() {
            router.push({ name: 'CommentList' })
        }

        return {
            isCollapse,
            asideWidth,
            currentUser,
            editPassword ,userQutit, routeHome, routePersonal, routeUserList, routeArticleList, routeArticleTypeList, routeCommentList
        };
    },
};
</script>

<style lang="less" scoped>
.el-container {
    height: 100%;
}
.iconfont {
    font-size: 1.5em;
    margin-right: 5px;
}
.el-aside .el-menu {
    height: 100%;
    border: 0;
}
.main-header {
    border-bottom: 1px solid #d8dce5;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
    display: flex;
    line-height: 50px;
}
.main-header div {
    display: flex;
}
.user-info {
    flex: 1;
    justify-content: flex-end;
    align-items: center;
}
.user-info .el-avatar {
    margin-right: 10px;
}
.user-info span {
    line-height: 50px;
}
.el-icon-chat-dot-square {
    margin-left: -2px;
}
</style>