package com.hmall.item.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmall.common.dto.PageDTO;
import com.hmall.item.pojo.Item;
import com.hmall.item.service.IItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private IItemService itemService;

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public PageDTO<Item> page(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        log.info("page : {}", page);
        log.info("size : {}", size);
        Page<Item> itemPage = itemService.page(new Page<>(page, size), null);
        return new PageDTO<Item>(itemPage.getTotal(), itemPage.getRecords());
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Item selectById(@PathVariable Long id) {
        log.info("id : {}", id);
        Item item = itemService.getById(id);
        return item;
    }

    /**
     * 添加商品
     * @param item
     * @return
     */
    @PostMapping
    public String insert(@RequestBody Item item) {
        itemService.save(item);
        return "新增商品成功";
    }

    /**
     * 上架下架商品
     * @param id
     * @param status
     * @return
     */
    @PutMapping("status/{id}/{status}")
    public String update(@PathVariable Long id, @PathVariable Integer status) {
        log.info("id : {}", id);
        log.info("status : {}", status);

        Item item = new Item();
        item.setId(id);
        item.setStatus(status);
        item.setUpdateTime(new Date());
        itemService.updateById(item);
        return "修改成功";
    }

    /**
     * 根据id修改商品
     * @param item
     * @return
     */
    @PutMapping
    public String update(@RequestBody Item item) {
        itemService.updateById(item);
        return "修改成功";
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String remevo(@PathVariable Long id) {
        itemService.removeById(id);
        return "删除成功";
    }
}