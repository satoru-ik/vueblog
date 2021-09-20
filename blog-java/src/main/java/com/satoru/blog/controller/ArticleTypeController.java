package com.satoru.blog.controller;

import com.satoru.blog.model.ArticleType;
import com.satoru.blog.service.ArticleTypeService;
import com.satoru.blog.utility.Utility;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Satoru
 * @date:2021/8/27
 * @version:1.0
 */
@RestController
@RequestMapping({"/articleType/"})
public class ArticleTypeController {
    private ArticleTypeService articleTypeService;

    public ArticleTypeController(ArticleTypeService articleTypeService) {
        this.articleTypeService = articleTypeService;
    }

    /**
     * 前台 获取除id=0以外的所有文章分类信息
     */
    @GetMapping("headerList")
    public Map<String, Object> frontSelectArticleTypeList() {
        Map<String, Object> map = new HashMap();
        map.put("code", 200);
        map.put("data", this.articleTypeService.selectArticleTypeList());
        return map;
    }

    /**
     * 后台管理 根据分页、条件查询获取一个分页内的文章分类信息
     */
    @GetMapping("list")
    public Map<String, Object> endSelectTypeList(@RequestParam(name = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                                 @RequestParam(name = "pageSize",required = false,defaultValue = "20") Integer pageSize,
                                                 @RequestParam(name = "typeName",required = false,defaultValue = "") String typeName) {
        Map<String, Object> map = new HashMap();
        map.put("code", 200);
        map.put("data", this.articleTypeService.selectTypeList(pageNum, pageSize, typeName));
        map.put("total", this.articleTypeService.queryCount(typeName));
        return map;
    }

    /**
     * 后台管理 添加一个文章分类
     */
    @PostMapping("insert")
    public Map<String, Object> insertType(@RequestBody ArticleType articleType) {
        Map<String, Object> map = new HashMap();
        if(articleTypeService.insertType(articleType) == 1) {
            map.put("code", 200);
        } else {
            map.put("code", 404);
        }
        return map;
    }

    /**
     * 后台管理 根据id删除一个文章分类
     */
    @DeleteMapping("{articleTypeId}")
    public Map<String, Object>  deleteType(@PathVariable Integer articleTypeId) {
        Map<String, Object> map = new HashMap();
        if(articleTypeService.deleteType(articleTypeId) == 1) {
            map.put("code", 200);
        } else {
            map.put("code", 404);
        }
        return map;
    }

    /**
     * 后台管理 根据id修改一个文章分类
     */
    @PutMapping("update")
    public Map<String, Object> updateType(@RequestBody ArticleType articleType) {
        Map<String, Object> map = new HashMap();
        if(articleTypeService.updateType(articleType) == 1) {
            map.put("code", 200);
        } else {
            map.put("code", 404);
        }
        return map;
    }

    /**
     * 后台管理 获取父节点分类列表
     */
    @GetMapping("parentList")
    public Map<String, Object> selectParentTypeList() {
        Map<String, Object> map = new HashMap();
        List<ArticleType> list = this.articleTypeService.selectParentTypeList();
        map.put("code", 200);
        map.put("data", list);
        return map;
    }
}
