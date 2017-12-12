package com.xiongkuang.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiongkuang.common.jedis.JedisClient;
import com.xiongkuang.common.pojo.EasyUiDataGridResult;
import com.xiongkuang.common.utils.JsonUtils;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.content.service.ContentService;
import com.xiongkuang.mapper.TbContentMapper;
import com.xiongkuang.pojo.TbContent;
import com.xiongkuang.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Override
    public ResponseResult addContent(TbContent content) {
        //增删改数据库的时候都把缓存里面的数据删了，下次再查询时候就能实现同步了
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        //缓存同步，即删除缓存中对应的数据
        jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
        return ResponseResult.ok();
    }


    @Override
    public EasyUiDataGridResult showContentListByCategoryId(long categoryId, int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
//        TbContentExample example = new TbContentExample();
//        TbContentExample.Criteria criteria = example.createCriteria();
//        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = getContentListByCid(categoryId);
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
        //缓存同步，即删除缓存中对应的数据
        jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult deleteContent(Long[] ids) {
        for(long id : ids){
            contentMapper.deleteByPrimaryKey(id);
            //缓存同步，即删除缓存中对应的数据
            long cid = contentMapper.selectByPrimaryKey(id).getCategoryId();
            jedisClient.hdel(CONTENT_LIST, cid+"");
        }
        return ResponseResult.ok();
    }


    /**
     * 根据content的categoryId返回相应的所有content
     * @param cid
     * @return
     */
    @Override
    public List<TbContent> getContentListByCid(long cid) {
        //查询缓存
        try{
            //如果有直接返回
            String json = jedisClient.hget(CONTENT_LIST, cid+"");
            if(StringUtils.isNotBlank(json)){
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //如果缓存中没有再查询数据库，把结果添加到缓存
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
        try{
            jedisClient.hset(CONTENT_LIST, cid+"", JsonUtils.objectToJson(list));
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}
