package com.kun.allgo.CryptographySystem;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 12120 on 7/1/2016.
 */
public class AESEncryptionSystem {
    private byte[] Key;
    private byte[] DataToEncrypt;
    private byte[] DataToDecrypt;
    private byte[] DataOutput;

    public byte[] GenerateKey()
    {
        Random rnd =new Random();
        Key = new byte[24];
        for (int i = 0; i < 24; i++)
        {
            Key[i] = (byte)(rnd.nextInt(256) - 128);
        }
        return Key;
    }

    public void Encrypt()
    {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(Key,"AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            DataOutput = cipher.doFinal(DataToEncrypt);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

    }
    public void Decrypt()
    {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(Key,"AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            DataOutput = cipher.doFinal(DataToDecrypt);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

    }

    public byte[] getKey() {
        return Key;
    }

    public void setKey(byte[] key) {
        Key = key;
    }

    public byte[] getDataToEncrypt() {
        return DataToEncrypt;
    }

    public void setDataToEncrypt(byte[] dataToEncrypt) {
        DataToEncrypt = dataToEncrypt;
    }

    public byte[] getDataToDecrypt() {
        return DataToDecrypt;
    }

    public void setDataToDecrypt(byte[] dataToDecrypt) {
        DataToDecrypt = dataToDecrypt;
    }

    public byte[] getDataOutput() {
        return DataOutput;
    }

    public void setDataOutput(byte[] dataOutput) {
        DataOutput = dataOutput;
    }
}
