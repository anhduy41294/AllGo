package com.kun.allgo.Utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 12120 on 7/2/2016.
 */
public class MessageDigestHelper {
    public static String DataHashMD5(String src)
    {
        byte[] dataToHash = src.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] data = md.digest(dataToHash);
        return Base64.encodeToString(data, Base64.URL_SAFE);
    }
}
