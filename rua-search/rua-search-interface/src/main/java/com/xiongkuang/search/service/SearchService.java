package com.xiongkuang.search.service;

import com.xiongkuang.common.pojo.SearchResult;

/**
 * Created by xiongkuang on 13/12/2017.
 */
public interface SearchService {


    SearchResult search(String keyWord, int page, int rows) throws Exception;
}
