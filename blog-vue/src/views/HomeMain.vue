<template>
    <div class="main-div article-header">
        <div>
            <el-link :underline="false" @click="routeHome">
                <div class="home-icon">
                    <i class="el-icon-s-home"></i>
                </div>
                <span>首页</span>
            </el-link>
        </div>
        <span class="span-line"></span>
        <div>
            <el-space wrap :size="5" class="type-space">
                <div v-for="articleType in articleTypeList" :key="articleType.articleTypeId">
                    <el-dropdown>
                        <el-button type="text">
                            {{ articleType.typeName}}<span class="badge">{{articleType.articleNumber}}</span>
                        </el-button>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-space wrap :size="15">
                                    <div v-for="child in articleType.childList"
                                        :key="child.articleTypeId">
                                        <el-link @click="routeTypePage(child.articleTypeId)" :underline="false">
                                            {{ child.typeName }}
                                        </el-link >
                                    </div>
                                </el-space>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </div>
            </el-space>
        </div>
    </div>
    <div class="main-div article" v-for="(article, index) in articleList" :key="article.articleId">
        <el-row>
            <el-col :span="11" :push="(index % 2) * 13" class="outline">
                <el-container>
                    <el-header :class="[(index % 2) == 1?'text-right': '']">
                        <div class="outline-icon">
                            <i class="el-icon-date"></i><span>发布于{{article.startTime}}</span>
                        </div>
                        <el-link type="primary" :underline="false" @click="routeArticle(article.articleId)" class="title">
                            <h3>{{article.title}}</h3>
                        </el-link>
                    </el-header>
                    <el-main>
                        <div class="outline-content">
                            <p>{{article.content}}</p>
                        </div>
                    </el-main>
                    <el-footer height="30" :class="[(index % 2) == 1?'text-right': '']">
                        <div class="outline-icon">
                            <i class="el-icon-view"></i><span>{{article.viewedNumber}}次阅读</span>
                            <i class="el-icon-chat-dot-square"></i><span>{{article.commentNumber}}条评论</span>
                            <i class="iconfont icon-good"></i><span>{{article.praiseNumber}}人点赞</span>
                        </div>
                    </el-footer>
                </el-container>
            </el-col>
            <el-col :span="13" :pull="(index % 2) * 11">
                <a @click="routeArticle(article.articleId)">
                    <el-image style="height:100%; weight:100%;" src='/api/img/download/img287.jpg' fit="cover">
                    </el-image>
                </a>
            </el-col>
        </el-row>
    </div>
    <div class="pagination">
        <el-pagination background layout="prev, pager, next" v-model:currentPage="currentPage" :total="total" :default-page-size="5">
        </el-pagination>
    </div>
</template>

<script>
import { reactive, ref, toRef, watch } from "vue"
import router from "../router/index"
import axios from "axios"

