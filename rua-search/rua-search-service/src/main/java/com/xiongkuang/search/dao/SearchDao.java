package com.xiongkuang.search.dao;

import com.xiongkuang.common.pojo.SearchItem;
import com.xiongkuang.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *根据条件查询索引
 * Created by xiongkuang on 13/12/2017.
 */

@Repository
public class SearchDao {

    @Autowired
    private SolrClient solrClient;

    public SearchResult search(SolrQuery query) throws Exception{
        //根据query查询索引库
        QueryResponse response = solrClient.query(query);

        //取查询结果
        SolrDocumentList documents= response.getResults();

        //取查询结果总记录数
        long numFound = documents.getNumFound();
        SearchResult result = new SearchResult();
        result.setRecordCount(numFound);

        //取商品列表，高亮显示
        Map<String, Map<String, List<String>>> highlighting= response.getHighlighting();
        List<SearchItem> itemList = new ArrayList<>();
        documents.forEach(document -> {
            SearchItem item = new SearchItem();
            item.setId((String)document.get("id"));
            item.setCategory_name((String)document.get("item_category_name"));
            item.setImage((String)document.get("item_image"));
            item.setPrice((long)document.get("item_price"));
            item.setSell_point((String)document.get("item_sell_point"));
            List<String> list = highlighting.get(document.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0){
                title = list.get(0);
            }else{
                title = (String)document.get("item_title");
            }
            item.setTitle(title);
            //添加到商品列表
            itemList.add(item);
        });
        result.setItemList(itemList);
        return result;
    }
}
