package com.imooc.controller.center;

import com.immoc.utils.JSONResult;
import com.imooc.pojo.Users;
import com.imooc.service.center.CenterUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "center - 用户中心",tags = {"用户中心展示的相关接口"})
@RestController
@RequestMapping("/center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "GET")
    @GetMapping("/userInfo")
    public JSONResult userInfo(@ApiParam(name="userId",required = true) @RequestParam String userId){

        Users users = centerUserService.queryUserInfo(userId);
        return JSONResult.ok(users);

    }

}
