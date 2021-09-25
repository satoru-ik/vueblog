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

<style lang="less">
// 富文本编辑器样式
body{word-break:break-word}.tb-alert{padding:10px 15px;border-radius:6px;border:1px solid #e9eaec;background-color:#f8f8f9;margin-top:1em;margin-bottom:1em}.tb-alert.tb-alert-primary{border-color:rgba(18,150,219,0.3);background-color:rgba(18,150,219,0.15)}.tb-alert.tb-alert-primary.tb-alert-fill{color:#fff;background-color:#1296db}.tb-alert.tb-alert-success{border-color:rgba(21,189,154,0.3);background-color:rgba(21,189,154,0.15)}.tb-alert.tb-alert-success.tb-alert-fill{color:#fff;background-color:#15bd9a}.tb-alert.tb-alert-info{border-color:rgba(106,209,236,0.3);background-color:rgba(106,209,236,0.15)}.tb-alert.tb-alert-info.tb-alert-fill{color:#fff;background-color:#6ad1ec}.tb-alert.tb-alert-warning{border-color:rgba(255,153,0,0.3);background-color:rgba(255,153,0,0.15)}.tb-alert.tb-alert-warning.tb-alert-fill{color:#fff;background-color:#f90}.tb-alert.tb-alert-danger{border-color:rgba(231,79,94,0.3);background-color:rgba(231,79,94,0.15)}.tb-alert.tb-alert-danger.tb-alert-fill{color:#fff;background-color:#E74F5E}.tb-alert.tb-alert-dark{border-color:rgba(73,80,96,0.3);background-color:rgba(73,80,96,0.15)}.tb-alert.tb-alert-dark.tb-alert-fill{color:#fff;background-color:#495060}.tb-alert.tb-alert-gray{border-color:rgba(187,190,196,0.3);background-color:rgba(187,190,196,0.15)}.tb-alert.tb-alert-gray.tb-alert-fill{color:#fff;background-color:#bbbec4}.tb-alert-fill code{background-color:rgba(255,255,255,0.2);border:none}tb-katex,.katex-display,.katex,.katex-html{display:inline-block}tb-katex{margin-left:0.5em;margin-right:0.5em}tb-step{display:flex}.tb-step-item{position:relative;flex:1}.tb-step-item:last-child .tb-step-item-line{display:none}.tb-step-item.tb-complete .tb-step-item-line{border-top-color:#15bd9a}.tb-step-item.tb-complete .tb-step-item-icon{background-color:#15bd9a}.tb-step-item.tb-current .tb-step-item-line{border-top-style:dashed}.tb-step-item.tb-current .tb-step-item-icon{background-color:#1296db}.tb-step-item.tb-waiting .tb-step-item-line{border-top-style:dashed}.tb-step-item.tb-waiting .tb-step-item-icon{background-color:#bbbec4}.tb-step-item.tb-waiting .tb-step-item-content{opacity:.8}.tb-step-item-header{position:relative;margin-bottom:1em}.tb-step-item-icon{width:1.6em;height:1.6em;border-radius:50%;position:relative;text-align:center;line-height:1.6em;color:#fff;font-weight:500}.tb-step-item-line{position:absolute;top:50%;left:0;right:0;border-top:1px solid #dddee1}.tb-step-item-content{padding-right:15px}.tb-step-title{font-weight:500;margin:0;font-size:1.2em}.tb-step-title>small{font-weight:normal;opacity:.8}.tb-step-content{font-weight:normal;margin:0}.tb-step-item-add{position:absolute;right:0;top:0;display:none;cursor:pointer}.tb-step-item-add:hover{transform:scale(1.2)}.tb-step-item-add:after{content:"+"}.tb-step-item:hover .tb-step-item-add{display:block}.tb-step-item-icon{cursor:pointer}tb-progress{margin:2em 0 1em;background-color:#eee;border-radius:3px;height:6px;display:block;position:relative}tb-progress>div{height:100%;border-radius:inherit;background-color:#aaa;position:relative}tb-progress>span{position:absolute;bottom:100%;font-size:12px}.tb-progress-value{position:absolute;right:0;bottom:100%;background-color:#000;color:#fff;padding:3px 8px;border-radius:5px;font-size:13px;transform:translateX(50%) translateY(-4px)}.tb-progress-value:after{content:"";position:absolute;top:100%;left:50%;margin-left:-4px;width:0;height:0;border-width:4px;border-style:solid;border-color:#000 transparent transparent}.tb-progress-min{left:0}.tb-progress-max{right:0}tb-progress[type=primary]>div{background-color:#1296db}tb-progress[type=info]>div{background-color:#6ad1ec}tb-progress[type=success]>div{background-color:#15bd9a}tb-progress[type=warning]>div{background-color:#ff9900}tb-progress[type=danger]>div{background-color:#E74F5E}tb-progress[type=dark]>div{background-color:#495060}tb-progress[type=gray]>div{background-color:#bbbec4}tb-timeline{display:block;padding-top:1em;padding-left:5px}.tb-timeline-item{display:block;position:relative;padding-left:1.5em;padding-bottom:0.5em;opacity:.76}.tb-timeline-item:first-of-type>.tb-timeline-line{top:1em}.tb-timeline-item:last-of-type>.tb-timeline-line{bottom:calc(100% - 1em)}.tb-timeline-line{position:absolute;left:0;top:0;bottom:0;width:0;border-left:1px solid #dddee1}.tb-timeline-icon{box-sizing:border-box;position:absolute;width:9px;height:9px;border-radius:50%;left:-4px;top:.5em;background-color:#fff;border:1px solid #bbbec4}.tb-timeline-item-primary{opacity:1}.tb-timeline-item-primary>.tb-timeline-icon{border-color:#1296db;background-color:#1296db}.tb-timeline-item-primary>.tb-timeline-line{border-color:#1296db}.tb-timeline-item-info{opacity:1}.tb-timeline-item-info>.tb-timeline-icon{border-color:#6ad1ec;background-color:#6ad1ec}.tb-timeline-item-info>.tb-timeline-line{border-color:#6ad1ec}.tb-timeline-item-success{opacity:1}.tb-timeline-item-success>.tb-timeline-icon{border-color:#15bd9a;background-color:#15bd9a}.tb-timeline-item-success>.tb-timeline-line{border-color:#15bd9a}.tb-timeline-item-warning{opacity:1}.tb-timeline-item-warning>.tb-timeline-icon{border-color:#ff9900;background-color:#ff9900}.tb-timeline-item-warning>.tb-timeline-line{border-color:#ff9900}.tb-timeline-item-danger{opacity:1}.tb-timeline-item-danger>.tb-timeline-icon{border-color:#E74F5E;background-color:#E74F5E}.tb-timeline-item-danger>.tb-timeline-line{border-color:#E74F5E}.tb-timeline-item-dark{opacity:1}.tb-timeline-item-dark>.tb-timeline-icon{border-color:#495060;background-color:#495060}.tb-timeline-item-dark>.tb-timeline-line{border-color:#495060}.tb-timeline-item-gray{opacity:1}.tb-timeline-item-gray>.tb-timeline-icon{border-color:#bbbec4;background-color:#bbbec4}.tb-timeline-item-gray>.tb-timeline-line{border-color:#bbbec4}.tb-timeline-icon:hover{transform:scale(1.2);cursor:pointer}.tb-timeline-add{display:none;position:absolute;right:0;top:0;font-size:20px;line-height:1;cursor:pointer}.tb-timeline-add:before{content:"+"}.tb-timeline-add:hover{transform:scale(1.2)}.tb-timeline-item:hover .tb-timeline-add{display:block}.tb-timeline-content{overflow:hidden}tb-word-explain{display:flex;margin-top:1em;margin-bottom:1em;padding:10px 20px;background-color:#f8f8f9;border-radius:10px}.tb-word-explain-title-group{width:140px;padding-right:20px}.tb-word-explain-title{margin:0;font-size:inherit}.tb-word-explain-subtitle{margin:0;font-weight:300;font-size:0.9em}.tb-word-explain-detail{flex:1;padding-left:20px;border-left:1px dashed #ddd}@media screen and (max-width:767px){tb-word-explain{display:block}.tb-word-explain-title-group{width:auto !important;padding-right:0;display:flex;align-items:baseline;padding-bottom:0.5em;margin-bottom:0.5em;border-bottom:1px dashed #ddd}.tb-word-explain-subtitle{margin-left:0.5em;font-weight:300;font-size:0.9em}.tb-word-explain-detail{padding-left:0;border-left:none}}tb-word-explain{position:relative}tb-word-explain:hover .tb-word-explain-close{display:block}.tb-word-explain-close{display:none;position:absolute;right:10px;top:0;font-size:20px;line-height:1;cursor:pointer}.tb-word-explain-close:hover{transform:scale(1.2)}.tb-word-explain-close:before{content:"×"}tb-jumbotron{display:block;min-height:200px;margin-bottom:1em;background-color:#eee;padding:20px}tb-todo-list{display:block;margin-top:1em;margin-bottom:1em}.tb-todo-list-item{padding-top:0.2em;padding-bottom:0.2em;display:flex}.tb-todo-list-btn{margin-right:0.6em}.tb-todo-list-state{display:inline-block;margin-top:3px;width:12px;height:12px;border:2px solid #1296db;background:#fff;border-radius:3px;cursor:pointer;position:relative}.tb-todo-list-state:after{content:"";position:absolute;border-right:2px solid #fff;border-bottom:2px solid #fff;left:3px;top:1px;width:4px;height:6px;transform:rotateZ(45deg)}.tb-todo-list-state-active:after{border-color:#1296db}.tb-todo-list-state-disabled{opacity:0.5}.tb-todo-list-content{flex:1}tb-image-card{display:block;margin-top:10px;margin-bottom:20px;box-shadow:1px 2px 3px rgba(0,0,0,.1);border-radius:3px;overflow:hidden}tb-image-card>div>img{width:100%;display:block;min-height:40px}tb-image-card>p{margin:0;text-align:center;font-size:15px;color:#aaa;height:24px;line-height:24px;padding:6px 20px;box-sizing:content-box}blockquote{padding:10px 15px;border-left:10px solid #dddee1;background-color:#f8f8f9;margin:1em 0;border-radius:4px}code,pre{background-color:#fefefe}pre code{padding:0;border:none;background:none;border-radius:0;vertical-align:inherit}code{padding:1px 5px;border-radius:3px;vertical-align:middle;border:1px solid rgba(0,0,0,.08)}pre{line-height:1.418em;display:flex;border-radius:5px;border:1px solid #e9eaec;word-break:break-all;word-wrap:break-word;white-space:pre-wrap;overflow:hidden;position:relative}code,kbd,pre,samp{font-family:Microsoft YaHei Mono,Menlo,Monaco,Consolas,Courier New,monospace}.tb-code-line-number-bg{background-color:#f9f9f9;border-right:1px solid #ddd;width:3em}.tb-code-content{flex:1;padding:15px 15px 15px 0.5em;counter-reset:codeNum}.tb-code-line{position:relative}.tb-code-line::before{counter-increment:codeNum;content:counter(codeNum);position:absolute;left:-3.5em;top:0;width:2em;text-align:right;padding:0 0.5em;overflow:hidden;white-space:nowrap;color:#aeaeae}.tb-pre-lang{position:absolute;right:0;top:0;opacity:0.5;pointer-events:none;font-size:13px;padding:4px 10px}.tb-hl-keyword{font-weight:bold}.tb-hl-string{color:rgb(221,17,68)}.tb-hl-function{color:rgb(0,134,179)}.tb-hl-number{color:#388138}.tb-hl-tag{color:rgb(0,0,128)}.tb-hl-comment{color:rgb(153,153,136);font-style:italic}.tb-hl-boolean{color:#388138;font-weight:bold}.tb-hl-builtin{color:rgb(0,134,179)}.tb-hl-regex{color:#f60}.tb-hl-attr-name{color:rgb(0,134,179)}.tb-hl-attr-value{color:rgb(221,17,68)}.tb-hl-class-name{color:rgb(0,134,179);font-weight:bold}.tb-hl-selector{color:rgb(0,134,179);font-weight:bold}pre[theme=dark]{color:#a9aeb2;background-color:#1c2838;border-color:#353535}pre[theme=dark] .tb-hl-keyword{color:rgb(0,134,179)}pre[theme=dark] .tb-hl-tag{color:rgb(0,134,179)}pre[theme=dark] .tb-hl-comment{color:#4c5156}pre[theme=dark] .tb-hl-string{color:#ce5a70}pre[theme=dark] .tb-hl-attr-value{color:#ce5a70}pre[theme=dark] .tb-hl-regex{color:#af741d}pre[theme=dark] .tb-hl-selector{color:#ce5a70;font-weight:normal}pre[theme=dark] .tb-code-line::before{color:#536171}pre[theme=dark] .tb-code-line-number-bg{background-color:#2d3a48;border-right-color:#292929}td,th{border-width:1px;border-style:solid}table{border-spacing:0;border-collapse:collapse;width:100%}.tb-table td,th{border-color:#aaa}[style*=color]:not([style*=background-color])
   a{color:inherit}a{text-decoration:underline;color:#449fdb;cursor:text}
</style>