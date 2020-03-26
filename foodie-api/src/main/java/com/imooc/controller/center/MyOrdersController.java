package com.imooc.controller.center;

import com.immoc.utils.*;
import com.imooc.controller.BaseController;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.resources.FileUpload;
import com.imooc.service.center.MyOrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户中心我的订单",tags = {"用户中心我的订单相关接口"})
@RestController
@RequestMapping("/myorders")
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表",notes = "查询订单列表",httpMethod = "POST")
    @PostMapping("/query")
    public JSONResult query(
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus",value = "订单状态",required = false)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page",value = "页数",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页显示数量",required = false)
            @RequestParam Integer pageSize
    ){
        if(StringUtils.isBlank(userId)){
            return JSONResult.errorMsg("用户名不能为空！");
        }

        if (page == null){
            page = 1;
        }

        if(pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.queryMyOrders(userId,
                                                            orderStatus,
                                                            page,
                                                            pageSize);

        return JSONResult.ok(grid);
    }

    // 商家发货没有后端，所以这个接口仅仅只是用于模拟
    @ApiOperation(value="商家发货", notes="商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public JSONResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) throws Exception {

        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单ID不能为空");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return JSONResult.ok();
    }

    @ApiOperation(value="用户确认收货", notes="用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public JSONResult confirmRecevie(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId) throws Exception {

        JSONResult checkResult = checkUserOrder(orderId, userId);
        if(checkResult.getStatus() != HttpStatus.OK.value()){
            return checkResult;
        }

        boolean res = myOrdersService.updateReceiveOrderStatus(orderId);

        if(!res){
            return JSONResult.errorMap("订单确认收获失败");
        }

        return JSONResult.ok();
    }

    @ApiOperation(value="用户删除订单", notes="用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId) throws Exception {

        JSONResult checkResult = checkUserOrder(orderId, userId);
        if(checkResult.getStatus() != HttpStatus.OK.value()){
            return checkResult;
        }

        boolean res = myOrdersService.deleteOrder(orderId, userId);

        if(!res){
            return JSONResult.errorMap("订单删除失败！");
        }

        return JSONResult.ok();
    }

    @ApiOperation(value = "查询订单动向",notes = "查询订单动向",httpMethod = "POST")
    @PostMapping("/trend")
    public JSONResult trend(
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId,
            @ApiParam(name = "page",value = "页数",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页显示数量",required = false)
            @RequestParam Integer pageSize
    ){
        if(StringUtils.isBlank(userId)){
            return JSONResult.errorMsg("用户名不能为空！");
        }

        if (page == null){
            page = 1;
        }

        if(pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.getOrdersTrend(userId,
                                                                page,
                                                                pageSize);

        return JSONResult.ok(grid);
    }





}
