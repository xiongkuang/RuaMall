package com.xiongkuang.service;

import com.sun.org.apache.regexp.internal.RE;
import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbItem;
import com.xiongkuang.pojo.TbItemDesc;

/**
 * Created by xiongkuang on 29/11/2017.
 */

public interface ItemService {

    TbItem getItemById(Long id);
    EasyUiDataGridResult getItemList(int page, int rows);

    ResponseResult addItem(TbItem item, String desc);
    ResponseResult deleteItem(Long[] ids);
    ResponseResult offShelfItem(Long[] ids);
    ResponseResult reShelfItem(Long[] ids);

    TbItemDesc getDescById(Long id);

    ResponseResult updateItem(TbItem item, String desc);
}
