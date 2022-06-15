package com.hmall.web;

import com.alibaba.fastjson.JSON;
import com.hmall.common.clint.ItemClient;
import com.hmall.common.dto.Item;
import com.hmall.common.dto.PageDTO;
import com.hmall.pojo.ItemDoc;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/6/14 14:31
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ItemClient itemClient;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/list")
    public PageDTO<Item> page() {
        return itemClient.page(1, 5);
    }

    /**
     * 数据同步
     * @return
     */
    @GetMapping("/h")
    public String term() {
        int page = 1;
        while (true) {
            PageDTO<Item> pageInfo = itemClient.page(page, 500);
            List<Item> list = pageInfo.getList();
            if (list.size() <= 0) {
                break;
            }
            //request
            BulkRequest request = new BulkRequest();
            for (Item item : list) {
                if (item.getStatus() == 2) {
                    // 下架商品直接跳过
                    continue;
                }
                ItemDoc itemDoc = new ItemDoc(item);
                //链式编程
                request.add(new IndexRequest("item").id(itemDoc.getId().toString())
                        .source(JSON.toJSONString(itemDoc), XContentType.JSON));
            }
            try {
                restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            page++;
        }
        return "h";
    }

    @GetMapping("/suggestion")
    public List<String> searchBySuggestion(String key){



        return null;
    }
}