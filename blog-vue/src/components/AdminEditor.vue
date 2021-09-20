<template>
    <div>
        <div ref="editorContainer"></div>
        <div style="text-align: right">
            <el-button type="primary" @click="sumbit">回复</el-button>
        </div>
    </div>
</template>

<script>
import { ref, onMounted, reactive, inject } from "vue";
import { createEditor } from "@textbus/textbus";
import "@textbus/textbus/bundles/textbus.min.css";
import { ElMessage } from "element-plus";
import axios from "axios";

export default {
    name: "AdminEditor",
    props: ["articleId"],
    setup(props, context) {
        const editorContainer = ref();
        const currentUser = inject("currentUser");
        const comment = reactive({
            articleId: Number(props.articleId),
            content: "",
            text: "",
        });

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
                                            form.append("file", files[i]);
                                        }
                                        document.body.removeChild(fileInput);
                                        console.log('ss', prevValue)
                                        axios({
                                            method: "POST",
                                            url: "/api/img/upload",
                                            data: form,
                                        })
                                            .then(function (response) {
                                                console.log(
                                                    "图片发送成功",
                                                    response
                                                );
                                                //console.log('图片路径:', require(path))
                                                resolve(response.data.data.imageUrl);
                                            })
                                            .then(function (err) {
                                                console.log(
                                                    "图片发送失败",
                                                    err
                                                );
                                            });
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

            editor.onChange.subscribe(() => {
                comment.content = editor.getContents().content;
            });
        });

        // 提交评论
        function sumbit() {
            console.log("editor--当前用户", currentUser);
            console.log("过滤结果:", filter(comment.content));
            console.log(comment);
            if (currentUser === null) {
                ElMessage({
                    type: "error",
                    message: "请登录后进行回复!",
                });
            } else if (
                comment.articleId === null ||
                comment.articleId === undefined
            ) {
                ElMessage({
                    type: "error",
                    message: "所在页面错误，请刷新文章后回复!",
                });
            } else if (filter(comment.content).length === 0) {
                ElMessage({
                    type: "error",
                    message: "不能回复空的内容!",
                });
            } else {
                axios({
                    method: "POST",
                    url: "/web/comment/insert",
                    data: {
                        articleId: comment.articleId,
                        commentContent: comment.content,
                    },
                }).then(function (response) {
                    if (response.data.code === 200) {
                        ElMessage({
                            type: "success",
                            message: "回复成功!",
                            duration: 1000,
                            onClose: () => {
                                location.reload();
                            },
                        });
                    } else {
                        ElMessage({
                            type: "error",
                            message: "发送失败!",
                        });
                    }
                });
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

        return {
            editorContainer,
            context,
            sumbit,
        };
    },
};
</script>

<style>
</style>