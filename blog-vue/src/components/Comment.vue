<template>
    <div>
        <div class="comments-list-title">
            <h3>
                Comments
                <el-divider direction="vertical"></el-divider>
                <span>{{total}}条评论</span>
            </h3>
        </div>
        <div v-for="comment in comments" :key="comment.commentId">
            <el-container>
                <el-aside width="50px">
                    <a href="javascript:;">
                        <el-avatar :size="45" :src='comment.user.avatar'>
                        </el-avatar>
                    </a>
                </el-aside>
                <el-container class="comment-header">
                    <el-header height="20px">
                        <el-link href="javascript:;" type="danger" :underline="false">{{comment.user.nickname}}</el-link>
                    </el-header>
                    <el-main>
                        发布于{{comment.commentTime}}
                    </el-main>
                </el-container>
            </el-container>
            <div class="comment-content">
                <p v-html="comment.commentContent"></p>
            </div>
        </div>
        <div class="pagination">
            <el-pagination background layout="prev, pager, next" v-model:currentPage="currentPage" :total="total" :default-page-size="10">
            </el-pagination>
        </div>
    </div>
    
</template>

<script>
import { reactive, toRefs } from '@vue/reactivity'
import axios from 'axios'
import { watch } from '@vue/runtime-core'

export default {
    name: 'Comment',
    props: ['articleId'],
    setup(props, context) {
        const pagination = reactive({
            articleId: Number(props.articleId),
            currentPage: 1,
            total: 0
        })
        const comments = reactive([{
            user: {},
            commentTreeList: [{}]
        }])
        console.log('pagination:', pagination)
        console.log('context:', context)

        /**
        * 监听currentPage属性变化
        */
        watch(() => pagination.currentPage, () => {
            console.log('监听评论列表', pagination)
            getCommentList()
        }, {immediate: true, deep: true})

        // 获取当前页的评论
        function getCommentList() {
            axios({
                method: 'GET',
                url: '/web/comment/list',
                params: {
                    pageNum: pagination.currentPage,
                    articleId: pagination.articleId
                }
            }).then(function(response) {
                comments.length = 0
                Object.assign(comments, response.data.data)
                pagination.total = response.data.total
                console.log('评论请求返回', response)
                console.log('评论列表',comments)
            })
        }

        return {
            ...toRefs(pagination), comments
        }
    }
}
</script>

<style scoped>
.comments-list-title {
    color: #909399;
    padding-bottom: 40px;
}
.comments-list-title h3 {
    font-weight: 400;
}
.comments-list-title span {
    font-size: 13px;
}
.comment-header .el-header {
    padding: 0;
}
.comment-header .el-link {
    font-weight: 600;
    font-size: 15px;
}
.comment-header .el-main {
    padding: 5px 0 0 0;
    color: #909399;
    font-size: 12px;
}
.comment-content {
    line-height: 32px;
    color: #63686d;
    border-bottom: 1px solid rgba(0,0,0,.05);
    position: relative;
    min-height: 100px;
}
.pagination {
    text-align: center;
}
</style>