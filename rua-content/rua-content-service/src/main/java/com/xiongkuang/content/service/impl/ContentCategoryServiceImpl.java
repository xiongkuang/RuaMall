package com.xiongkuang.content.service.impl;

import com.xiongkuang.common.pojo.EasyUiTreeNode;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.content.service.ContentCategoryService;
import com.xiongkuang.mapper.TbContentCategoryMapper;
import com.xiongkuang.pojo.TbContent;
import com.xiongkuang.pojo.TbContentCategory;
import com.xiongkuang.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiongkuang on 08/12/2017.
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUiTreeNode> getContentCatList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> catList = contentCategoryMapper.selectByExample(example);
        List<EasyUiTreeNode> res = new ArrayList<>();
        for(TbContentCategory cat : catList){
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setId(cat.getId());
            node.setText(cat.getName());
            node.setState(cat.getIsParent()?"closed":"open");
            res.add(node);
        }
        return res;
    }

    /**
     * 添加内容目录
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public ResponseResult addContentCategory(long parentId, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        tbContentCategory.setStatus(1);//1正常 ，2删除
        tbContentCategory.setSortOrder(1);
        Date date =new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        tbContentCategory.setIsParent(false);//一定是叶子

        //插入后需要判断父节点的isparent属性，如果不是true改为true
        contentCategoryMapper.insert(tbContentCategory);
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parent.getIsParent()){
            parent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parent);
        }

        return ResponseResult.ok(tbContentCategory);
    }

    @Override
    public ResponseResult deleteCategory(long id) {
        TbContentCategory currentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        long parentId = currentCategory.getParentId();
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);

        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

        if(list.size() <= 1){
            parent.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        contentCategoryMapper.deleteByPrimaryKey(id);

        return ResponseResult.ok();
    }

    @Override
    public ResponseResult renameCategory(long id, String name) {
        TbContentCategory currentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        currentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKey(currentCategory);
        return ResponseResult.ok();
    }
}
