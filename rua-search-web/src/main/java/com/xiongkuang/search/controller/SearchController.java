package com.xiongkuang.search.controller;

import com.xiongkuang.common.pojo.SearchResult;
import com.xiongkuang.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品搜索
 * Created by xiongkuang on 13/12/2017.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")

    public String earchItemList(Model model, String keyword, @RequestParam(defaultValue = "1") Integer page) throws Exception{
        keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");

        SearchResult result =  searchService.search(keyword, page, SEARCH_RESULT_ROWS);
        model.addAttribute("query", keyword);
        model.addAttribute("totalpages", result.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("recordCount", result.getRecordCount());
        model.addAttribute("itemList", result.getItemList());
        return "search";
    }
}
