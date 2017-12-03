package com.xiongkuang.service;

import com.xiongkuang.common.pojo.EasyUiTreeNode;

import java.util.List;

/**
 * Created by xiongkuang on 01/12/2017.
 */
public interface ItemCatService {
    List<EasyUiTreeNode> getItemCatList(long parentId);
}
