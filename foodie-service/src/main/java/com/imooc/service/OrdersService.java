package com.imooc.service;

import com.imooc.pojo.Category;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;

import java.util.List;

public interface OrdersService {

    /**
     * 创建订单相关信息
     * @param submitOrderBO
     */
    String createOrder(SubmitOrderBO submitOrderBO);



}
