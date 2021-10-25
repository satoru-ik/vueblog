<template>
    <el-container>
        <div class="login-background"></div>
        <el-main>
            <el-card class="box-card login-div">
                <template #header>
                    <div class="card-header">
                        <span>个人博客</span>
                    </div>
                </template>
                <el-form
                    :model="user"
                    status-icon
                    :rules="rules"
                    ref="loginFrom"
                >
                    <el-form-item prop="username">
                        <el-input
                            prefix-icon="el-icon-user"
                            placeholder="请输入用户名"
                            v-model.trim="user.username"
                        ></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input
                            prefix-icon="el-icon-lock"
                            placeholder="请输入密码"
                            type="password"
                            v-model.trim="user.password"
                        ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-row>
                            <el-col :span="12">
                                <div class="grid-content">
                                    <el-checkbox v-model="checkTime"
                                        >7天免登录</el-checkbox
                                    >
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="align-right">
                                    <el-link
                                        :underline="false"
                                        @click="routeHome"
                                    >
                                        返回首页
                                    </el-link>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="align-right">
                                    <el-link
                                        :underline="false"
                                        @click="updatePass"
                                    >
                                        忘记密码
                                    </el-link>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item>
                        <el-row justify="space-between">
                            <el-col :span="10">
                                <el-button
                                    class="full-width"
                                    type="primary"
                                    @click="login"
                                >
                                    登录
                                </el-button>
                            </el-col>
                            <el-col :span="10">
                                <div style="text-align: right">
                                    <el-button
                                        class="full-width"
                                        @click="routeRegister"
                                    >
                                        立即注册
                                    </el-button>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-main>
    </el-container>
</template>

<script>
import { reactive, ref } from "vue";
import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";
import router from "../router/index";

export default {
    name: "Login",
    setup() {
        const user = reactive({
            username: "",
            password: "",
        });
        const checkTime = ref(false);
        const loginFrom = ref("null");

        // 验证字段规则
        let validateUsername = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("用户名不能为空"));
            } else {
                callback();
            }
        };
        let validatePass = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("密码不能为空"));
            } else {
                callback();
            }
        };
        const rules = {
            username: [
                {
                    validator: validateUsername,
                    trigger: "blur",
                },
            ],
            password: [
                {
                    validator: validatePass,
                    trigger: "blur",
                },
            ],
        };

        // 忘记密码
        function updatePass() {
            ElMessageBox.prompt("请输入账号所对应的邮箱,你将会收到一条重置密码的邮件", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
                inputErrorMessage: "邮箱格式不正确",
            })
                .then(({ value }) => {
                    // 调用发送邮件的接口
                    axios({
                        method: 'GET',
                        url: '/web/user/reset',
                        params: {
                            email: value
                        }
                    }).then(function(response) {
                        if(response.data.code === 200) {
                            ElMessage({
                                type: "success",
                                message: "重置邮件发送成功",
                            })
                        } else {
                            ElMessage({
                                type: "error",
                                message: "该邮箱所对应的账号不存在",
                            })
                        }
                    })
                    
                })
                .catch(() => {
                    ElMessage({
                        type: "info",
                        message: "取消输入",
                    });
                });
        }

        // 登录按钮
        function login() {
            loginFrom.value.validate((valid) => {
                if (valid) {
                    let time = checkTime.value ? 1 : 7;
                    axios({
                        method: "POST",
                        url: "/web/user/login",
                        params: {
                            time: time
                        },
                        data: {
                            username: user.username,
                            password: user.password
                        },
                    }).then(function (response) {
                        console.log("返回是", response);
                        if (response.data.code === 200) {
                            ElMessage({
                                message: response.data.msg,
                                type: "success",
                                duration: 1000,
                                onClose: () => routeHome()
                            })
                        } else {
                            ElMessage({
                                message: response.data.msg,
                                type: "error",
                            })
                        }
                    });
                } else {
                    return false;
                }
                user.password = ''
            });
        }

        // 路由跳转
        function routeHome() {
            router.push({ name: "Home" });
        }
        function routeRegister() {
            router.push({ name: "Register" });
        }

        return {
            user,checkTime,
            loginFrom,rules,
            routeHome, updatePass, login, routeRegister
        };
    },
};
</script>

<style scoped>
.login-background {
    width: 100vw;
    height: 100vh;
    background-image: url(/api/img/download/img1.jpg);
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
.login-div {
    margin-top: 15vh;
    margin-left: auto;
    margin-right: auto;
    width: 360px;
    padding: 10px;
}
.align-right {
    text-align: right;
}
.full-width {
    width: 100%;
}
</style>