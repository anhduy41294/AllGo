package com.kun.allgo.Utils;

import com.kun.allgo.Global.GlobalVariable;

/**
 * Created by 12120 on 7/1/2016.
 */
public class QRCodeDataParser {
    public static void QRCodeConnectPCParser(String data){
        String[] element = data.split("[|]");
        GlobalVariable.IPCurrentPC = element[0];
        GlobalVariable.PortCurrentPC = Integer.parseInt(element[1]);
        GlobalVariable.keyAutoLoginController = ByteTransformUtils.TransformFromCSharp(element[2]);

        GlobalVariable.aesEncryptionAutoLogin.setKey(GlobalVariable.keyAutoLoginController);
    }

}
