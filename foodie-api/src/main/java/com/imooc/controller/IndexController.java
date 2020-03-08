package com.imooc.controller;

import com.immoc.enums.YesOrNo;
import com.immoc.utils.JSONResult;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.service.StuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "首页",tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult carousel(){

        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);
        return JSONResult.ok(carousels);
    }

    /**
     * 首页分类展示需求：
     *  1、第一次刷新主页查询大分类，渲染展示到首页
     *  2、如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "获取商品分类（一级分类）",notes = "获取商品分类（一级分类）",httpMethod = "GET")
    @GetMapping("/cats")
    public JSONResult cats(){

        List<Category> categories = categoryService.queryAllRootLevelCat();
        return JSONResult.ok(categories);
    }

    @ApiOperation(value = "获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(    @PathVariable("rootCatId")
                                     @ApiParam(name = "rootCatId",value = "商品一级分类id",required = true)
                                         Integer rootCatId){

        if(rootCatId == null){
            return JSONResult.errorMsg("你妈死了");
        }

        List<CategoryVO> subCatList = categoryService.getSubCatList(rootCatId);
        return JSONResult.ok(subCatList);
    }

    @ApiOperation(value = "查询每个一级分类下的最新六条消息",notes = "查询每个一级分类下的最新六条消息",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sitNewItems(    @PathVariable("rootCatId")
                                 @ApiParam(name = "rootCatId",value = "商品一级分类id",required = true)
                                         Integer rootCatId){

        if(rootCatId == null){
            return JSONResult.errorMsg("你妈死了");
        }

        List<NewItemsVO> newItemsVOList = categoryService.getSixNewItemsLazy(rootCatId);
        return JSONResult.ok(newItemsVOList);
    }

}
