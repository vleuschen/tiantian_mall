package com.imooc.config;

import com.immoc.utils.DateUtil;
import com.imooc.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 */
@Component
public class OrderTask {

    @Autowired
    private OrdersService ordersService;


    /**
     * 使用定时任务关闭超期未支付的订单，会存在的弊端：
     * 1、会有时间差：导致程序不严谨
     *  10:39下单，11:00检查不足1小时，12:00检查，会超过一小时多余39分钟
     * 2、不支持就集群，
     *  单机没毛病，使用集群后，就会有多个定时任务
     *      解决方案： 只使用一台计算机节点，负责计算所有定时任务
     * 3、涉及到数据库全表搜素，极其影响数据库性能
     * 定时任务，仅仅只适用于小型轻量级项目，传统项目
     *
     * 后续课程涉及到消息队列： MQ: RabbitMQ、RocketMQ、Kafka、ZeroMQ...
     *  延时任务（队列）
     */

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder(){
        ordersService.closeOrder();
        System.out.println("执行定时任务 ，当前时间为："+ DateUtil.dateToString(new Date(),DateUtil.DATETIME_PATTERN));
    }



}
