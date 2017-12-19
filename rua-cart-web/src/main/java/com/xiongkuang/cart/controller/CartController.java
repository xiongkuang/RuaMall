package com.xiongkuang.cart.controller;

import com.xiongkuang.cart.service.CartService;
import com.xiongkuang.common.utils.CookieUtils;
import com.xiongkuang.common.utils.JsonUtils;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.pojo.TbItem;
import com.xiongkuang.pojo.TbUser;
import com.xiongkuang.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiongkuang on 19/12/2017.
 */
@Controller
public class CartController {
    @Value("${COOKIE_MAX_AGE}")
    private Integer COOKIE_MAX_AGE;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{itemId}")
    public String addToCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                            HttpServletRequest request, HttpServletResponse response){
        //判断用户是否为登录状态。登录状态下把用户信息写入redis，未登录则写入cookie
        TbUser user = (TbUser)request.getAttribute("user");
        if(user != null){
            //写入redis,保存到服务端
            cartService.addToCart(user.getId(), itemId, num);

            //返回逻辑视图
            return "cartSuccess";
        }


        //从cookie中取得购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);

        //判断商品是否已经存在于列表中,如果存在则只改变数量，否则查询商品的信息并加入列表中
        boolean exist = false;
        for (TbItem item : cartList){
            if (item.getId() == itemId.longValue()){
                item.setNum(item.getNum()+num);
                exist = true;
                break;
            }
        }
        if (!exist){
            TbItem tbItem = itemService.getItemById(itemId);
            tbItem.setNum(num);
            String image = tbItem.getImage();
            if (StringUtils.isNotBlank(image)){
                tbItem.setImage(image.split(",")[0]);
            }
            cartList.add(tbItem);
        }

        //写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_MAX_AGE, true);

        //返回成功页面
        return "cartSuccess";
    }


    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, HttpServletResponse response){
        //从cookie 中取列表
        List<TbItem> cartList = getCartListFromCookie(request);

        //判断是否登录状态.如果是登录状态，则需要把cookie中购物车列表与redis中列表合并
        TbUser user = (TbUser)request.getAttribute("user");
        if (user != null){
            cartService.mergeCart(user.getId(), cartList);
            //把cookie中列表删除
            CookieUtils.deleteCookie(request, response, "cart");
            cartList = cartService.getCartListFromRedis(user.getId());
        }
        request.setAttribute("cartList", cartList);
        return "cart";
    }


    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public ResponseResult updateCartNum(@PathVariable Long itemId, @PathVariable Integer num,
                                        HttpServletRequest request, HttpServletResponse response){
        //判断是否登录
        TbUser user = (TbUser)request.getAttribute("user");
        if (user != null){
            cartService.updateCartNum(user.getId(), itemId, num);
        }else{
            List<TbItem> cartList = getCartListFromCookie(request);
            for (TbItem item : cartList){
                if (item.getId() == itemId.longValue()){
                    item.setNum(num);
                    break;
                }
            }
            CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_MAX_AGE, true);

        }
        return ResponseResult.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deletCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response){
        //判断是否登录
        TbUser user = (TbUser)request.getAttribute("user");
        if (user != null){
            cartService.deleteCartItem(user.getId(), itemId);
        }else{
            List<TbItem> cartList = getCartListFromCookie(request);
            for (TbItem item : cartList){
                if (item.getId() == itemId.longValue()){
                    cartList.remove(item);
                    break;
                }
            }
            CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_MAX_AGE, true);

        }
        return "redirect:/cart/cart.html";
    }

    private List<TbItem> getCartListFromCookie(HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request, "cart", true);
        List<TbItem> res = new ArrayList<>();
        if (StringUtils.isBlank(json)){
            return res;
        }
        res = JsonUtils.jsonToList(json, TbItem.class);
        return res;
    }
}
