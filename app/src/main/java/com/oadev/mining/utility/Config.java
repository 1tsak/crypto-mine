package com.oadev.mining.utility;

public class Config {
    public static String BASE_URL = "http://nincoin.org/miner/";
    public static String loginUrl = BASE_URL + "auth.php?";
    public static String signupUrl = BASE_URL + "signup.php?";
    public static String functionUrl = BASE_URL + "function.php?";
    public static String paytmMID = "";

    public static float storedamount;

    public static String referedby;

    public static boolean isTimerRunning = false;

    public static float currentamount, amount;
    public static String miningstatus;
}
