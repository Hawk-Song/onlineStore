package com.store.enums;

public enum ShopStateEnum {
    CHECK(0, "under review"), OFFLINE(-1, "illegal shop"), SUCCESS(1, "succeed"), PASS(2, "verified"),
    INNER_ERROR(-1001, "inner system error"), NULL_SHOPID(-1002, "Shop id is null"),
    NULL_SHOP(-1003, "Shop information is null");

    private int state;
    private String stateInfo;

    ShopStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopStateEnum stateOf(int state){
        for(ShopStateEnum stateEnum : values()){
            if(stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }


    public String getStateInfo() {
        return stateInfo;
    }
    // we do not want method out of program to change the value of state, so not setter here
}
