package com.satoru.blog.controller;

import com.satoru.blog.model.CommentTree;
import com.satoru.blog.service.CommentTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@Controller
@RequestMapping({"/commentTree/"})
public class CommentTreeController {
    private CommentTreeService commentTreeService;

    public CommentTreeController(CommentTreeService commentTreeService) {
        this.commentTreeService = commentTreeService;
    }

    @ResponseBody
    @GetMapping("treeList")
    public Map<String, Object> selectCommentTreePage(Integer commentId, Integer pageNum) {
        Map<String, Object> map = new HashMap();
        List<CommentTree> commentTreeList = this.commentTreeService.queryCommentTreePage(commentId, pageNum - 1);
        map.put("data", commentTreeList);
        return map;
    }

    public Boolean insertByCommentId(CommentTree commentTree) {
        return this.commentTreeService.insertByCommentId(commentTree);
    }

    public Boolean deleteById(Integer commentTreeId) {
        return this.commentTreeService.deleteById(commentTreeId);
    }
}
