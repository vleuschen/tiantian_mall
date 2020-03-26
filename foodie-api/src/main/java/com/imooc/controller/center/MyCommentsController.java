package com.imooc.controller.center;

import com.immoc.enums.YesOrNo;
import com.immoc.utils.JSONResult;
import com.immoc.utils.PagedGridResult;
import com.imooc.controller.BaseController;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.service.center.MyCommentService;
import com.imooc.service.center.MyOrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.schema.Collections;

import java.util.List;

@Api(value = "用户中心评价模块",tags = {"用户中心我的评价相关接口"})
@RestController
@RequestMapping("/mycomments")
public class MyCommentsController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCommentsController.class);

    @Autowired
    private MyCommentService myCommentService;

    @ApiOperation(value = "查询我的评价",notes = "查询我的评价",httpMethod = "POST")
    @PostMapping("/query")
    public JSONResult query(
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

        PagedGridResult grid = myCommentService.quertMyComments(userId,
                page,
                pageSize);

        return JSONResult.ok(grid);
    }


    @ApiOperation(value = "查询订单列表",notes = "查询订单列表",httpMethod = "POST")
    @PostMapping("/pending")
    public JSONResult pending(
                                @ApiParam(name = "userId",value = "用户id",required = true)
                                @RequestParam String userId,
                                @ApiParam(name = "orderId",value = "订单id",required = false)
                                @RequestParam String orderId){

        //判断用户和订单是否关联
        JSONResult checkResult = checkUserOrder(orderId, userId);
        if(checkResult.getStatus() != HttpStatus.OK.value()){
            return checkResult;
        }

        //判断该笔订单是否已经评价过，评价过了就不用评价
        Orders myOrder = (Orders) checkResult.getData();
        if(myOrder.getIsComment() == YesOrNo.YES.type){
            return JSONResult.errorMap("该笔订单已经评价");
        }

        List<OrderItems> orderItems = myCommentService.queryPendingComment(orderId);

        return JSONResult.ok(orderItems);
    }

    @ApiOperation(value = "查询订单列表",notes = "查询订单列表",httpMethod = "POST")
    @PostMapping("/saveList")
    public JSONResult saveList(
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId",value = "订单id",required = false)
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList){

        LOGGER.info("当前评价为：{}",commentList);

        //判断用户和订单是否关联
        JSONResult checkResult = checkUserOrder(orderId, userId);
        if(checkResult.getStatus() != HttpStatus.OK.value()){
            return checkResult;
        }

        //判断评论内容不能为空
        if(commentList == null || commentList.isEmpty() || commentList.size() == 0){
            return JSONResult.errorMap("评论内容不能为空");
        }

        myCommentService.saveComments(orderId,userId,commentList);

        return JSONResult.ok();
    }

}
