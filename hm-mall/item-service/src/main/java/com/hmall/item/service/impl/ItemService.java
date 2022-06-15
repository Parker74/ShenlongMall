package com.hmall.item.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hmall.item.mapper.ItemMapper;
import com.hmall.item.pojo.Item;
import com.hmall.item.service.IItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ItemService extends ServiceImpl<ItemMapper, Item> implements IItemService {



}
