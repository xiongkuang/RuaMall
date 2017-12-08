package com.xiongkuang.content.service;

import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbContent;

import java.util.List;

/**
 * Created by xiongkuang on 08/12/2017.
 */
public interface ContentService {

    ResponseResult addContent(TbContent content);
    EasyUiDataGridResult showContentListByCategoryId(long categoryId, int page, int rows);
    ResponseResult editContent(TbContent content);
    ResponseResult deleteContent(Long[] ids);

    List<TbContent> getContentListByCid(long cid);
}
