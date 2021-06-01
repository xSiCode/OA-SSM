package com.th.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Interface : SearchMapper
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/28 16:14
 * @Version : 1.0
 */

@Repository
public interface SearchMapper {

    List<Map<String,Object>> getResultByTitle(@Param("userId") Integer userId,
                                              @Param("title") String title);
}
