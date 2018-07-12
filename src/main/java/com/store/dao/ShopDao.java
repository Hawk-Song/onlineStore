package com.store.dao;

import com.store.entity.Shop;

public interface ShopDao {

    /**
    * @Description: look up shop information by id
    * @Param:
    * @return:
    * @Author: Hawk.Song
    * @Date: 7/12/18
    */
    Shop queryByShopId(long shopId);

    /** 
    * @Description:  register a new shop
    * @Param:  shop class
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 6/16/18 
    */ 
    int insertShop(Shop shop);

    /** 
    * @Description: update shop info 
    * @Param:  shop
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 6/16/18 
    */ 
    int updateShop(Shop shop);
}
