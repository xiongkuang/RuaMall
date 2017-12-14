package com.xiongkuang.search.message;

import com.xiongkuang.common.pojo.SearchItem;
import com.xiongkuang.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听商品添加消息，接收消息后，把对应的商品信息同步到索引库
 * Created by xiongkuang on 14/12/2017.
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public void onMessage(Message message) {
        //从消息中取商品id
        try {
            TextMessage textMessage = (TextMessage)message;
            String text = textMessage.getText();
            Long itemId = Long.parseLong(text);

            //等待事务提交
            Thread.sleep(1000);

            //根据商品id从数据库中查询商品信息
            SearchItem item = itemMapper.getItemById(itemId);

            //创建文档对象，向文档对象中添加域
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());

            //把文档写入索引库，提交
            solrClient.add(document);
            solrClient.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
