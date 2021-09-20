<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ name: 'Home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ name: 'Home', query: { type: article.articleType.articleTypeId }}">{{article.articleType.typeName}}</el-breadcrumb-item>
            <el-breadcrumb-item>{{article.title}}</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
</template>

<script>
export default {
    name: 'headerNav'
}
</script>

<style>

</style>