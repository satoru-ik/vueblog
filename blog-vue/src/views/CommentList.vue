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
                <el-form-item label="评论内容">
                    <el-input
                        placeholder="评论内容"
                        v-model="queryList.content"
                    ></el-input>
                </el-form-item>
                <el-form-item label="评论时间">
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
            <!-- <el-button type="primary" plain @click="dialogCreateUser = true"
                >添加</el-button
            > -->
            <el-table :data="commentList" border style="width: 100%">
                <el-table-column
                    fixed
                    prop="commentId"
                    label="id"
                    width="120"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="article.title"
                    label="评论所属文章"
                    width="180"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="commentContent"
                    label="评论内容"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="user.nickname"
                    label="昵称"
                    width="120"
                ></el-table-column>
                <el-table-column fixed label="头像" width="100">
                    <template #default="scope">
                        <el-avatar :size="50" :src="scope.row.user.avatar">
                        </el-avatar>
                    </template>
                </el-table-column>
                <el-table-column
                    fixed
                    prop="commentTime"
                    label="评论时间"
                    width="180"
                ></el-table-column>
                <el-table-column fixed label="操作">
                    <template #default="scope">
                        <el-button
                            type="primary"
                            icon="el-icon-edit"
                            circle
                            @click="editComment(scope.row)"
                        ></el-button>
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            circle @click="deleteComment(scope.row.commentId)"
                        ></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog
                title="评论内容"
                v-model="dialogUpdateComment"
                destroy-on-close
                width="40%"
            >
                <el-form :model="comment" status-icon>
                    <el-form-item>
                        <el-input
                            type="textarea"
                            prefix-icon="el-icon-chat-dot-round"
                            placeholder="评论内容"
                            v-model.trim="comment.commentContent"
                        ></el-input>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogUpdateComment = false"
                            >取 消</el-button
                        >
                        <el-button type="primary" @click="updateComment"
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
    name: "CommentList",
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
        const commentList = reactive([]);
        const dialogUpdateComment = ref(false);
        const comment = reactive({});

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
                getCommentList();
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
        function editComment(model) {
            dialogUpdateComment.value = true;
            Object.assign(comment, model);
            console.log(comment);
        }

        /**
         * 从后台获取评论列表数据
         */
        function getCommentList() {
            axios({
                method: "GET",
                url: "/web/comment/allList",
                params: queryList,
            }).then(function (response) {
                if (response.data.code === 200) {
                    commentList.length = 0
                    Object.assign(commentList, response.data.data);
                    total.value = response.data.total;
                } else {
                    ElMessage({
                        type: "error",
                        message: "获取数据失败",
                    });
                }
            });
        }
        // 更新评论内容
        function updateComment() {
            axios({
                method: "PUT",
                url: "/web/comment/update",
                data: comment,
            }).then(function (response) {
                console.log('res', response)
                if (response.data.code === 200) {
                    ElMessage({
                        type: "success",
                        message: "修改成功",
                        duration: 1000,
                        onClose: getCommentList(),
                    });
                    dialogUpdateComment.value = false;
                } else {
                    ElMessage({
                        type: "error",
                        message: "修改失败",
                    });
                }
            });
        }
        function deleteComment(commentId) {
            ElMessageBox.confirm("此操作将永久删除该评论, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    axios({
                        method: "DELETE",
                        url: "/web/comment/" + commentId,
                    }).then(function (response) {
                        if (response.data.code === 200) {
                            ElMessage({
                                type: "success",
                                message: "删除成功!",
                            });
                            getCommentList();
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
                name: "CommentList",
                query: queryList,
            });
        }

        return {
            queryList,
            total,
            defaultTime,
            time,
            commentList,
            dialogUpdateComment,
            comment,
            onSubmit,
            updeteTime,
            editComment,
            updateComment,
            deleteComment
        };
    },
};
</script>

<style scoped>
.pagination {
    text-align: center;
}
</style>