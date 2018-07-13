package com.store.util;

public class PageCalculator {
    //if index is 1, means we chose form first page,  that is we choose start from 0th row
    //if pageIndex is 2, pageSize is 5, then we choose start from 5
    public static int calculateRowIddex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
