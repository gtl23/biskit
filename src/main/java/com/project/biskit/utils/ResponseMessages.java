package com.project.biskit.utils;

public class ResponseMessages {

    private ResponseMessages(){}

    public static final String RESPONSE_MSG_KEY = "error_msg";
    public static final String NO_ITEMS_TO_ADD = "Please provide some items to be added to store.";
    public static final String NO_ITEMS_FOUND = "No such item/s found.";
    public static final String CANNOT_DELETE = "This item cannot be deleted as an order is associated with it which is either active, delivered or cancelled.";
    public static final String STORE_EMPTY = "Your store is empty, please add some items!";
    public static final String INVALID_SIGN_UP_REQUEST = "Email, name and password are mandatory for signing up!";
    public static final String ACCOUNT_ALREADY_EXISTS = "An account already exists with this email!";
    public static final String INVALID_LOGIN_REQUEST = "Invalid credentials or account doesn't exists.";

}
