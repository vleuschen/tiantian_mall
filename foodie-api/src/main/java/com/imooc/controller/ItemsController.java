package com.imooc.controller;

import com.immoc.utils.JSONResult;
import com.immoc.utils.PagedGridResult;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.*;
import com.imooc.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口",tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("/items")
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONResult info(
                                @ApiParam(name = "itemId",value = "商品id",required = true)
                                @PathVariable("itemId") String itemId
                                ){
        if(StringUtils.isBlank(itemId)){
            return JSONResult.errorMsg(null);
        }

        Items items = itemService.queryItemById(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO(items,itemsParam,itemsImgList,itemsSpecList);

        return JSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级",notes = "查询商品评价等级",httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam String itemId
    ){
        if(StringUtils.isBlank(itemId)){
            return JSONResult.errorMsg(null);
        }

        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);

        return JSONResult.ok(countsVO);
    }

    @ApiOperation(value = "查询商品评论",notes = "查询商品评论",httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult comments(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level",value = "评价等级",required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page",value = "页数",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页显示数量",required = false)
            @RequestParam Integer pageSize
    ){
        if(StringUtils.isBlank(itemId)){
            return JSONResult.errorMsg(null);
        }

        if (page == null){
            page = 1;
        }

        if(pageSize == null){
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = itemService.queryPageComments(itemId,
                        level,page,pageSize);

        return JSONResult.ok(grid);
    }

    @ApiOperation(value = "搜索商品列表",notes = "搜索商品列表",httpMethod = "GET")
    @GetMapping("/search")
    public JSONResult search(
            @ApiParam(name = "keywords",value = "商品id",required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "页数",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页显示数量",required = false)
            @RequestParam Integer pageSize
    ){
        if(StringUtils.isBlank(keywords)){
            return JSONResult.errorMsg(null);
        }

        if (page == null){
            page = 1;
        }

        if(pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);

        return JSONResult.ok(grid);
    }

    @ApiOperation(value = "通过分类id搜索商品列表",notes = "通过分类id搜索商品列表",httpMethod = "GET")
    @GetMapping("/catItems")
    public JSONResult catItems(
            @ApiParam(name = "catId",value = "三级分类id",required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "页数",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页显示数量",required = false)
            @RequestParam Integer pageSize
    ){
        if( catId == null ){
            return JSONResult.errorMsg(null);
        }

        if (page == null){
            page = 1;
        }

        if(pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(catId, sort, page, pageSize);

        return JSONResult.ok(grid);
    }

    //用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格）
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据",notes = "根据商品规格ids查找最新的商品数据",httpMethod = "GET")
    @GetMapping("/refresh")
    public JSONResult refresh(
                                @ApiParam(name = "itemSpecIds",value = "拼接的规格ids",required = true,example = "1001,1002,1003")
                                @RequestParam String itemSpecIds){

        if (StringUtils.isBlank(itemSpecIds)){
            return JSONResult.ok();
        }

        List<ShopcartVO> itemsSpecList = itemService.queryItemsBySpecIds(itemSpecIds);

        return JSONResult.ok(itemsSpecList);
    }

}
