package com.store.dao;

import com.store.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /** 
    * @Description: paging query, codition can be shop name(vage), shop status, shop category, area id, owner
    * @Param:  
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 7/13/18 
    */ 
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
    
    /** 
    * @Description:  return the total count of shop list
    * @Param:  
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 7/13/18 
    */ 
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
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
