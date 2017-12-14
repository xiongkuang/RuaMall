package com.xiongkuang.search.mapper;


import com.xiongkuang.common.pojo.SearchItem;

import java.util.List;

/**
 * Created by xiongkuang on 12/12/2017.
 */
public interface ItemMapper {

    List<SearchItem> getItemList();
    SearchItem getItemById(long itemId);
}
