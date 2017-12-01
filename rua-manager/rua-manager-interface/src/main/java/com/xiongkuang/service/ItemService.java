package com.xiongkuang.service;

import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.pojo.TbItem;

/**
 * Created by xiongkuang on 29/11/2017.
 */

public interface ItemService {

    TbItem getItemById(Long id);
    EasyUiDataGridResult getItemList(int page, int rows);
}
