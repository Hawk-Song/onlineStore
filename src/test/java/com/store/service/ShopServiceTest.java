package com.store.service;

import com.store.BaseTest;
import com.store.dto.ShopExecution;
import com.store.entity.Area;
import com.store.entity.PersonInfo;
import com.store.entity.Shop;
import com.store.entity.ShopCategory;
import com.store.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop(){
        Shop shop =new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("test shop one");
        shop.setShopDesc("desc one");
        shop.setShopAddr("tes onet");
        shop.setPhone("test one");
        shop.setShopImg("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("/Users/savokisong/Downloads/SSM到Spring Boot-从零开发校园商铺平台/images/item/shop/15/20170605233021865310.jpg");

        ShopExecution se = shopService.addShop(shop, shopImg);
        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
    }
}
