package com.xiongkuang.front.controller;

import com.xiongkuang.content.service.ContentService;
import com.xiongkuang.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by xiongkuang on 06/12/2017.
 */
@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;

    @Value("${CONTENT_CAROUSEL_ID}")
    private Long CONTENT_CAROUSEL_ID;

    @RequestMapping("/index")
    public String showIndex(Model model){
        //查询内容列表
        List<TbContent> adBigList =  contentService.getContentListByCid(CONTENT_CAROUSEL_ID);
        model.addAttribute("adBigList", adBigList);
        return "index";
    }
}
