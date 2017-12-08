package com.xiongkuang.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.content.service.ContentService;
import com.xiongkuang.mapper.TbContentMapper;
import com.xiongkuang.pojo.TbContent;
import com.xiongkuang.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xiongkuang on 08/12/2017.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public ResponseResult addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        return ResponseResult.ok();
    }


    @Override
    public EasyUiDataGridResult getContentListByCategoryId(long categoryId, int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExample(example);
        EasyUiDataGridResult result = new EasyUiDataGridResult();
        result.setRows(list);
        PageInfo<TbContent> info = new PageInfo<>(list);
        long total = info.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public ResponseResult editContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKey(content);
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult deleteContent(Long[] ids) {
        for(long id : ids){
            contentMapper.deleteByPrimaryKey(id);
        }
        return ResponseResult.ok();
    }
}
