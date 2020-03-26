package com.imooc.service.center;

import com.github.pagehelper.PageInfo;
import com.immoc.utils.PagedGridResult;

import java.util.List;

public class BaseService {


    public PagedGridResult setterPageGrid(List<?> list, Integer page){

        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPageSize());
        grid.setRecords(pageList.getTotal());

        return grid;
    }

}
