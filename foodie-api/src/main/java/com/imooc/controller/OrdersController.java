package com.imooc.controller;

import com.immoc.enums.PayMethod;
import com.immoc.utils.CookieUtils;
import com.immoc.utils.JSONResult;
import com.immoc.utils.MobileEmailUtils;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.service.AddressService;
import com.imooc.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "订单相关",tags = {"订单相关的api"})
@RestController
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrdersService ordersService;

    @ApiOperation(value = "用户下蛋",notes = "用户下蛋",httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO,
                             HttpServletRequest request,
                             HttpServletResponse response){

        LOGGER.info("当前订单信息为： {}",submitOrderBO);

        if(submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type && submitOrderBO.getPayMethod()!= PayMethod.ALIPAY.type){
            return JSONResult.errorMsg("");
        }

        // 1、创建订单
        String orderId = ordersService.createOrder(submitOrderBO);

        // 2、创建订单完成之后，移除购物车中已经结算的商品
        /**
         * 1001
         * 1002 -> 用户购买
         * 1003 -> 用户购买
         * 1004
         */
        // TODO 整合redis之后，完善购物车中已结算商品清楚，并且同步前端的cookie
//        CookieUtils.setCookie(request,response,FOODIE_SHOPCART,"",true);//购物车清空

        // 3、向支付中心发送当前订单，用于保存支付中心的订单数据

        return JSONResult.ok(orderId);
    }






}
