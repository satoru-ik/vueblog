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
                <el-form :model="user" status-icon :rules="rules" ref="registerFrom">
                    <el-form-item prop="username">
                        <el-input
                            prefix-icon="el-icon-user"
                            placeholder="请输入用户名"
                            v-model.trim="user.username"></el-input>
                    </el-form-item>
                    <el-form-item prop="nickname">
                        <el-input
                            prefix-icon="el-icon-user-solid"
                            placeholder="请输入昵称"
                            v-model.trim="user.nickname"></el-input>
                    </el-form-item>
                    <el-form-item prop="email">
                        <el-input
                            prefix-icon="el-icon-message"
                            placeholder="请输入邮箱"
                            v-model.trim="user.email"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input
                            prefix-icon="el-icon-lock"
                            placeholder="请输入密码"
                            type="password"
                            v-model.trim="user.password"></el-input>
                    </el-form-item>
                    <el-form-item prop="checkPass">
                        <el-input
                            prefix-icon="el-icon-lock"
                            placeholder="确认密码"
                            type="password"
                            v-model.trim="user.checkPass"></el-input>
                    </el-form-item>
                    <el-form-item prop="verify">
                        <el-input
                            prefix-icon="el-icon-lock"
                            placeholder="请输入验证码"
                            v-model.trim="user.verify">
                            <template #append>
                                <canvas ref="canvas" width="100" height="34" style="margin:0 -20px" @click="reDraw"></canvas>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-row>
                            <el-col :span="12">
                                <div class="grid-content">
                                    <el-link :underline="false" @click="routeHome">
                                        返回首页
                                    </el-link>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="align-right">
                                    <el-link :underline="false" @click="updatePass">
                                        忘记密码
                                    </el-link>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="align-right">
                                    <el-link :underline="false" @click="routeLogin">
                                        立即登录
                                    </el-link>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item>
                        <el-button class="full-width" type="primary" @click="register">
                            注册
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-main>
    </el-container>
</template>

<script>
import { onMounted, reactive, ref } from "vue"
import axios from "axios"
import { ElMessage, ElMessageBox } from 'element-plus'
import router from '../router/index'
import schema from 'async-validator'

