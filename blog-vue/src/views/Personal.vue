<template>
    <el-card shadow="hover">
        <template #header>
            <div class="card-header">
                <span>个人资料</span>
            </div>
        </template>
        <el-form
            label-width="80px"
            :model="currentUser"
        >
            <el-form-item label="用户名">
                <el-input v-model="currentUser.username" disabled></el-input>
            </el-form-item>
            <el-form-item label="头像">
                <el-avatar :size="100" :src="currentUser.avatar"></el-avatar>
                <el-upload
                    class="upload-demo"
                    action="/api/img/upload"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                >
                    <el-button size="small" type="primary">点击上传</el-button>
                    <template #tip>
                        <div class="el-upload__tip">
                            只能上传 jpg/png 文件，且不超过 500kb
                        </div>
                    </template>
                </el-upload>
            </el-form-item>
            <el-form-item label="昵称">
                <el-input v-model="currentUser.nickname"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="currentUser.email"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">保存</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
import { reactive } from "vue";
import axios from "axios";
import { ElMessage } from 'element-plus';

export default {
    name: "Personal",
    setup() {
        const currentUser = reactive({});

        // 获取当前用户信息
        function getCurrentUser() {
            axios({
                method: "GET",
                url: "/web/user/currentUser",
            }).then(function (response) {
                if (response.data.code === 200) {
                    Object.assign(currentUser, response.data.data);
                    console.log("Home-currentUser", response);
                }
            });
        }
        getCurrentUser()

        // 上传头像成功后刷新网页
        function handleAvatarSuccess(res, file) {
            let avatar = res.data.imageUrl
            axios({
                method: 'PUT',
                url: '/web/user/update/avatar',
                data: {
                    avatar: avatar
                }
            }).then(function(response) {
                if(response.data.code === 200) {
                    ElMessage({
                        type: 'success',
                        message: '保存成功',
                        duration: 1000,
                        onClose: () => {
                            location.reload()
                        }
                    })
                } else {
                    ElMessage({
                        type: 'error',
                        message: '保存失败'
                    })
                }
            })
        }
        // 上传头像格式
        function beforeAvatarUpload(file) {
            const isJPG = file.type === "image/jpeg";
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error("上传头像图片只能是 JPG 格式!");
            }
            if (!isLt2M) {
                this.$message.error("上传头像图片大小不能超过 2MB!");
            }
            return isJPG && isLt2M;
        }
        // 保存修改后的信息
        function onSubmit() {
            axios({
                method: 'PUT',
                url: '/web/user/update/user',
                data: currentUser
            }).then(function(response) {
                if(response.data.code === 200) {
                    ElMessage({
                        type: 'success',
                        message: '保存成功'
                    })
                } else {
                    ElMessage({
                        type: 'error',
                        message: '保存失败'
                    })
                }
            })
        }

        return {
            currentUser,
            beforeAvatarUpload,
            handleAvatarSuccess,
            onSubmit
        };
    },
};
</script>

<style>
</style>