export default {
    name: 'Index',
    setup() {
        const route = router.currentRoute.value
        const articleTypeList = reactive([])
        const articleList = reactive([])
        const total = ref(0)
        const queryList = reactive({
            articleTypeId: route.query.type,
            search: route.query.search,
            currentPage: route.query.page === undefined ? 1 : Number(route.query.page)
        })

        /**
        * 监听currentPage属性变化
        */
        watch(() => queryList.currentPage, () => {
            routePage()
        }, {immediate: false, deep: true})
        watch(() => router.currentRoute.value, () => {
            queryList.search = router.currentRoute.value.query.search
            getArticleList()
            getArticleTypeList()
        }, {immediate: true, deep: true})

        /**
         * 访问后台接口
         *  */ 
        function getArticleTypeList() {
            axios({
                method: "GET",
                url: "/web/articleType/headerList",
            }).then(function (response){
                articleTypeList.length = 0
                articleTypeList.push(...typeListSort(response.data.data))
            })
        }
        function getArticleList() {
            axios({
                method: "GET",
                url: "/web/article/page",
                params: {
                    articleTypeId: queryList.articleTypeId,
                    title: queryList.search,
                    pageNum: queryList.currentPage
                }
            }).then(function (response){
                articleList.length = 0
                articleList.push(...response.data.data)
                for(let i = 0; i < articleList.length; i++) {
                    articleList[i].content = filter(articleList[i].content)
                }
                total.value = response.data.total
            })
        }
        
        // 处理文章类型列表
        function typeListSort(typeList) {
            function TypeObj(articleTypeId, typeName, articleNumber) {
                this.articleTypeId = articleTypeId;
                this.typeName = typeName;
                this.articleNumber = articleNumber;
                this.childList = []
            }
            let list = [];
            for (let i = 0; i < typeList.length; i++) {
                let node = typeList[i];
                if (node.parentNode === -1) {
                    let obj = new TypeObj(
                        node.articleTypeId,
                        node.typeName,
                        node.articleNumber
                    );
                    list.push(obj)
                }
            }
            for (let i = 0; i < typeList.length; i++) {
                let node = typeList[i];
                if (node.parentNode !== -1) {
                    list[node.parentNode - 1].childList.push(typeList[i]);
                    list[node.parentNode - 1].articleNumber += node.articleNumber;
                }
            }
            return list;
        }
        // 过滤html标签
        function filter(str) {
            let reg=/<[^<>]+>/g
            if (str === undefined) {
                console.log('str', str)
                return ''
            }
            return str.replace(reg, '')
        }

        /**
         * 路由跳转
         *  */ 
        function routeHome() {
            queryList.currentPage = 1
            queryList.search = undefined
            queryList.articleTypeId = undefined
            router.push({ name: "Home" });
        }
        function routeTypePage(articleTypeId) {
            queryList.articleTypeId = articleTypeId
            router.push({
                name: "HomeMain",
                query: {
                    search: queryList.search,
                    type: articleTypeId
                }
            })
        }
        function routeArticle(articleId) {
            router.push({
                name: 'Article',
                params: {
                    id: articleId
                }
            })
        }
        function routePage() {
            router.push({
                name: "HomeMain",
                query: {
                    search: queryList.search === ''? undefined : queryList.search,
                    type: queryList.articleTypeId,
                    page: queryList.currentPage === 1 ? undefined : queryList.currentPage
                }
            })
        }

        return {
            articleTypeList, articleList, total, currentPage:toRef(queryList, 'currentPage'),
            routeHome, routeTypePage, routeArticle
        }
    },
};
</script>

<style scoped>
.article-header {
    height: 90px;
    display: flex;
    padding: 5px;
    text-align: center;
    overflow: hidden;
}
.article {
    height: 300px;
    margin-top: 30px;
    overflow: hidden;
}
.main-div.article:hover {
    box-shadow: 0 5px 10px 5px rgba(241, 155, 29, 0.6);
}
/** 中间主页内容部分的样式 */
.main-div {
    background-color: rgba(255,255,255,.8);
    border-radius: 10px;
}
/** 主页icon图标的样式 */
.home-icon {
    background: #1e9fff;
    border-radius: 20px;
    margin: 10px;
}
.home-icon i {
    font-size: 40px;
    color: #fff;
    display: block;
}
/** 主页icon与文章分类之间间分割线的样式 */
.span-line {
    border-right: var(--el-border-color-base) 1px solid;
    margin-right: 10px;
    display: block;
}
/** 文章分类div的样式 */
.type-space div {
    width: 100px;
}
.type-space .el-button {
    color: #4c4c4c;
}
.type-space .el-button:hover {
    color: #01aaed;
}
/** 徽章的样式 */
.badge {
    margin-left: 5px;
    height: 18px;
    width: 20px;
    line-height: 18px;
    display: inline-block;
    padding: 0 6px;
    font-size: 12px;
    text-align: center;
    border-radius: 2px;
    color: #fff;
    background-color: #009688;
}
/** 文章分类下拉列表样式 */
.el-dropdown-menu {
    padding: 8px;
    width: 180px;
}
.el-dropdown-item {
  font-size: 12px;
}
/** 文章简介的样式 */
.outline {
    padding: 20px 0 20px 0;
}
.outline h3 {
    font-size: 1.8rem;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    word-wrap: break-word;
    margin-bottom: .5rem;
    font-weight: 500;
    line-height: 1.2;
    margin-top: 0;
}

.outline-icon i {
    padding: 5px;
}

.outline-icon span {
    color: #888;
    font-size: 12px;
    margin-right: 20px;
}
.outline-content {
    color: rgba(0,0,0,.66);
    margin-bottom: 1.5em;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 7;
    overflow: hidden;
    height: 160px;
}

/** 分页样式 */
.pagination {
    margin-top: 20px;
}
.el-pagination {
    text-align: center;
}

.el-container {
    height: 100%;
}
.el-main {
    padding-top: 0;
    padding-bottom: 0;
}
/**文章内容靠右对齐 */
.text-right {
    text-align: right;
}
</style>