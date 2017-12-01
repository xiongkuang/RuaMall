package com.xiongkuang.controller;

import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.pojo.TbItem;
import com.xiongkuang.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiongkuang on 29/11/2017.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUiDataGridResult getItemList(Integer page, Integer rows){
        EasyUiDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }
}
