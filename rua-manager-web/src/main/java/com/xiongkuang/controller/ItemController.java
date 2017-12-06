package com.xiongkuang.controller;

import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbItem;
import com.xiongkuang.pojo.TbItemDesc;
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
    public ResponseResult deleteItem(@RequestParam("ids") Long[] ids){
        ResponseResult result = itemService.deleteItem(ids);

        return result;
    }

    /**
     * 下架
     */
    @RequestMapping(value = "/offShelf", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult offShelfItem(@RequestParam("ids") Long[] ids){
        ResponseResult result = itemService.offShelfItem(ids);
        return result;
    }

    /**
     * 上架
     */
    @RequestMapping(value = "/reShelf", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult reShelfItem(@RequestParam("ids") Long[] ids){
        ResponseResult result = itemService.reShelfItem(ids);
        return result;
    }




    /**
     *返回描述responseResult
     */
    @RequestMapping(value = "/query/desc/{id}")
    @ResponseBody
    public ResponseResult getDesc(@PathVariable Long id){
        TbItemDesc desc= itemService.getDescById(id);
        ResponseResult result = new ResponseResult(desc);
        return result;
    }


    /**
     * 返回item的ResponseResult
     */
    @RequestMapping(value = "/query/item/{id}")
    @ResponseBody
    public ResponseResult getItemRes(@PathVariable Long id){
        TbItem item = itemService.getItemById(id);
        ResponseResult result = new ResponseResult(item);
        return result;
    }


    /**
     * 商品修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateItem(TbItem item, String desc){
        ResponseResult result = itemService.updateItem(item, desc);
        return result;
    }



}
