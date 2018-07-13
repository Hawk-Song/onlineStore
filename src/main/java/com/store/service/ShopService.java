package com.store.service;

import com.store.dto.ShopExecution;
import com.store.entity.Shop;
import com.store.exception.ShopOperationException;

import java.io.InputStream;

public interface ShopService {

    /** 
    * @Description:  return shop list pagingly based on the shopCondition
    * @Param:  
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 7/13/18 
    */ 
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
    /** 
    * @Description: get shop information by id 
    * @Param:  
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 7/12/18 
    */ 
    Shop getByshopId(long shopId);
    
    /** 
    * @Description: update shop information, including the process for picture 
    * @Param:  
    * @return:  
    * @Author: Hawk.Song 
    * @Date: 7/12/18 
    */ 
    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

    
    
    
    
    
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
}
