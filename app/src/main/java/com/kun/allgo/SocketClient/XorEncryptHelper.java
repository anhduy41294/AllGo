package com.kun.allgo.SocketClient;

/**
 * Created by Duy on 07-Jul-16.
 */
public class XorEncryptHelper {
    public static String XorEncrypt(String data, String key){
        String rs = "";

        int dem = 0;
        for (int i = 0; i < data.length(); i++){
            rs +=(char) (data.charAt(i) ^ key.charAt(dem));
            dem++;
            if(dem >= key.length()){
                dem = 0;
            }
        }
        return rs;
    }
}
