package com.xiongkuang.controller;

import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 倒入索引
 * Created by xiongkuang on 12/12/2017.
 */
@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public ResponseResult importItemList(){
        return searchItemService.importAllItem();
    }

}
