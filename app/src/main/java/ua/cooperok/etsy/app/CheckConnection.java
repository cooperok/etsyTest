package ua.cooperok.etsy.app;

import java.net.InetAddress;

import ua.cooperok.etsy.log.Logger;

public class CheckConnection {

    public static boolean isInternetAvailable() {
        boolean result = false;
        try {
            InetAddress ipAddr = InetAddress.getByName("8.8.8.8");
            result = !ipAddr.equals("");
        } catch (Exception e) {
            Logger.getInstance().log(e);
        }
        return result;
    }

}