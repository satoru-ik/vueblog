package com.satoru.blog.controller;

import com.satoru.blog.model.Comment;
import com.satoru.blog.model.User;
import com.satoru.blog.service.CommentService;
import com.satoru.blog.service.RedisService;
import com.satoru.blog.utility.Utility;
import org.springframework.stereotype.Controller;
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
@RequestMapping({"/comment/"})
@RestController
public class CommentController {
    private CommentService commentService;
    private RedisService redisService;

    public CommentController(CommentService commentService, RedisService redisService) {
        this.commentService = commentService;
        this.redisService = redisService;
    }

    /**
     * 根据文章id、分页、每页的数量获取该分页下的评论
     * 并且获取该文章下的评论总数
     */
    @GetMapping("list")
    public Map<String, Object> selectPageByArticleId(@RequestParam(name = "articleId", required = true) Integer articleId,
                                                     @RequestParam(name = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                                     @RequestParam(name = "pageSize",required = false,defaultValue = "10") Integer pageSize) {
        Map<String, Object> map = new HashMap();
        map.put("code", 200);
        List<Comment> commentList = this.commentService.selectPageByArticleId(articleId, pageNum - 1, pageSize);
        Integer total = this.commentService.queryCountByArticleId(articleId, null, null, null, null);
        map.put("data", commentList);
        map.put("total", total);
        return map;
    }

    /**
     * 后台管理 根据多种条件(文章id, 评论用户昵称, 评论内容, 评论时间段)和分页、每页的数量获取该分页下的评论
     * 并获取符合条件的评论数量
     */
    @GetMapping("allList")
    public Map<String, Object> selectPage(@RequestParam(name = "articleId", required = false, defaultValue = "") Integer articleId,
                                         @RequestParam(name = "nickname", required = false, defaultValue = "") String nickname,
                                         @RequestParam(name = "content", required = false, defaultValue = "") String content,
                                         @RequestParam(name = "startTime", required = false, defaultValue = "") String startTime,
                                         @RequestParam(name = "endTime", required = false, defaultValue = "") String endTime,
                                         @RequestParam(name = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                         @RequestParam(name = "pageSize",required = false,defaultValue = "10") Integer pageSize) {
        Map<String, Object> map = new HashMap();
        List<Comment> list = this.commentService.selectPage(articleId, nickname, content, startTime, endTime, pageNum, pageSize);
        Integer total = this.commentService.queryCountByArticleId(articleId, nickname, content, startTime, endTime);
        map.put("code", 200);
        map.put("data", list);
        map.put("total", total);
        return map;
    }

    /**
     * 添加一条评论
     */
    @PostMapping("insert")
    public Map<String, Object> insert(@RequestBody Comment comment, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        User user = this.redisService.currentUser(request);
        comment.setUser(user);
        Integer result = this.commentService.insert(comment);
        if (result == 1) {
            map.put("code", 200);
        } else {
            map.put("code", 404);
        }

        return map;
    }

    /**
     * 根据id删除一条评论
     */
    @DeleteMapping("{commentId}")
    public Map<String, Object> deleteById(@PathVariable Integer commentId) {
        Map<String, Object> map = new HashMap();
        Integer result = this.commentService.deleteById(commentId);
        if (result == 1) {
            map.put("code", 200);
        } else {
            map.put("code", 404);
        }

        return map;
    }

    /**
     * 根据id修改一条评论
     */
    @PutMapping("update")
    public Map<String, Object> updateById(@RequestBody Comment comment) {
        Map<String, Object> map = new HashMap();
        Integer result = this.commentService.updateById(comment);
        if (result == 1) {
            map.put("code", 200);
        } else {
            map.put("code", 404);
        }
        return map;
    }

    /**
     *
     */
}
