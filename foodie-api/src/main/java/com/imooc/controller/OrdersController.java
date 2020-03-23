package com.imooc.controller;

import com.immoc.enums.OrderStatusEnum;
import com.immoc.enums.PayMethod;
import com.immoc.utils.JSONResult;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.MerchantOrdersVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单相关",tags = {"订单相关的api"})
@RestController
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private RestTemplate restTemplate;

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
        OrderVO orderVO = ordersService.createOrder(submitOrderBO);

        String orderId = orderVO.getOrderId();

        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

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

        //为了方便测试购买，所有的支付金额都统一改为一分钱
        merchantOrdersVO.setAmount(1);;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "xxx");
        headers.add("password", "xxx");

        HttpEntity<MerchantOrdersVO> httpEntity =
                new HttpEntity<MerchantOrdersVO>(merchantOrdersVO,headers);

        ResponseEntity<JSONResult> responseEntity = restTemplate.postForEntity(paymentUrl, httpEntity, JSONResult.class);

        JSONResult paymentResult = responseEntity.getBody();
        if(paymentResult.getStatus() != 200){
            return JSONResult.errorMap("支付中心订单创建失败，请联系管理员");
        }

        return JSONResult.ok(orderId);
    }

    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){
        ordersService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELEVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("/getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(String orderId){

        OrderStatus orderStatus = ordersService.queryOrderStatusInfo(orderId);

        return JSONResult.ok(orderStatus);
    }



}
