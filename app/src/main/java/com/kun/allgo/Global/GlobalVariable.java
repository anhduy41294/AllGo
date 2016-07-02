package com.kun.allgo.Global;

import com.kun.allgo.CryptographySystem.AESEncryptionSystem;
import com.kun.allgo.Models.AppUser;
import com.kun.allgo.Models.Workspace;

/**
 * Created by 12120 on 4/16/2016.
 */
public class GlobalVariable {
    public static AppUser CurrentAppUser;
    public static String currentUserId;
    public static String currentWorkspaceId;
    public static Workspace currentWorkspace;
    public static String currentRoomId;
    public static String latitude = "";
    public static String longititude = "";
    public static String IPCurrentPC = "192.168.1.43";
    public static AESEncryptionSystem aesEncryptionAutoLogin = new AESEncryptionSystem();
    public static int PortCurrentPC = 11000;
    public static byte[] keyAutoLoginController =new byte[] { 1, 2, 3, 4, 5,
            6, 7, 8, 9, 10, 11, 12, 13,
            14, 110, 111, 112, 113, 114, 115, 116,
            117, 118, 119 };
    public static String RandomP = "";
    public static String RandomK = "";
    public static String HashPServer = "";
    public static String HashKClient = "";
    public static String MasterKeyEncryptedServer;
    public static byte[] MasterKeyServer;
}
