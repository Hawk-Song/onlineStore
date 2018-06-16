package com.store.dao;

import com.store.entity.Area;

import java.util.List;

public interface AreaDao {
    /**
    * @Description: get area list
    * @Param:  none
    * @return:  list
    * @Author: Hawk.Song 
    * @Date: 6/15/18 
    */ 
    List<Area> queryArea();
}
