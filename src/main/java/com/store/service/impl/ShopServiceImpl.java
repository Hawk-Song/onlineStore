package com.store.service.impl;

import com.store.dao.ShopDao;
import com.store.dto.ShopExecution;
import com.store.entity.Shop;
import com.store.enums.ShopStateEnum;
import com.store.exception.ShopOperationException;
import com.store.service.ShopService;
import com.store.util.ImageUtil;
import com.store.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service // means this class need spring to manage, or you get noSuchBeanDefinition error
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional // means this method need transaction support
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
        //check if shop information is null
        if(shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        //if shop information is not null, start to register shop
        try{
            shop.setEnableStatus(0); // 0: in censor,
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum <= 0) {
                //why use runtime Exception? because only when method throw ShopOperationException or implement
                //ShopOperationException can the transaction terminate and roll back
                throw new ShopOperationException("failed to create the shop");
            } else {
                if(shopImgInputStream != null) {
                    //store pics
                    try {
                        addShopImg(shop, shopImgInputStream, fileName);
                    } catch(Exception e) {
                        throw new ShopOperationException("addShopImg Error: " + e.getMessage());
                    }
                    //update shop pics address
                    effectedNum = shopDao.updateShop(shop);
                    if(effectedNum <= 0) {
                        throw new ShopOperationException("fail to update pic address");
                    }
                }
            }
        } catch(Exception e) {
            throw new ShopOperationException("addShop error: "+ e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName){
        //get related path of shop pic directoy
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);
        shop.setShopImg(shopImgAddr);

    }

    @Override
    public Shop getByshopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {

        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            try {
                // determine if need to process pic
                if (shopImgInputStream != null && fileName != null && !fileName.equals("")) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopId() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, shopImgInputStream, fileName);
                }

                //update shop infromation
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error" + e.getMessage());
            }
        }
    }
}
