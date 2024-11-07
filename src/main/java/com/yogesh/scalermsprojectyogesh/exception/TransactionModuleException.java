package com.yogesh.scalermsprojectyogesh.exception;

import com.yogesh.scalermsprojectyogesh.transaction.model.TransactionBean;

public class TransactionModuleException extends Exception {
    public TransactionModuleException() {
        super();
    }
    public TransactionModuleException(String message) {
        super(message);
    }
}
