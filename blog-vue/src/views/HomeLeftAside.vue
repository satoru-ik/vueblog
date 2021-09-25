<template>
    <el-container>
        <el-main>
            <el-row>
                <el-col :xl="{span:14, offset: 10}" :lg="{span:16, offset: 8}" :md="{span:20, offset: 4}" :sm="0">
                    <el-card>
                        <template #header>
                            <span>公告栏</span>
                        </template>
                        <p>
                            游客账号:guest
                        </p>
                        <p>
                            密码:123456
                        </p>
                        <p>
                            (由于使用session验证登录,同时多地登录会被顶号)
                        </p>
                    </el-card>
                </el-col>
            </el-row>
        </el-main>
    </el-container>
</template>

<script>
export default {
    name: "HomeLeftAside",
};
</script>

<style scoped>
</style>