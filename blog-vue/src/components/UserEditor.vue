<template>
    <div>
        <div ref="editorContainer" style="min-hight:650px;">
        </div>
        <div style="text-align:right">
            <el-button type="primary" @click="sumbit">回复</el-button>
        </div>
    </div>
</template>

<script>
import { ref, onMounted, reactive, inject } from 'vue'
import { Editor, BlockComponent, boldFormatter, Toolbar, TOOLS } from '@textbus/textbus'
import '@textbus/textbus/bundles/textbus.min.css'
import { ElMessage } from 'element-plus'
import axios from 'axios'

export default {
    name: 'UserEditor',
    props: ['articleId'],
    setup(props, context) {
        const editorContainer = ref()
        const currentUser = inject('currentUser')
        const comment = reactive({
            articleId: Number(props.articleId),
            content: '',
            text: ''
        })

        onMounted(() => {
            const options = {
                contents: '<p>欢迎留言！</p>',
                components: [BlockComponent],
                formatters: [boldFormatter],
                providers: [{
                    provide: TOOLS,
                    useValue:[
                    ]

                }],
                plugins: [Toolbar],
            }
            const editor = new Editor(editorContainer.value, options)
            editor.onChange.subscribe(() => {
                comment.content = editor.getContents().content
                console.log('test:', comment)
            })
        })

        // 提交评论
        function sumbit() {
            console.log('editor--当前用户', currentUser)
            console.log('过滤结果:', filter(comment.content))
            console.log(comment)
            if(currentUser === null) {
                ElMessage({
                    type: 'error',
                    message: '请登录后进行回复!'
                })
            } else if(comment.articleId === null || comment.articleId === undefined) {
                ElMessage({
                    type: 'error',
                    message: '所在页面错误，请刷新文章后回复!'
                })
            } else if(filter(comment.content).length === 0) {
                ElMessage({
                    type: 'error',
                    message: '不能回复空的内容!'
                })
            } else {
                axios({
                    method: 'POST',
                    url: '/web/comment/insert',
                    data: {
                        articleId: comment.articleId,
                        commentContent: comment.content
                    }
                }).then(function(response) {
                    if(response.data.code === 200) {
                        ElMessage({
                            type: 'success',
                            message: '回复成功!',
                            duration: 1000,
                            onClose: () => { location.reload() }
                        })
                    } else {
                        ElMessage({
                            type: 'error',
                            message: '发送失败!'
                        })
                    }
                })
            }
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


        return {
            editorContainer, context,
            sumbit
        }
    }
}
</script>

<style>

</style>