<template>
    <el-container>
        <el-header>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="昵称">
                    <el-input
                        placeholder="昵称"
                        v-model="queryList.nickname"
                    ></el-input>
                </el-form-item>
                <el-form-item label="注册时间">
                    <el-date-picker
                        v-model="time"
                        type="datetimerange"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        :default-time="defaultTime"
                        :unlink-panels="true"
                        @change="updeteTime"
                    >
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">查询</el-button>
                </el-form-item>
            </el-form>
        </el-header>
        <el-main>
            <el-button type="primary" plain @click="dialogCreateUser = true"
                >添加</el-button
            >
            <el-table :data="userList" border style="width: 100%">
                <el-table-column
                    fixed
                    prop="uid"
                    label="id"
                    width="120"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="username"
                    label="用户名"
                    width="120"
                ></el-table-column>
                <el-table-column fixed label="头像" width="150">
                    <template #default="scope">
                        <el-avatar :size="50" :src="scope.row.avatar">
                        </el-avatar>
                    </template>
                </el-table-column>
                <el-table-column
                    fixed
                    prop="nickname"
                    label="昵称"
                    width="120"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="email"
                    label="邮箱"
                    width="180"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="registerTime"
                    label="注册时间"
                ></el-table-column>
                <el-table-column fixed label="操作">
                    <template #default="scope">
                        <el-button
                            type="primary"
                            icon="el-icon-edit"
                            circle
                            @click="editUserInfo(scope.row)"
                        ></el-button>
                        <el-button
                            type="warning"
                            icon="el-icon-lock"
                            circle
                            @click="editPassword(scope.row.uid)"
                        ></el-button>
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            circle @click="deleteUser(scope.row.uid)"
                        ></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog
                title="添加用户"
                v-model="dialogCreateUser"
                destroy-on-close
                width="30%"
            >
                <el-form :model="user" status-icon>
                    <el-form-item>
                        <el-input
                            prefix-icon="el-icon-user"
                            placeholder="请输入用户名"
                            v-model.trim="user.username"
                        ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input
                            prefix-icon="el-icon-user-solid"
                            placeholder="请输入昵称"
                            v-model.trim="user.nickname"
                        ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input
                            prefix-icon="el-icon-message"
                            placeholder="请输入邮箱"
                            v-model.trim="user.email"
                        ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input
                            prefix-icon="el-icon-lock"
                            placeholder="请输入密码"
                            type="password"
                            v-model.trim="user.password"
                        ></el-input>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogCreateUser = false"
                            >取 消</el-button
                        >
                        <el-button type="primary" @click="addUser"
                            >添 加</el-button
                        >
                    </span>
                </template>
            </el-dialog>
            <el-dialog
                title="用户信息"
                v-model="dialogUpdateUser"
                destroy-on-close
                width="30%"
            >
                <el-form :model="userInfo" status-icon>
                    <el-form-item>
                        <el-input
                            prefix-icon="el-icon-user"
                            placeholder="请输入用户名"
                            v-model.trim="userInfo.username"
                        ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input
                            prefix-icon="el-icon-user-solid"
                            placeholder="请输入昵称"
                            v-model.trim="userInfo.nickname"
                        ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input
                            prefix-icon="el-icon-message"
                            placeholder="请输入邮箱"
                            v-model.trim="userInfo.email"
                        ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input
                            prefix-icon="el-icon-lock"
                            placeholder="邮箱是否已验证"
                            v-model.trim="userInfo.emailVerify"
                        ></el-input>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogUpdateUser = false"
                            >取 消</el-button
                        >
                        <el-button type="primary" @click="updateUser"
                            >修 改</el-button
                        >
                    </span>
                </template>
            </el-dialog>
            <div class="pagination">
                <el-pagination
                    v-model:currentPage="queryList.pageNum"
                    :page-sizes="[1, 20, 50, 100]"
                    v-model:page-size="queryList.pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                >
                </el-pagination>
            </div>
        </el-main>
    </el-container>
</template>

<script>
import { reactive, ref, watch } from "vue";
import router from "../router/index";
import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";

