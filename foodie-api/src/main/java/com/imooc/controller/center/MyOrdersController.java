package com.imooc.controller.center;

import com.immoc.utils.*;
import com.imooc.controller.BaseController;
import com.imooc.resources.FileUpload;
import com.imooc.service.center.MyOrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户中心我的订单",tags = {"用户中心我的订单相关接口"})
@RestController
@RequestMapping("/myorders")
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    @Autowired
    private FileUpload fileUpload;

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
}
