package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrdersMapperCustom{


    List<MyOrdersVO> queryMyOrders(@Param("paramsMap")Map<String,Object> map);
}