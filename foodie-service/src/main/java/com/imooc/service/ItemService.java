package com.imooc.service;

import com.immoc.utils.PagedGridResult;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.ShopcartVO;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品id查询详情
     * @param id
     * @return
     */
    Items queryItemById(String id);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id 查询商品参数
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品的评价（分页）
     * @param itemId
     * @param level
     * @return
     */
    PagedGridResult queryPageComments(String itemId, Integer level,
                                      Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(String keywords, String sort,
                                      Integer page, Integer pageSize);


    /**
     * 根据三级分类id搜索商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(Integer catId, String sort,
                                Integer page, Integer pageSize);

    /**
     * 根据规格ids查询最新的购物车中的商品数据（用于刷新渲染）
     * @param specIds
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);
}
