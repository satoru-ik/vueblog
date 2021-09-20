<template>
    <el-form
        status-icon
        label-width="100px"
        class="demo-ruleForm"
    >
        <el-form-item label="密码">
            <el-input
                type="password"
                v-model="password"
                autocomplete="off"
            ></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
            <el-input
                type="password"
                v-model="checkPassword"
                autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="updatePassword">提交</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import { ref, reactive, toRefs } from "vue";
import router from "../router/index";
import axios from "axios";
import { ElMessage } from "element-plus";

export default {
    name: "ResetPassword",
    setup() {
        const route = router.currentRoute.value;
        const token = ref();
        const form = reactive({
            password: '',
            checkPassword: ''
        })
        token.value = route.query.token;

        // 发送修改密码请求
        function updatePassword() {
            if (form.password === form.checkPassword && form.password.length !== 0) {
                axios({
                    method: "PUT",
                    url: "/web/user/reset/password",
                    data: {
                        token: token.value,
                        password: form.password
                    },
                }).then(function (response) {
                    if (response.data.code === 200) {
                        ElMessage({
                            type: "success",
                            message: "密码修改成功",
                            duration: 1000,
                            onClose: routeHome(),
                        });
                    } else {
                        ElMessage({
                            type: "error",
                            message: "验证信息已过期",
                            duration: 1000,
                            onClose: routeHome(),
                        });
                    }
                });
            } else {
                ElMessage({
                    type: "error",
                    message: "两次密码不一致"
                });
                //routeHome();
            }
        }

        // 路由跳转
        function routeHome() {
            router.push({ name: "Home" });
        }

        return{
            ...toRefs(form),
            updatePassword
        }
    },
};
</script>

<style>
</style>