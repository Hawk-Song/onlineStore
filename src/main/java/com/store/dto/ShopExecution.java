package com.store.dto;

import com.store.entity.Shop;
import com.store.enums.ShopStateEnum;

import java.util.List;

public class ShopExecution {
    // result state
    private int state;

    // words to explain what the state mean
    private String stateInfo;

    //count of shop
    private int count;

    //shop in operation(use in add shop or delete shop)
    private Shop shop;

    //shop list(used in search)
    private List<Shop> shopList;


    //constructor for failure to register a shop
    public ShopExecution(ShopStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //constructor for success to register a shop
    public ShopExecution(ShopStateEnum stateEnum, Shop shop){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    //
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shop){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
