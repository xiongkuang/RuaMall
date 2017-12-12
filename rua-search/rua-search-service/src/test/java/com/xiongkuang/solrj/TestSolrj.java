package com.xiongkuang.solrj;



import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiongkuang on 12/12/2017.
 */
public class TestSolrj {

    @Test
    public void addDocument() throws Exception{
        //创建一个solrserver对象，建立一个连接.参数为solrf服务的url
        String urlString = "http://localhost:8983/solr/core1";
        SolrClient solrClient = new HttpSolrClient.Builder(urlString).build();

        //创建文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();

        //向文档对象中添加field。文档中必须有一个id域，所有域的名字必须再schema.xml中定义
        document.addField("id", "doc02");
        document.addField("item_title","测试商品02");
        document.addField("item_price",3000);
        document.addField("item_sell_point", "很帅");
        document.addField("item_image", "http://192.168.25.133/group1/M00/00/00/wKgZhVotSbmAdFU6AAGpMc8blCI804.jpg");
        document.addField("item_category_name", "大广告");

        //把文档写入索引库，并commit
        solrClient.add(document);
        solrClient.commit();


    }

    @Test
    public void deleteDocument() throws Exception{
        String urlString = "http://localhost:8983/solr/core1";
        HttpSolrClient solrClient = new HttpSolrClient.Builder(urlString).build();

        solrClient.deleteById("doc01");
//        solrClient.deleteByQuery("id:doc01");
        solrClient.commit();
    }


    @Test
    public void testBean() throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-solr.xml");
        SolrClient solrClient = context.getBean(HttpSolrClient.class);

        //创建文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
//
//        //向文档对象中添加field。文档中必须有一个id域，所有域的名字必须再schema.xml中定义
//        document.addField("id", "doc01");
//        document.addField("item_title","测试商品01");
//        document.addField("item_price",1000);


        //向文档对象中添加field。文档中必须有一个id域，所有域的名字必须再schema.xml中定义
        document.addField("id", "doc02");
        document.addField("item_title","测试商品02");
        document.addField("item_price",3000);
        document.addField("item_sell_point", "很帅");
        document.addField("item_image", "http://192.168.25.133/group1/M00/00/00/wKgZhVotSbmAdFU6AAGpMc8blCI804.jpg");
        document.addField("item_category_name", "大广告");


        //把文档写入索引库，并commit
        solrClient.add(document);
        solrClient.commit();

    }
}
