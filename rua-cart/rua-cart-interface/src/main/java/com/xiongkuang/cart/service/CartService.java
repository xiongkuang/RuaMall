package com.xiongkuang.cart.service;

import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbItem;

import java.util.List;

/**
 * Created by xiongkuang on 20/12/2017.
 */
public interface CartService {

        ResponseResult addToCart(long userId, long itemId, int num);
        ResponseResult mergeCart(long userId, List<TbItem> itemList);
        List<TbItem> getCartListFromRedis(long userId);
        ResponseResult updateCartNum(long userId, long itemId, int num);
        ResponseResult deleteCartItem(long userId, long itemId);
}
