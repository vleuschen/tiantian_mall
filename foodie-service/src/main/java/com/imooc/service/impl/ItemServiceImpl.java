package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.immoc.enums.CommentLevel;
import com.immoc.utils.DesensitizationUtil;
import com.immoc.utils.PagedGridResult;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {

        Example itemsImgExp = new Example(ItemsImg.class);

        Example.Criteria criteria = itemsImgExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        return itemsImgMapper.selectByExample(itemsImgExp);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {

        Example itemsSpecExp = new Example(ItemsSpec.class);

        Example.Criteria criteria = itemsSpecExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        return itemsSpecMapper.selectByExample(itemsSpecExp);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example itemsParamExp = new Example(ItemsParam.class);

        Example.Criteria criteria = itemsParamExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        return itemsParamMapper.selectOneByExample(itemsParamExp);
    }

    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {

        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);

        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO(totalCounts,goodCounts,normalCounts,badCounts);

        return commentLevelCountsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
     Integer getCommentCounts(String itemId,Integer level){

        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);

        if(level != null){
            condition.setCommentLevel(level);
        }

        return itemsCommentsMapper.selectCount(condition);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPageComments(String itemId, Integer level,
                                                 Integer page,Integer pageSize) {

        Map<String,Object> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);

        //mybatis-pagehelper
        PageHelper.startPage(page,pageSize);

        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);

        for (ItemCommentVO vo : list) {
            //对昵称进行脱敏处理
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }

        return setterPageGrid(list,page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {

        Map<String,Object> map = new HashMap<>();
        map.put("keywords",keywords);
        map.put("sort",sort);

        PageHelper.startPage(page,pageSize);

        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);

        return setterPageGrid(list,page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("catId",catId);
        map.put("sort",sort);

        PageHelper.startPage(page,pageSize);

        List<SearchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(map);

        return setterPageGrid(list,page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {

        String[] ids = specIds.split(",");

        List<String> specIdsList = new ArrayList<>();
        //将数组数据装进集合
        Collections.addAll(specIdsList,ids);

        return itemsMapperCustom.queryItemsBySpecIds(specIdsList);
    }

    private PagedGridResult setterPageGrid(List<?> list, Integer page){

        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPageSize());
        grid.setRecords(pageList.getTotal());

        return grid;
    }
}
