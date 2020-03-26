package com.imooc.mapper;


import com.imooc.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemsCommentsMapperCustom {

    void saveComments(Map<String,Object> queryMap);

    List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String,Object> map);
}
