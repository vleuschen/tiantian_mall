package com.imooc.controller;

import com.immoc.utils.JSONResult;
import com.imooc.pojo.Orders;
import com.imooc.service.center.MyOrdersService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;


public class BaseController {

    public static final Integer COMMON_PAGE_SIZE = 10;

    public static final Integer PAGE_SIZE = 20;

    //支付中心的调用地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    //微信支付 ->支付中心 -> 天天吃货平台(回调支付的url)
    String payReturnUrl = "http://dfiy9h.natappfree.cc/orders/notifyMerchantOrderPaid";

    public static final String IMAGE_USER_FACE_LOCATION = "D:"+ File.separator
                                                            +"codel_files"+ File.separator
                                                            +"images"+ File.separator
                                                            +"foodie"+ File.separator
                                                            +"faces";

    @Autowired
    private MyOrdersService myOrdersService;

    /**
     * 验证用户和订单是否有关联关系，避免非法调用
     * @return
     */
    public JSONResult checkUserOrder(String orderId, String userId){

        Orders orders = myOrdersService.queryMyOrder(orderId, userId);
        if (orders == null){
            return JSONResult.errorMap("订单不存在");
        }
        return JSONResult.ok(orders);
    }


}
