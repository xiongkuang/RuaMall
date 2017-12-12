package com.xiongkuang.search.service.impl;

import com.xiongkuang.common.pojo.SearchResult;
import com.xiongkuang.search.dao.SearchDao;
import com.xiongkuang.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiongkuang on 13/12/2017.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyWord, int page, int rows) throws Exception{
        //创建solrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(keyWord);
        //设置分页条件
        if (page <= 0){
            page = 1;
        }
        query.setStart((page - 1) * rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df", "item_title");
        //设置高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //调用dao执行查询
        SearchResult result = searchDao.search(query);
        //计算总页数，返回结果;
        long recordCount = result.getRecordCount();
        int totalPages = (int)(recordCount/rows);
        if (recordCount % rows > 0){
            totalPages++;
        }
        result.setTotalPages(totalPages);
        return result;
    }
}
