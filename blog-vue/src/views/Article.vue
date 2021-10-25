<template>
    <div class="main-div">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ name: 'Home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ name: 'Home', query: { type: article.articleType.articleTypeId }}">{{article.articleType.typeName}}</el-breadcrumb-item>
            <el-breadcrumb-item>{{article.title}}</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <div class="article-div">
        <div class="article-header">
            <div>
                <p class="article-font-title">{{article.title}}</p>
				<p>
					<span><i class="el-icon-date"></i>发布于{{article.startTime}}</span>
                    <span><i class="el-icon-time"></i>更新于{{article.updateTime}}</span>
				</p>
                <p>
					<span><i class="el-icon-view"></i>{{article.viewedNumber}}次阅读</span>
					<span><i class="el-icon-chat-dot-square"></i>{{article.commentNumber}}条评论</span>
					<span><i class="iconfont icon-good"></i>{{article.praiseNumber}}人点赞</span>
                </p>
            </div>
        </div>
        <div class="article-content article">
            <p v-html="article.content"></p>
        </div>
        <comment class="article" :articleId="id">
        </comment>
        <editor class="article" :articleId="id">
        </editor>
    </div>
</template>

<script>
import { reactive } from 'vue'
import axios from 'axios'
import router from '../router/index'
import comment from '../components/Comment'
import editor from '../components/UserEditor'

export default {
    name: 'Article',
    components: {comment, editor},
    setup() {
        const route = router.currentRoute.value
        const id = route.params.id
        const article = reactive({
            articleType: {},
            user: {}
        })

        // 获取文章内容
        function getArticle() {
            axios({
                method: "GET",
                url: "/web/article/" + id
            }).then(function (response){
                Object.assign(article, response.data.data)
                console.log("添加后", article.articleType)
            })
        }
        getArticle()

        return {
            id, article
        }
    }
}
</script>

<style scoped>
.main-div {
    background-color: #F6F6F6;
    border-radius: 10px;
    padding: 10px;
}
/** 文章div样式 */
.article-div {
    background-color: rgba(255,255,255,.8);
    border-radius: 10px;
    margin-top: 20px;
    overflow: hidden;
}
/** 文章头部样式 */
.article-header {
    height: 320px;
    background-image: url('/api/img/download/img246.jpg');
    width: 100%;
    background-size: cover;
    background-position: center center;
    position: relative;
}
.article-header div {
    z-index: 1;
    position: absolute;
    bottom: 0;
    height: 150px;
    width: 100%;
    color: #FFFFFF;
    padding: 20px;
    text-shadow: 2px 2px 10px #000;
}
.article-header span {
    padding-right: 20px;
}
.article-font-title {
	font-size: xx-large;
    margin-bottom: 0;
}
.text-padding {
	padding-left: 10px;
}
.article-content {
    min-height: 500px;
}
.article {
    padding: 20px;
}
</style>