export default {
    name: "UserList",
    setup() {
        const route = router.currentRoute.value.query;
        const time = ref("");
        const defaultTime = new Date(2000, 1, 1, 12, 0, 0);
        const queryList = reactive({
            nickname: route.nickname === undefined ? undefined : route.nickname,
            startTime:
                route.startTime === undefined ? undefined : route.startTime,
            endTime: route.endTime === undefined ? undefined : route.endTime,
            pageNum: route.pageNum === undefined ? 1 : Number(route.pageNum),
            pageSize:
                route.pageSize === undefined ? 20 : Number(route.pageSize),
        });
        const total = ref(0);
        const userList = reactive([]);
        const dialogCreateUser = ref(false);
        const dialogUpdateUser = ref(false);
        const user = reactive({
            username: "",
            nickname: "",
            email: "",
            passwrod: "",
        });
        const userInfo = reactive({});

        // 监听pageNum和pageSize的变化更新路由
        watch(
            [() => queryList.pageNum, () => queryList.pageSize],
            () => {
                routePage();
            },
            { immediate: false, deep: true }
        );
        // 监听route变化,更新列表数据
        watch(
            () => router.currentRoute.value,
            () => {
                getUserList();
            },
            { immediate: true, deep: true }
        );

        // 选择日期后将日期更新到queryList中
        function updeteTime() {
            queryList.startTime = time.value[0];
            queryList.endTime = time.value[1];
        }
        // 根据条件搜索用户,更新列表数据
        function onSubmit() {
            routePage();
        }
        function editUserInfo(model) {
            dialogUpdateUser.value = true;
            Object.assign(userInfo, model);
            console.log(userInfo);
        }
        function editPassword(uid) {
            ElMessageBox.prompt("请输入密码", "提示", {
                confirmButtonText: "修改密码",
                cancelButtonText: "取消",
                inputType: "password",
            })
                .then(({ value }) => {
                    console.log("uid:", uid);
                    let password = value;
                    axios({
                        method: "PUT",
                        url: "/web/user/update/password",
                        data: {
                            uid: uid,
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

        /**
         * 从后台获取用户列表数据
         */
        function getUserList() {
            axios({
                method: "GET",
                url: "/web/user/page",
                params: queryList,
            }).then(function (response) {
                if (response.data.code === 200) {
                    userList.length = 0;
                    Object.assign(userList, response.data.data);
                    total.value = response.data.total;
                } else {
                    ElMessage({
                        type: "error",
                        message: "获取数据失败",
                    });
                }
            });
        }
        // 添加用户
        function addUser() {
            axios({
                method: "POST",
                url: "/web/user/insert",
                data: user,
            }).then(function (response) {
                if (response.data.code === 200) {
                    ElMessage({
                        type: "success",
                        message: response.data.msg,
                        duration: 1000,
                        onClose: getUserList(),
                    });
                    dialogCreateUser.value = false;
                } else {
                    ElMessage({
                        type: "error",
                        message: response.data.msg,
                    });
                }
            });
        }
        // 更新用户数据
        function updateUser() {
            axios({
                method: "PUT",
                url: "/web/user/update/user",
                data: userInfo,
            }).then(function (response) {
                if (response.data.code === 200) {
                    ElMessage({
                        type: "success",
                        message: "修改成功",
                        duration: 1000,
                        onClose: getUserList(),
                    });
                    dialogUpdateUser.value = false;
                } else {
                    ElMessage({
                        type: "error",
                        message: "修改失败",
                    });
                }
            });
        }
        function deleteUser(uid) {
            ElMessageBox.confirm("此操作将永久删除该用户, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    axios({
                        method: "DELETE",
                        url: "/web/user/" + uid,
                    }).then(function (response) {
                        if (response.data.code === 200) {
                            ElMessage({
                                type: "success",
                                message: "删除成功!",
                            });
                            getUserList();
                        }
                    });
                })
                .catch(() => {
                    ElMessage({
                        type: "info",
                        message: "已取消删除",
                    });
                });
        }

        /**
         * 路由跳转
         **/
        function routePage() {
            router.push({
                name: "UserList",
                query: queryList,
            });
        }

        return {
            queryList,
            total,
            defaultTime,
            time,
            userList,
            dialogCreateUser,
            dialogUpdateUser,
            user,
            userInfo,
            onSubmit,
            updeteTime,
            addUser,
            editUserInfo,
            updateUser,
            editPassword,
            deleteUser
        };
    },
};
</script>

<style scoped>
.pagination {
    text-align: center;
}
</style>