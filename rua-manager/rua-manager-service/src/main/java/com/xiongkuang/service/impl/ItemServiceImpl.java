package com.xiongkuang.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.common.utils.IDUtils;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.mapper.TbItemDescMapper;
import com.xiongkuang.mapper.TbItemMapper;
import com.xiongkuang.pojo.TbItem;
import com.xiongkuang.pojo.TbItemDesc;
import com.xiongkuang.pojo.TbItemExample;
import com.xiongkuang.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * Created by xiongkuang on 29/11/2017.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name = "topicDestination")
    private Destination topicDestination;

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

    @Override
    public EasyUiDataGridResult getItemList(int page, int rows){
        //设置分页信息
        PageHelper.startPage(page, rows);

        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        EasyUiDataGridResult result = new EasyUiDataGridResult();
        result.setRows(list);
        PageInfo<TbItem> info = new PageInfo<>(list);
        long total = info.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public ResponseResult addItem(TbItem item, String desc) {
        //补全属性
        final long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //1正常，2下架，3删除
        item.setStatus((byte)1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //插入商品表
        itemMapper.insert(item);

        //创建商品描述对象
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        // 插入商品描述表
        itemDescMapper.insert(itemDesc);
        //使用jmstemplate发布商品添加消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(itemId+"");
                return message;
            }
        });
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult deleteItem(Long[] ids) {
        for(long id : ids){
            itemMapper.deleteByPrimaryKey(id);
            itemDescMapper.deleteByPrimaryKey(id);
        }
        return ResponseResult.ok();
    }

    /**
     * 下架
     * @param ids
     * @return
     */
    @Override
    public ResponseResult offShelfItem(Long[] ids) {
        for(long id : ids){
            TbItem item = itemMapper.selectByPrimaryKey(id);
            item.setStatus((byte)2);
            itemMapper.updateByPrimaryKey(item);
        }
        return ResponseResult.ok();
    }

    /**
     * 上架
     * @param ids
     * @return
     */
    @Override
    public ResponseResult reShelfItem(Long[] ids) {
        for(long id : ids){
            TbItem item = itemMapper.selectByPrimaryKey(id);
            item.setStatus((byte)1);
            itemMapper.updateByPrimaryKey(item);
        }
        return ResponseResult.ok();
    }

    @Override
    public TbItemDesc getDescById(Long id) {
        TbItemDesc desc = itemDescMapper.selectByPrimaryKey(id);
        return desc;
    }

    @Override
    public ResponseResult updateItem(TbItem item, String desc) {
        long itemId = item.getId();

        item.setStatus((byte)1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);

        itemMapper.updateByPrimaryKey(item);
        TbItemDesc newDesc = itemDescMapper.selectByPrimaryKey(itemId);
        itemDescMapper.updateByPrimaryKey(newDesc);

        return ResponseResult.ok();
    }
}
