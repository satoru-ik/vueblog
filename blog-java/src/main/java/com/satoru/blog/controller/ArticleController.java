package com.satoru.blog.controller;

import com.satoru.blog.model.Article;
import com.satoru.blog.model.User;
import com.satoru.blog.service.ArticleService;
import com.satoru.blog.service.RedisService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@RestController
@RequestMapping("/article/")
public class ArticleController {
    private ArticleService articleService;
    private RedisService redisService;

    public ArticleController(ArticleService articleService, RedisService redisService) {
        this.articleService = articleService;
        this.redisService = redisService;
    }

    /**
     * 根据文章分类、文章标题、分页起始的索引和每页多少条数据来查询一个分页的文章列表
     * 并返回符合搜索条件的文章总数
     */
    @GetMapping("page")
    public Map<String, Object> queryArticlePage(@RequestParam(name = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                                 @RequestParam(name = "pageSize",required = false,defaultValue = "5") Integer pageSize,
                                                 @RequestParam(name = "title",required = false,defaultValue = "") String title,
                                                 @RequestParam(name = "articleTypeId",required = false,defaultValue = "0") Integer articleTypeId) {
        Map<String, Object> map = new HashMap();
        List<Article> articleList = articleService.queryArticlePage(pageNum, pageSize, title, articleTypeId);
        Integer total = articleService.queryCount(title, articleTypeId);
        map.put("code", 200);
        map.put("data", articleList);
        map.put("total", total);
        return map;
    }

    /**
     * 根据id查询一篇文章
     * 并增加文章的访问数量
     */
    @GetMapping("{articleId}")
    public Map<String, Object> articleView(@PathVariable Integer articleId) {
        Map<String, Object> map = new HashMap();
        articleService.addViewsById(articleId);
        Article article = articleService.queryOneById(articleId);
        if (article == null) {
            map.put("code", 404);
        } else {
            map.put("code", 200);
            map.put("data", article);
        }

        return map;
    }

    /**
     *  添加一篇文章
     */
    @PostMapping("insert")
    public Map<String, Object> insertArticle(@RequestBody Article article, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        User user = redisService.currentUser(request);
        article.setUser(user);
        Integer status = articleService.insert(article);
        if (status == 0) {
            map.put("code", 404);
        } else {
            map.put("code", 200);
        }

        return map;
    }

    /**
     * 根据id更新一篇文章的内容、和置顶或删除状态
     */
    @PutMapping("update")
    public Map<String, Object> updateArticle(@RequestBody Article article) {
        Map<String, Object> map = new HashMap();
        Integer status = articleService.updateById(article);
        if (status == 0) {
            map.put("code", 404);
        } else {
            map.put("code", 200);
        }
        return map;
    }

    /**
     *  根据id删除一篇文章
     */
    @DeleteMapping("{articleId}")
    public Map<String, Object> deleteArticle(@PathVariable Integer articleId) {
        Map<String, Object> map = new HashMap();
        Integer status = articleService.deleteById(articleId);
        if (status == 0) {
            map.put("code", 404);
        } else {
            map.put("code", 200);
        }
        return map;
    }
}
