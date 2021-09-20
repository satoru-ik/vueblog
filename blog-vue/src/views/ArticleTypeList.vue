<template>
    <el-container>
        <el-header>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="类型名">
                    <el-input
                        placeholder="类型名"
                        v-model="queryList.typeName"
                    ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">查询</el-button>
                </el-form-item>
            </el-form>
        </el-header>
        <el-main>
            <el-button type="primary" plain @click="dialogCreateArticleType = true"
                >添加</el-button
            >
            <el-table :data="articleTypeList" border style="width: 100%">
                <el-table-column
                    fixed
                    prop="articleTypeId"
                    label="id"
                    width="120"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="typeName"
                    label="类型名"
                    width="180"
                ></el-table-column>
                <el-table-column
                    fixed
                    prop="parentNode"
                    label="父节点id"
                    width="120"
                ></el-table-column>
                <el-table-column fixed label="操作">
                    <template #default="scope">
                        <el-button
                            type="primary"
                            icon="el-icon-edit"
                            circle
                            @click="editArticleType(scope.row)"
                        ></el-button>
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            circle @click="deleteArticleType(scope.row.articleTypeId)"
                        ></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog
                title="添加文章分类"
                v-model="dialogCreateArticleType"
                destroy-on-close
                width="20%"
            >
                <el-form :model="articleType" status-icon>
                    <el-form-item>
                        <el-input
                            placeholder="请输入类型名"
                            v-model.trim="articleType.typeName"
                        ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-select v-model="articleType.parentNode" placeholder="请选择父节点">
                            <el-option v-for="item in parentList" :key="item.articleTypeId" 
                                :label="item.typeName" :value="item.articleTypeId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogCreateArticleType = false"
                            >取 消</el-button
                        >
                        <el-button type="primary" @click="addArticleType"
                            >添 加</el-button
                        >
                    </span>
                </template>
            </el-dialog>
            <el-dialog
                title="文章分类信息"
                v-model="dialogUpdateArticleType"
                destroy-on-close
                width="20%"
            >
                <el-form :model="articleType" status-icon>
                    <el-form-item>
                        <el-input
                            prefix-icon="el-icon-user"
                            placeholder="请输入类型名"
                            v-model.trim="articleType.typeName"
                        ></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-select v-model="articleType.parentNode" placeholder="请选择父节点">
                            <el-option v-for="item in parentList" :key="item.articleTypeId" 
                                :label="item.typeName" :value="item.articleTypeId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogUpdateArticleType = false"
                            >取 消</el-button
                        >
                        <el-button type="primary" @click="updateArticleType"
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
    name: "ArticleTypeList",
    setup() {
        const route = router.currentRoute.value.query;
        const articleType = reactive({
            articleTypeId: '',
            typeName: '',
            parentNode: ''
        })
        const queryList = reactive({
            typeName: route.typeName,
            pageNum: route.pageNum === undefined ? 1 : Number(route.pageNum),
            pageSize: route.pageSize === undefined ? 20 : Number(route.pageSize),
        })
        const total = ref(0)
        const articleTypeList = reactive([])
        const parentList = reactive([{
            articleTypeId: -1,
            typeName: '成为父节点'
        }])
        const dialogCreateArticleType = ref(false)
        const dialogUpdateArticleType = ref(false)

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
                getArticleTypeList();
                getParentList();
            },
            { immediate: true, deep: true }
        );

        // 根据条件搜索文章类型,更新列表数据
        function onSubmit() {
            routePage();
        }

        // 打开编辑文章分类页面
        function editArticleType(model) {
            dialogUpdateArticleType.value = true
            Object.assign(articleType, model)
        }
        

        /**
         * 从后台获取文章类型列表数据
         */
        function getArticleTypeList() {
            axios({
                method: "GET",
                url: "/web/articleType/list",
                params: queryList,
            }).then(function (response) {
                if (response.data.code === 200) {
                    articleTypeList.length = 0;
                    Object.assign(articleTypeList, response.data.data);
                    total.value = response.data.total;
                } else {
                    ElMessage({
                        type: "error",
                        message: "获取数据失败",
                    });
                }
            });
        }
        // 获取父节点列表
        function getParentList() {
            axios({
                method: "GET",
                url: "/web/articleType/parentList"
            }).then(function (response) {
                if (response.data.code === 200) {
                    parentList.push(...response.data.data)
                } else {
                    ElMessage({
                        type: "error",
                        message: "获取数据失败",
                    });
                }
            });
        }
        // 添加文章类型
        function addArticleType() {
            console.log()
            axios({
                method: "POST",
                url: "/web/articleType/insert",
                data: articleType,
            }).then(function (response) {
                if (response.data.code === 200) {
                    ElMessage({
                        type: "success",
                        message: '添加成功',
                        duration: 1000,
                        onClose: getArticleTypeList(),
                    });
                    dialogCreateArticleType.value = false;
                } else {
                    ElMessage({
                        type: "error",
                        message: '添加失败',
                    });
                }
            });
        }
        // 更新文章类型数据
        function updateArticleType() {
            axios({
                method: "PUT",
                url: "/web/articleType/update",
                data: articleType,
            }).then(function (response) {
                if (response.data.code === 200) {
                    ElMessage({
                        type: "success",
                        message: "修改成功",
                        duration: 1000,
                        onClose: getArticleTypeList(),
                    });
                    dialogUpdateArticleType.value = false;
                } else {
                    ElMessage({
                        type: "error",
                        message: "修改失败",
                    });
                }
            });
        }
        // 删除文章类型
        function deleteArticleType(articleTypeId) {
            ElMessageBox.confirm("此操作将永久删除该类型, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    axios({
                        method: "DELETE",
                        url: "/web/articleType/" + articleTypeId,
                    }).then(function (response) {
                        if (response.data.code === 200) {
                            ElMessage({
                                type: "success",
                                message: "删除成功!",
                            });
                            getArticleTypeList();
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
                name: "ArticleTypeList",
                query: queryList,
            });
        }

        return {
            queryList,
            total,
            articleTypeList,
            parentList,
            dialogCreateArticleType,
            dialogUpdateArticleType,
            articleType,
            onSubmit,
            addArticleType,
            editArticleType,
            updateArticleType,
            deleteArticleType
        };
    },
};
</script>

<style scoped>
.pagination {
    text-align: center;
}
</style>