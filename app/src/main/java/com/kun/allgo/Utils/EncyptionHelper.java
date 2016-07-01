package com.kun.allgo.Utils;

import com.kun.allgo.Global.GlobalVariable;

/**
 * Created by 12120 on 7/1/2016.
 */
public class EncyptionHelper {
    public static String EncryptDataAutoLogin(String src){
        String rs = "";
        GlobalVariable.aesEncryptionAutoLogin.setKey(GlobalVariable.keyAutoLoginController);
        GlobalVariable.aesEncryptionAutoLogin.setDataToEncrypt(src.getBytes());
        GlobalVariable.aesEncryptionAutoLogin.Encrypt();
        rs = ByteTransformUtils.TransformToCSharp(GlobalVariable.aesEncryptionAutoLogin.getDataOutput());
        return rs;
    }

    public static String DecryptDataAutoLogin(String src){
        String rs = "";

        return rs;
    }
}
