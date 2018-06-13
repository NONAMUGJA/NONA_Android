package com.dgsw.nona.data;

public class Data {
    //TODO USER ID
    private static String userID = "2208";

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        Data.userID = userID;
    }

    public static void clear() {
        userID = null;
    }
}
