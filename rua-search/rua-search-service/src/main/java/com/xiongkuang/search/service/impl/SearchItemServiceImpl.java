package com.xiongkuang.search.service.impl;

import com.xiongkuang.common.pojo.SearchItem;
import com.xiongkuang.common.utils.ResponseResult;
import com.xiongkuang.search.mapper.ItemMapper;
import com.xiongkuang.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 索引库维护service
 * Created by xiongkuang on 12/12/2017.
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public ResponseResult importAllItem() {
        //查询便利商品列表
        try {
            List<SearchItem> itemList = itemMapper.getItemList();
            for (SearchItem item : itemList){
                //创建文档对象，向文档对象中添加域
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategory_name());
                //写入索引库并提交
                solrClient.add(document);
            }
            solrClient.commit();
            return ResponseResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.build(500, "数据返回异常");
        }
    }
}
