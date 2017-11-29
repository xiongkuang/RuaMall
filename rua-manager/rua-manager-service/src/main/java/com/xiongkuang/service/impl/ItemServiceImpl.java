package com.xiongkuang.service.impl;

import com.xiongkuang.mapper.TbItemMapper;
import com.xiongkuang.pojo.TbItem;
import com.xiongkuang.pojo.TbItemExample;
import com.xiongkuang.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiongkuang on 29/11/2017.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public TbItem getItemById(Long itemId) {

        //方法1
//        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
//        return tbItem;

        //方法2
        TbItemExample itemExample = new TbItemExample();
        TbItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = itemMapper.selectByExample(itemExample);
        if (list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
