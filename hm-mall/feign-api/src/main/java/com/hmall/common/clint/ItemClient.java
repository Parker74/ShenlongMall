package com.hmall.common.clint;

import com.hmall.common.dto.Item;
import com.hmall.common.dto.PageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author HaoXiaoLong
 * @version 1.0
 * @date 2022/6/14 19:05
 */
@FeignClient(value = "itemservice")
public interface ItemClient {

    @GetMapping("/item/list")
    PageDTO<Item> page(@RequestParam("page") Integer page, @RequestParam("size") Integer size);

    @GetMapping("/item/{id}")
    Item selectById(@PathVariable Long id);
}
