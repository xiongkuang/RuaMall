package com.xiongkuang.service.impl;

import com.xiongkuang.common.pojo.EasyUiTreeNode;
import com.xiongkuang.mapper.TbItemCatMapper;
import com.xiongkuang.pojo.TbItemCat;
import com.xiongkuang.pojo.TbItemCatExample;
import com.xiongkuang.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiongkuang on 01/12/2017.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyUiTreeNode> getItemCatList(long parentId) {
        //根据parent id查询节点列表
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        //执行查询并封装
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        List<EasyUiTreeNode> result = new ArrayList<>();
        for(TbItemCat item : list){
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setId(item.getId());
            node.setText(item.getName());
            node.setState(item.getIsParent()?"closed":"open");
            result.add(node);
        }
        return result;
    }


}
