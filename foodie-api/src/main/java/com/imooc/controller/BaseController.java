package com.imooc.controller;

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
}
