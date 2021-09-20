<template>
    <el-container>
        <el-header>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="标题">
                    <el-input
                        placeholder="标题"
                        v-model="article.title"
                    ></el-input>
                </el-form-item>
                <el-form-item label="文章分类">
                    <el-select v-model="article.articleType.articleTypeId" placeholder="请选择分类">
                        <el-option v-for="item in typeList" :key="item.articleTypeId" 
                            :label="item.typeName" :value="item.articleTypeId">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
        </el-header>
        <el-main>
            <div ref="editorContainer"></div>
            <div style="text-align: right">
                <el-button type="primary" @click="sumbit">提交</el-button>
            </div>
        </el-main>
    </el-container>
</template>

<script>
import { ref, onMounted, reactive } from "vue"
import { createEditor } from "@textbus/textbus"
import "@textbus/textbus/bundles/textbus.min.css"
import { ElMessage } from "element-plus"
import axios from "axios"
import router from '../router/index'


export default {
    name: 'EditArticle',
    setup() {
        const article = reactive({
            articleId: router.currentRoute.value.query.articleId,
            title: '',
            content: '',
            articleType: {
                articleTypeId: undefined,
                typeName: undefined
            }
        })
        const typeList = reactive([])
        const editorContainer = ref();

        console.log('query:', article.articleId)

        onMounted(() => {
            // PreComponeent.theme = 'dark'
            const options = reactive({
                theme: "dark",
                PreComponeent: {
                    theme: "dark",
                },
                uploader: function (type, prevValue) {
                    switch (type) {
                        case "image": {
                            let fileInput = document.createElement("input")
                            fileInput.setAttribute("type", "file")
                            fileInput.setAttribute("accept", "image/png, image/gif, image/jpeg, image/bmp, image/x-icon");
                            fileInput.style.cssText = "position: absolute; left: -9999px; top: -9999px; opacity: 0";
                            let promise = new Promise(function (resolve) {
                                fileInput.addEventListener("change", function (event) {
                                        let form = new FormData();
                                        let files = event.target.files;
                                        for (let i = 0; i < files.length; i++) {
                                            // 这里的 file 字符串为后台接收 FormData 的字段名，可以改成自己需要的名字
                                            form.append("file", files[i])
                                        }
                                        document.body.removeChild(fileInput)
                                        axios({
                                            method: "POST",
                                            url: "/api/img/upload",
                                            data: form,
                                        })
                                            .then(function (response) {
                                                console.log("图片发送成功",response)
                                                //console.log('图片路径:', require(path))
                                                resolve(response.data.data.imageUrl)
                                            })
                                            .then(function (err) {
                                                console.log("图片发送失败",err)
                                            })
                                    }
                                );
                            });
                            document.body.appendChild(fileInput);
                            fileInput.click();
                            return promise;
                        }
                            
                    }
                }
            })
            const editor = createEditor(editorContainer.value, options);

            // 获取文章内容
            function getArticle() {
                if(article.articleId !== undefined) {
                    axios({
                        method: 'GET',
                        url: '/web/article/' + article.articleId
                    }).then(function(resposne) {
                        if(resposne.data.code !== 200) {
                            ElMessage({
                                type: 'error',
                                message: '获取文章失败'
                            })
                        } else {
                            Object.assign(article, resposne.data.data)
                            console.log('获取的内容:', article)
                            editor.setContents(article.content)
                        }
                    })
                }
            }
            getArticle()


            editor.onChange.subscribe(() => {
                article.content = editor.getContents().content;
            })
        });

        
        /**
         * 从后台获取数据
         */
        // 获取文章分类
        function getTypeList() {
            axios({
                method: 'GET',
                url: '/web/articleType/list?pageSize=200'
            }).then(function(response) {
                Object.assign(typeList, response.data.data)
                // 筛除父节点
                for(let i = 0; i < typeList.length; i++) {
                    if(typeList[i].parentNode === -1) {
                        typeList.splice(i, 1)
                        i--
                    }
                }
            })
        }
        getTypeList()

        // 发布或更新文章
        function sumbit() {
            //console.log("editor--当前用户", currentUser);
            console.log("过滤结果:", filter(article.content));
            if(article.title.length === 0 || article.articleType.articleTypeId === undefined) {
                ElMessage({
                    type: "error",
                    message: "文章的标题和分类不能为空!",
                });
            } else if (filter(article.content).length === 0) {
                ElMessage({
                    type: "error",
                    message: "不能发布空的内容!",
                });
            } else if (article.articleId === undefined) {
                axios({
                    method: "POST",
                    url: "/web/article/insert",
                    data: article,
                }).then(function (response) {
                    if (response.data.code === 200) {
                        ElMessage({
                            type: "success",
                            message: "发布成功!",
                            duration: 1000,
                            onClose: () => {
                                routeArticleList()
                            },
                        })
                    } else {
                        ElMessage({
                            type: "error",
                            message: "发布失败!",
                        })
                    }
                })
            } else {
                axios({
                    method: "PUT",
                    url: "/web/article/update",
                    data: article,
                }).then(function (response) {
                    if (response.data.code === 200) {
                        ElMessage({
                            type: "success",
                            message: "更新成功!",
                            duration: 1000,
                            onClose: () => {
                                routeArticleList()
                            },
                        })
                    } else {
                        ElMessage({
                            type: "error",
                            message: "更新失败!",
                        })
                    }
                })
            }
        }

        // 过滤html标签
        function filter(str) {
            let reg = /<[^<>]+>/g;
            if (str === undefined) {
                console.log("str", str);
                return "";
            }
            return str.replace(reg, "");
        }


        /**
         * 路由跳转
         */
        function routeArticleList() {
            router.push({
                name: 'ArticleList'
            })
        }

        return {
            editorContainer,
            article, typeList,
            sumbit
        }
    }
}
</script>

<style>

</style>