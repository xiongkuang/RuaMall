package com.xiongkuang.controller;

import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.content.service.ContentService;
import com.xiongkuang.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiongkuang on 08/12/2017.
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult addContent(TbContent content){
        ResponseResult res = contentService.addContent(content);
        return res;
    }


    @RequestMapping(value = "/content/query/list")
    @ResponseBody
    public EasyUiDataGridResult showContentListByCategoryId(Long categoryId, Integer page, Integer rows){
        EasyUiDataGridResult res = contentService.showContentListByCategoryId(categoryId, page, rows);
        return res;
    }

    @RequestMapping("/content/delete")
    @ResponseBody
    public ResponseResult deleteContent(@RequestParam Long[] ids){
        return contentService.deleteContent(ids);
    }

    @RequestMapping("/content/edit")
    @ResponseBody
    public ResponseResult updateContent(TbContent content){
        return contentService.editContent(content);
    }
}
