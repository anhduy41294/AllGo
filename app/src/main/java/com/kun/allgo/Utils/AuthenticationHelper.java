package com.kun.allgo.Utils;

import android.util.Base64;

import com.kun.allgo.CryptographySystem.AESEncryptionSystem;
import com.kun.allgo.Global.GlobalVariable;

/**
 * Created by 12120 on 7/2/2016.
 */
public class AuthenticationHelper {
    public static boolean ComparehashP(String pass)
    {
        String temp = pass+"|"+ GlobalVariable.RandomP;

        String hashPRebuil = MessageDigestHelper.DataHashMD5(temp);
        if (hashPRebuil.equals(GlobalVariable.HashPServer) )
        {
            return true;
        }
        return false;
    }

    public static void BuilHashKClient(String pass)
    {
        String temp = pass+"|"+ GlobalVariable.RandomK;

        String hashKbuil = MessageDigestHelper.DataHashMD5(temp);

        GlobalVariable.HashKClient = hashKbuil;
    }

    public static void DecryptMasterKey(String pass)
    {
        byte[] dataToDecrypt  = Base64.decode(GlobalVariable.MasterKeyEncryptedServer, Base64.URL_SAFE);

        BuilHashKClient(pass);

        byte[] key = Base64.decode(GlobalVariable.HashKClient, Base64.URL_SAFE);

        AESEncryptionSystem aesEncryptionSystem = new AESEncryptionSystem();
        aesEncryptionSystem.setKey(key);
        aesEncryptionSystem.setDataToDecrypt(dataToDecrypt);
        aesEncryptionSystem.Decrypt();

        GlobalVariable.MasterKeyServer = aesEncryptionSystem.getDataOutput();

    }

    public static void EncryptMasterKey(String pass)
    {
        byte[] dataToEncrypt  = Base64.decode(GlobalVariable.MasterKeyServer, Base64.URL_SAFE);

        BuilHashKClient(pass);

        byte[] key = Base64.decode(GlobalVariable.HashKClient, Base64.URL_SAFE);

        AESEncryptionSystem aesEncryptionSystem = new AESEncryptionSystem();
        aesEncryptionSystem.setKey(key);
        aesEncryptionSystem.setDataToEncrypt(dataToEncrypt);
        aesEncryptionSystem.Encrypt();

        GlobalVariable.MasterKeyEncryptedServer = Base64.encodeToString(aesEncryptionSystem.getDataOutput(), Base64.URL_SAFE);
    }

    public static String EncryptData(String data)
    {
//        byte[] dataToEncrypt  = Base64.decode(data, Base64.URL_SAFE);
        byte[] dataToEncrypt  = data.getBytes();

        AESEncryptionSystem aesEncryptionSystem = new AESEncryptionSystem();
        aesEncryptionSystem.setKey(GlobalVariable.MasterKeyServer);
        aesEncryptionSystem.setDataToEncrypt(dataToEncrypt);
        aesEncryptionSystem.Encrypt();

        return Base64.encodeToString(aesEncryptionSystem.getDataOutput(), Base64.URL_SAFE);
    }

    public static String DecryptData(String data)
    {
        byte[] dataToDecrypt  = Base64.decode(data, Base64.URL_SAFE);

        AESEncryptionSystem aesEncryptionSystem = new AESEncryptionSystem();
        aesEncryptionSystem.setKey(GlobalVariable.MasterKeyServer);
        aesEncryptionSystem.setDataToDecrypt(dataToDecrypt);
        aesEncryptionSystem.Decrypt();

//        String st = Base64.encodeToString(aesEncryptionSystem.getDataOutput(), Base64.URL_SAFE);
        byte[] b = aesEncryptionSystem.getDataOutput();
        String st = new String(b);
        return st;
    }

    public static void GenerateMasterKeySystem(String pass)
    {
        RandomHelper randomHelper = new RandomHelper(8);
        GlobalVariable.RandomP = randomHelper.nextString();
        GlobalVariable.RandomK = randomHelper.nextString();

        String tempP = pass+"|"+ GlobalVariable.RandomP;
        String tempK = pass+"|"+ GlobalVariable.RandomK;

        GlobalVariable.HashPServer = MessageDigestHelper.DataHashMD5(tempP);
        GlobalVariable.HashKClient = MessageDigestHelper.DataHashMD5(tempK);

        AESEncryptionSystem aesEncryptionSystem = new AESEncryptionSystem();

        AESEncryptionSystem aesEncryptionSystemServer = new AESEncryptionSystem();
        GlobalVariable.MasterKeyServer = aesEncryptionSystemServer.GenerateKey();

        aesEncryptionSystem.setKey(Base64.decode(GlobalVariable.HashKClient,Base64.URL_SAFE));
        aesEncryptionSystem.setDataToEncrypt(GlobalVariable.MasterKeyServer);
        aesEncryptionSystem.Encrypt();

        GlobalVariable.MasterKeyEncryptedServer = Base64.encodeToString(aesEncryptionSystem.getDataOutput(),Base64.URL_SAFE);
    }

    public static void PasswordChangeEncryptKey(String pass)
    {
        String tempP = pass+"|"+ GlobalVariable.RandomP;
        String tempK = pass+"|"+ GlobalVariable.RandomK;

        GlobalVariable.HashPServer = MessageDigestHelper.DataHashMD5(tempP);
        GlobalVariable.HashKClient = MessageDigestHelper.DataHashMD5(tempK);

        AESEncryptionSystem aesEncryptionSystem = new AESEncryptionSystem();

        aesEncryptionSystem.setKey(Base64.decode(GlobalVariable.HashKClient,Base64.URL_SAFE));
        aesEncryptionSystem.setDataToEncrypt(GlobalVariable.MasterKeyServer);
        aesEncryptionSystem.Encrypt();

        GlobalVariable.MasterKeyEncryptedServer = Base64.encodeToString(aesEncryptionSystem.getDataOutput(),Base64.URL_SAFE);
    }
}