export default {
    name: 'Register',
    setup() {
        const user = reactive({
            username: '',
            nickname: '',
            email: '',
            password: '',
            checkPass: '',
            verify: ''
        });
        let show_num = []
        const registerFrom = ref(null)
        const canvas = ref(null)

        // 验证字段规则
        let validateUsername = (rule, value, callback) => {
            if(value === "") {
                callback(new Error("用户名不能为空"))
            } else if(value.length < 4) {
                callback(new Error("用户名长度不能小于4位"))
            } else {
                // 从数据库确认用户名是否被占用
                callback()
            }
        }
        let validateNickname = (rule, value, callback) => {
            if(value === "") {
                callback(new Error("昵称不能为空"))
            } else {
                callback()
            }
        }
        let validatePass = (rule, value, callback) => {
            if(value === "") {
                callback(new Error("密码不能为空"))
            } else if(value.length < 6) {
                callback(new Error("密码长度不能小于6位"))
            } else {
                if(registerFrom.value.model.checkPass !== '') {
                    registerFrom.value.validateField('checkPass')
                }
                callback()
            }
        }
        let validatePass2 = (rule, value, callback) => {
            if(value === '') {
                callback(new Error('请再次输入密码'))
            } else if(value !== registerFrom.value.model.password) {
                callback(new Error('两次输入密码不一致!'))
            } else {
                callback()
            }
        }
        let validateNum = (rule, value, callback) => {
            let num = show_num.join("")
            if(value === '') {
                callback(new Error('验证码不能为空'))
            } else if(value !== num) {
                callback(new Error('验证码错误'))
            } else {
                callback()
            }
        }

        const rules = {
            username: [{
                validator: validateUsername,
                trigger: 'blur'
            }],
            nickname: [{
               validator: validateNickname,
               trigger: 'blur' 
            }],
            email: [{
                required: true, 
                message: '请输入邮箱地址', 
                trigger: 'blur'
            },
            {
                type: 'email',
                message: '请输入正确的邮箱地址',
                trigger: ['blur', 'change']
            }],
            password: [
                {
                    validator: validatePass,
                    trigger: ['blur', 'change'],
                },
            ],
            checkPass: [{
                validator: validatePass2,
                trigger: ['blur', 'change']
            }],
            verify: [{
                validator: validateNum,
                trigger: ['blur', 'change']
            }]
        };

        // 注册按钮事件
        function register() {
            registerFrom.value.validate((valid) => {
                if(valid) {
                    axios({
                        method: "POST",
                        url: "/web/user/register",
                        data: {
                            username: user.username,
                            nickname: user.nickname,
                            password: user.password,
                            email: user.email,
                        }
                    }).then(function(response){
                        if(response.data.code === 200) {
                            ElMessage({
                                message: response.data.msg,
                                type: 'success'
                            })
                        } else {
                            ElMessage({
                                message: response.data.msg,
                                type: 'error'
                            })
                        }
                    })
                } else {
                    return false
                }
                user.password = ''
                user.checkPass = ''
                reDraw()
            })
        }
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

        // 路由跳转
        function routeHome() {
            router.push({name: 'Home'})
        }
        function routeLogin() {
            router.push({name: 'Login'})
        }

        // 生成验证码图片
        function draw(show_num) {
            let canvas_width = canvas.value.width;
            let canvas_height = canvas.value.heigh;
            canvas.value.heigh = canvas_height;
            canvas.value.width = canvas_width;
            let context = canvas.value.getContext("2d"); //获取到canvas画图的环境，演员表演的舞台
            let sCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
            let aCode = sCode.split(",");
            let aLength = aCode.length; //获取到数组的长度
            for (let i = 0; i <= 3; i++) {
                let j = Math.floor(Math.random() * aLength); //获取到随机的索引值
                let deg = Math.random() * 30 * Math.PI / 180; //产生0~30之间的随机弧度
                let txt = aCode[j]; //得到随机的一个内容
                show_num[i] = txt.toLowerCase();
                let x = 10 + i * 20; //文字在canvas上的x坐标
                let y = 20 + Math.random() * 8; //文字在canvas上的y坐标
                context.font = "bold 23px 微软雅黑";
                context.translate(x, y);
                context.rotate(deg);
                context.fillStyle = randomColor();
                context.fillText(txt, 0, 0);
                context.rotate(-deg);
                context.translate(-x, -y);
            }
            for (let i = 0; i <= 5; i++) { //验证码上显示线条
                context.strokeStyle = randomColor();
                context.beginPath();
                context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
                context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
                context.stroke();
            }
            for (let i = 0; i <= 30; i++) { //验证码上显示小点
                context.strokeStyle = randomColor();
                context.beginPath();
                let x = Math.random() * canvas_width;
                let y = Math.random() * canvas_height;
                context.moveTo(x, y);
                context.lineTo(x + 1, y + 1);
                context.stroke();
            }
        }
        function randomColor() { //得到随机的颜色值
            let r = Math.floor(Math.random() * 256);
            let g = Math.floor(Math.random() * 256);
            let b = Math.floor(Math.random() * 256);
            return "rgb(" + r + "," + g + "," + b + ")";
        }
        // 重新生成验证码
        function reDraw() {
            draw(show_num)
        }
        // 数据挂载之后
        onMounted(() => {
            draw(show_num)
        })

        // 关闭警告
        schema.warning = function() {
            null
        }

        return {
            user, registerFrom, canvas,
            rules, register, updatePass, reDraw,
            routeHome, routeLogin
        };
    },
};
</script>

<style scoped>
.login-background {
    width: 100vw;
    height: 100vh;
    background-image: url(/api/img/download/img2.jpg);
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
    width:100%;
}
</style>