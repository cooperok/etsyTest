package ua.cooperok.etsy.app;

import java.net.InetAddress;

import ua.cooperok.etsy.log.Logger;

public class CheckConnection {

    private static boolean sResult;

    public static boolean isInternetAvailable() {
        sResult = false;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress ipAddr = InetAddress.getByName("google.com");
                    sResult = !ipAddr.equals("");
                } catch (Exception e) {
                    Logger.getInstance().log(e);
                }
            }
        });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            Logger.getInstance().log(e);
        }

        return sResult;
    }

}