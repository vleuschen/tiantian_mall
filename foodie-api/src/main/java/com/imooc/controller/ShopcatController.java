package com.imooc.controller;

import com.immoc.utils.JSONResult;
import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.service.StuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车接口Controller",tags = {"购物车接口Controller"})
@RestController
@RequestMapping("/shopcart")
public class ShopcatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopcatController.class);

    @ApiOperation(value = "添加商品到购物车",notes = "添加商品到购物车")
    @PostMapping("/add")
    public JSONResult add(@RequestParam String userId,
                          @RequestBody ShopcartBO shopcartBO,
                          HttpServletRequest request,
                          HttpServletResponse response){

        if (StringUtils.isBlank(userId)){
            return JSONResult.errorMsg("");
        }

        LOGGER.info("当前的购物车数据为: {}",shopcartBO);
        //TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存4


        return JSONResult.ok();
    }

    @ApiOperation(value = "从购物车中删除商品",notes = "从购物车中删除商品")
    @PostMapping("/del")
    public JSONResult del(@RequestParam String userId,
                          @RequestParam String itemSpecId,
                          HttpServletRequest request,
                          HttpServletResponse response){

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)){
            return JSONResult.errorMsg("参数不能为空");
        }

        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，如果此时用户已经登录，需要删除reids中的数据

        return JSONResult.ok();
    }

}
