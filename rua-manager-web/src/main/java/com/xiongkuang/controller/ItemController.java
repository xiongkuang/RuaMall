package com.xiongkuang.controller;

import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbItem;
import com.xiongkuang.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xiongkuang on 29/11/2017.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/list")
    @ResponseBody
    public EasyUiDataGridResult getItemList(Integer page, Integer rows){
        EasyUiDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    /**
     * 商品添加
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult addItem(TbItem item, String desc){
        ResponseResult result = itemService.addItem(item, desc);
        return result;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteItem(@RequestParam("ids") long[] ids){
        ResponseResult result = itemService.deleteItem(ids);

        return result;
    }


}
