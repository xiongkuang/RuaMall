package com.xiongkuang.controller;

import com.xiongkuang.common.pojo.EasyUiTreeNode;
import com.xiongkuang.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xiongkuang on 01/12/2017.
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUiTreeNode> getItemList(@RequestParam(name="id", defaultValue = "0") Long parentId){
        List<EasyUiTreeNode> res = itemCatService.getItemCatList(parentId);
        return res;
    }
}
