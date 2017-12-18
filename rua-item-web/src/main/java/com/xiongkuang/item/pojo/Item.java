package com.xiongkuang.item.pojo;

import com.xiongkuang.pojo.TbItem;

/**
 * Created by xiongkuang on 15/12/2017.
 */
public class Item extends TbItem {

    public Item(TbItem tbItem){
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    public String[] getImages(){
        String image = this.getImage();
        if(image != null && image.length() != 0){
            String[] res = image.split(",");
            return res;
        }else{
            return null;
        }
    }
}