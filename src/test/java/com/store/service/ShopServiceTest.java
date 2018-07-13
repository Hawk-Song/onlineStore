package com.store.service;

import com.store.BaseTest;
import com.store.dto.ShopExecution;
import com.store.entity.Area;
import com.store.entity.PersonInfo;
import com.store.entity.Shop;
import com.store.entity.ShopCategory;
import com.store.enums.ShopStateEnum;
import com.store.exception.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;


    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(3L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition, 2, 2);
        System.out.println("shop list number: " + se.getShopList().size());
        System.out.println("shop total number: " + se.getCount());
    }

    @Test
    public void testModifyShop() throws ShopOperationException, FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(4L);
        shop.setShopName("modified Name");
        File shopImg = new File("/Users/savokisong/Downloads/SSM到Spring Boot-从零开发校园商铺平台/images/item/shop/20/20170606203631552081.png");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution se = shopService.modifyShop(shop, is, shopImg.getName());
        System.out.println("new pic addr: " + se.getShop().getShopImg());

    }

    @Test
    public void testAddShop() throws FileNotFoundException {
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
        shop.setShopName("test shop two");
        shop.setShopDesc("desc two");
        shop.setShopAddr("tes onet");
        shop.setPhone("test one");
        shop.setShopImg("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("/Users/savokisong/Downloads/SSM到Spring Boot-从零开发校园商铺平台/images/item/shop/15/20170605233021865310.jpg");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution se = shopService.addShop(shop, is, shopImg.getName());
        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
    }
}
