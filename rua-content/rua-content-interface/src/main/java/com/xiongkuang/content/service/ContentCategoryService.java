package com.xiongkuang.content.service;

import com.xiongkuang.common.pojo.EasyUiTreeNode;
import com.xiongkuang.common.utils.ResponseResult;

import java.util.List;

/**
 * Created by xiongkuang on 08/12/2017.
 */
public interface ContentCategoryService {

    List<EasyUiTreeNode> getContentCatList(long id);
    ResponseResult addContentCategory(long parentId, String name);
    ResponseResult deleteCategory(long id);
    ResponseResult renameCategory(long id, String name);
}
