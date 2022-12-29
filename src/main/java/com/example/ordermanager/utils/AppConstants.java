package com.example.ordermanager.utils;

public class AppConstants {

    public static final String INVALID_ITEM_NAME = "Item already exist with informed name!";
    public static final String INVALID_ITEM = "Item doesn't exist with informed name!";
    public static final String ITEM_NOT_FOUND = "Item not found with informed ID!";
    public static final String ITEM_DELETED = "Item deleted successfully.!";

    public static final String INVALID_USER_EMAIL = "User already exist with informed email!";
    public static final String INVALID_USER = "User doesn't exist with informed name!";

    public static final String STOCK_NOT_FOUND = "Stock Movement not found with informed ID!";
    public static final String STOCK_DELETED = "Stock Movement deleted successfully.!";
    public static final String INVALID_STOCK_WITHDRAWAL = "Stock withdrawal movement can't be done! This Item doesn't have stock amount enough for this operation.";

    public static final String USER_NOT_FOUND = "User not found with informed ID!";
    public static final String USER_DELETED = "User deleted successfully.!";

    public static final String ORDER_NOT_FOUND = "Order not found with informed ID!";
    public static final String ORDER_DELETED = "Order Movement deleted successfully.!";
    public static final String INVALID_ORDER_STATUS = "Invalid status! Only orders awaiting stock can be canceled";
}
