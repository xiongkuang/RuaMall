package com.xiongkuang.service;

import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbItem;

/**
 * Created by xiongkuang on 29/11/2017.
 */

public interface ItemService {

    TbItem getItemById(Long id);
    EasyUiDataGridResult getItemList(int page, int rows);

    ResponseResult addItem(TbItem item, String desc);

    ResponseResult deleteItem(long[] ids);
}
