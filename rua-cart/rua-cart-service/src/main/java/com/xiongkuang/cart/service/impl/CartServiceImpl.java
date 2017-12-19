package com.xiongkuang.cart.service.impl;

import com.xiongkuang.cart.service.CartService;
import com.xiongkuang.common.jedis.JedisClient;
import com.xiongkuang.common.utils.JsonUtils;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.mapper.TbItemMapper;
import com.xiongkuang.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiongkuang on 20/12/2017.
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public ResponseResult addToCart(long userId, long itemId, int num) {
        //向redis中添加购物车
        //数据类型是hash， key为用户id， field为商品id，value为商品信息
        //先判断商品是否存在，如果存在数量相加。如果不存在则根据id取得信息加到列表中
        Boolean exists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId+"");
        TbItem item;
        if (exists){
            String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId+"");
            //把json转换为Tbitem
            item = JsonUtils.jsonToPojo(json, TbItem.class);
            item.setNum(item.getNum() + num);
        }else{
            item = itemMapper.selectByPrimaryKey(itemId);
            item.setNum(num);
            String images = item.getImage();
            if (StringUtils.isNotBlank(images)){
                item.setImage(images.split(",")[0]);
            }
        }
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId+"", JsonUtils.objectToJson(item));
        return ResponseResult.ok();
    }

    /**
     * 合并cookie中商品与redis中商品
     * @param userId
     * @param itemList cookie中的商品列表
     * @return
     */
    @Override
    public ResponseResult mergeCart(long userId, List<TbItem> itemList) {
        for (TbItem item : itemList){
            addToCart(userId, item.getId(), item.getNum());
        }
        return ResponseResult.ok();
    }

    @Override
    public List<TbItem> getCartListFromRedis(long userId) {
        List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> res = new ArrayList<>();
        for (String json : jsonList){
            TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
            res.add(item);
        }
        return res;
    }

    @Override
    public ResponseResult updateCartNum(long userId, long itemId, int num) {
        //取商品信息
        String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId+"");
        TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
        item.setNum(num);
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId+"", JsonUtils.objectToJson(item));
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult deleteCartItem(long userId, long itemId) {
        jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId+"");
        return ResponseResult.ok();
    }
}
