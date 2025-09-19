package com.example.inventory.exceptionhandling;

public class ErrorCodes {
    private ErrorCodes() {
        //No construction
    }
    public static final String ERRORCODE1 = "error1";
    public static final String ERRORCODE1MESSAGE = "Your input is not correct";

    public static final String ERRORCODE2 = "error2";
    public static final String ERRORCODE2MESSAGE = "Format of the Http Request body is not correct";

    public static final String ERRORCODE3 = "error3";
    public static final String ERRORCODE3MESSAGE = "Some unexpected error happened";

    public static final String ERRORCODE4 = "error4";
    public static final String ERRORCODE4MESSAGE = "Please check your input";
}
