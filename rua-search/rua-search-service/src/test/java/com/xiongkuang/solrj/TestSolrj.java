package com.xiongkuang.solrj;



import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

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

    @Test
    public void queryIndex() throws Exception{
        //创建一个solrserver对象
        String urlString = "http://localhost:8983/solr/core1";
        SolrClient solrClient = new HttpSolrClient.Builder(urlString).build();

        //创建solrquery对象
        SolrQuery query = new SolrQuery();

        //设置查询条件
//        query.setQuery("*:*");
        query.set("q", "*:*");

        //执行查询，queryresponse对象
        QueryResponse response = solrClient.query(query);

        //取文档列表，取查询结果的总记录数
        SolrDocumentList documents = response.getResults();
        System.out.println("Result number is : " + documents.getNumFound());

        //遍历文档列表
        documents.forEach(document->{
            System.out.println(document.get("id"));
            System.out.println(document.get("item_title"));
            System.out.println(document.get("item_sell_point"));
            System.out.println(document.get("item_price"));
            System.out.println(document.get("item_image"));
            System.out.println(document.get("item_category_name"));
        });
    }

    @Test
    public void advancedQueryIndex() throws Exception{
        //创建一个solrserver对象
        String urlString = "http://localhost:8983/solr/core1";
        SolrClient solrClient = new HttpSolrClient.Builder(urlString).build();

        //创建solrquery对象
        SolrQuery query = new SolrQuery();

        //设置查询条件
        query.setQuery("华为");
        query.setStart(0);
        query.setRows(20);
        query.set("df", "item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");

        //执行查询，queryresponse对象
        QueryResponse response = solrClient.query(query);

        //取文档列表，取查询结果的总记录数
        SolrDocumentList documents = response.getResults();
        System.out.println("Result number is : " + documents.getNumFound());

        //遍历文档列表
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        documents.forEach(document->{
            System.out.println(document.get("id"));
            List<String> list = highlighting.get(document.get("id")).get("item_title");
            String title="";
            if(list != null && list.size() > 0){
                title = list.get(0);
            }else {
                title = (String)document.get("item_title");
            }
            System.out.println(title);
            System.out.println(document.get("item_sell_point"));
            System.out.println(document.get("item_price"));
            System.out.println(document.get("item_image"));
            System.out.println(document.get("item_category_name"));
        });

    }
}
