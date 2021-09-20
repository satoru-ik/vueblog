<template>
    <el-container>
        <el-header>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="标题">
                    <el-input
                        placeholder="标题"
                        v-model="queryList.title"
                    ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">查询</el-button>
                </el-form-item>
            </el-form>
        </el-header>
        <el-main>
            <el-button type="primary" plain @click="routeEditArticle()"
                >发布文章</el-button
            >
            <el-table :data="articleList" border style="width: 100%">
                <el-table-column
                    fixed
                    prop="articleId"
                    label="id"
                    width="100"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="title"
                    label="标题"
                    width="200"
                ></el-table-column>
                <el-table-column
                    fixed
                    label="文章分类"
                    width="120"
                >
                    <template #default="item">
                        <el-tag>{{item.row.articleType.typeName}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    fixed
                    prop="startTime"
                    label="发布时间"
                    width="180"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="updateTime"
                    label="更新时间"
                    width="180"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="viewedNumber"
                    label="阅读人次"
                    width="100"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="praiseNumber"
                    label="点赞次数"
                    width="100"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="commentNumber"
                    label="评论条数"
                    width="100"
                ></el-table-column>
                <el-table-column fixed label="操作">
                    <template #default="scope">
                        <el-button
                            type="primary"
                            icon="el-icon-edit"
                            circle
                            @click="routeEditArticle(scope.row.articleId)"
                        ></el-button>
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            circle @click="deleteArticle(scope.row.articleId)"
                        ></el-button>
                    </template>
                </el-table-column>
            </el-table>
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
    name: "ArticleList",
    setup() {
        const route = router.currentRoute.value.query;
        const queryList = reactive({
            title: route.title === undefined ? undefined : route.title,
            pageNum: route.pageNum === undefined ? 1 : Number(route.pageNum),
            pageSize:
                route.pageSize === undefined ? 20 : Number(route.pageSize),
        });
        const total = ref(0);
        const articleList = reactive([]);

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
                getArticleList();
            },
            { immediate: true, deep: true }
        );

        // 根据条件搜索文章,更新列表数据
        function onSubmit() {
            routePage();
        }

        /**
         * 从后台获取文章列表数据
         */
        function getArticleList() {
            axios({
                method: "GET",
                url: "/web/article/page",
                params: queryList,
            }).then(function (response) {
                if (response.data.code === 200) {
                    articleList.length = 0;
                    Object.assign(articleList, response.data.data);
                    total.value = response.data.total;
                } else {
                    ElMessage({
                        type: "error",
                        message: "获取数据失败",
                    });
                }
            });
        }
        function deleteArticle(articleId) {
            ElMessageBox.confirm("此操作将永久删除该文章, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    axios({
                        method: "DELETE",
                        url: "/web/article/" + articleId,
                    }).then(function (response) {
                        if (response.data.code === 200) {
                            ElMessage({
                                type: "success",
                                message: "删除成功!",
                            });
                            getArticleList();
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
                name: "ArticleList",
                query: queryList,
            });
        }
        function routeEditArticle(articleId) {
            router.push({
                name: 'EditArticle',
                query: {
                    articleId: articleId
                }
            })
        }

        return {
            queryList,
            total,
            articleList,
            onSubmit,
            deleteArticle,
            routeEditArticle
        };
    },
};
</script>

<style scoped>
.pagination {
    text-align: center;
}
</style>