package com.xiongkuang.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.xiongkuang.common.pojo.EasyUiTreeNode;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理
 * Created by xiongkuang on 08/12/2017.
 */
@Controller
public class ContentCatController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUiTreeNode> getContentCatList(@RequestParam(name = "id", defaultValue = "0") Long parentId){
        List<EasyUiTreeNode> res = contentCategoryService.getContentCatList(parentId);
        return res;
    }

    /**
     * 添加分类节点
     */
    @RequestMapping(value = "/content/category/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult createContentCat(Long parentId, String name){
        ResponseResult res = contentCategoryService.addContentCategory(parentId, name);
        return res;
    }


    @RequestMapping("/content/category/delete")
    @ResponseBody
    public ResponseResult deleteContentCategory(Long id){
        return contentCategoryService.deleteCategory(id);
    }

    @RequestMapping("/content/category/update")
    @ResponseBody
    public ResponseResult renameCategory(Long id, String name){
        return contentCategoryService.renameCategory(id, name);
    }
}
