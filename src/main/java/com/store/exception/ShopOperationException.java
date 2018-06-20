package com.store.exception;

public class ShopOperationException extends RuntimeException {

    //﻿RuntimeException extends Exception. Exception extends Throwable.
    // Throwableimplements Serializable. So DataNotFoundException is Serializable too
    private static final long serialVersionUID = -2268997239050430939L;

    public ShopOperationException(String msg) {
        super(msg);
    }

}
