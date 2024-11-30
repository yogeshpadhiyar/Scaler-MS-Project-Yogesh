package com.yogesh.scalermsprojectyogesh.transaction.model.entity;

public enum TransactionType {
    DEBIT(1, "DEBIT"),
    CREDIT(2, "CREDIT"),
    BLOCKED(3, "BLOCKED");

    public final int id;
    public final String name;

    TransactionType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TransactionType valueOf(int id) {
        for (TransactionType type : TransactionType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }

}
