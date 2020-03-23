package com.imooc.service.center.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.immoc.utils.PagedGridResult;
import com.imooc.mapper.OrdersMapperCustom;
import com.imooc.pojo.vo.MyOrdersVO;
import com.imooc.service.center.MyOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements MyOrdersService {

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {

        Map<String,Object> map = new HashMap<>();

        map.put("userId",userId);
        if(orderStatus != null){
            map.put("orderStatus",orderStatus);
        }

        PageHelper.startPage(page,pageSize);
        List<MyOrdersVO> list = ordersMapperCustom.queryMyOrders(map);

        return setterPageGrid(list,page);
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
