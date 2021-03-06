package com.kun.allgo.SocketClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.kun.allgo.Utils.RandomHelper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Duy on 07-Jul-16.
 */
public class SocketClientWindowLogin extends AsyncTask<Void, Void, Void> {
    String dstAddress;
    int dstPort;
    String response = "";

    String passSend = "";
    String preShareKey ="";
    String passwordLoginWindows = "";

    ProgressDialog progressDialog;

    Socket socket;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] buffer = new byte[1024];
    InputStream inputStream;
    OutputStream outputStream;
    PrintStream printStream;
    BufferedReader bufferedReader;
    long[] longArray = new long[] { 65, 65, 65 };
    byte[] longBytesArray;

    //Key RSA Mobile
    int keyPMobile;
    int keyQMobile;
    String publicKeyMobile;
    String encryptedPublicKeyMobile;

    //Key RSA PC
    String publicKeyPC="";
    String encryptedPublicKeyPC; // nhận từ PC
    long keyN_PC;
    long keyE_PC;
    Context context;

    RSACryptoSystem rsaMobile;

    public SocketClientWindowLogin(String addr, int port, String passwordLoginWindows, Context context, ProgressDialog p) {
        dstAddress = addr;
        dstPort = port;
        this.context = context;
        this.passwordLoginWindows = passwordLoginWindows;
        this.progressDialog = p;
    }

    @Override
    protected void onCancelled() {

        this.progressDialog.dismiss();
        Toast.makeText(this.context, "Connection Refuse", Toast.LENGTH_SHORT).show();
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        super.onCancelled();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        /**
         * Random PreShare Key
         */
        RandomHelper randomHelper = new RandomHelper(8);
        preShareKey = randomHelper.nextString();

        //Gửi PreShare Key
        Log.d("reShareKey", preShareKey);

        /**
         * Generate Key RSA Mobile
         */
        keyPMobile = RandomPrimeHelper.RandomPrime();
        keyQMobile = RandomPrimeHelper.RandomPrime();
        while (keyPMobile == keyQMobile) {
            keyQMobile = RandomPrimeHelper.RandomPrime();
        }

        rsaMobile = new RSACryptoSystem();
        rsaMobile.setP(keyPMobile);
        rsaMobile.setQ(keyQMobile);
        rsaMobile.genkey();

        Log.d("keyPMobile", String.valueOf(keyPMobile));
        Log.d("keyQMobile", String.valueOf(keyQMobile));
        /**
         * Encrypt Public Key Mobile use PreShare key
         */
        publicKeyMobile = String.valueOf(rsaMobile.getN()) +"|" + String.valueOf(rsaMobile.getE()[0]);
        encryptedPublicKeyMobile = XorEncryptHelper.XorEncrypt(publicKeyMobile, preShareKey);

        Log.d("keyNMobile", String.valueOf(rsaMobile.getN()));
        Log.d("keyEMobile", String.valueOf(rsaMobile.getE()[0]));
        Log.d("encryptedPKM", encryptedPublicKeyMobile);
        //Gửi encryptedPublicKeyMobile

        try {
            socket = new Socket(dstAddress, dstPort);
            //socket.setSoTimeout(5000);
            /////
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            for (long l : longArray) {
                dos.writeLong(l);
            }
            dos.close();
            longBytesArray = baos.toByteArray();
            Log.d("long: ", Integer.toString(longBytesArray.length));
            /////

            if(socket != null){
                printStream = new PrintStream(socket.getOutputStream());
                byteArrayOutputStream = new ByteArrayOutputStream(1024);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
            cancel(true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
            cancel(true);
        }
//        finally {
//            if (socket != null) {
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        Receiver receiver = new Receiver(); // Initialize chat receiver AsyncTask.
        receiver.execute();

        final Sender messageSender = new Sender(); // Initialize chat sender AsyncTask.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            messageSender.messageSend = preShareKey;
            messageSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            messageSender.execute();
        }


        super.onPostExecute(result);
    }


    //////
    private class Receiver extends AsyncTask<Void, Void, Void> {

        private String message;

        @Override
        protected Void doInBackground(Void... params) {
            while (true) {
                try {
                    message = "";
                    int bytesRead;
                     /*
                    * notice: inputStream.read() will block if no data return
                    */
                    //int av = inputStream.available();
                    //String s = Integer.toString(av);

                    if ((bytesRead = inputStream.read(buffer)) != -1) {
                        message = "";
                        Log.d("byte read", Integer.toString(bytesRead));
                        ByteArrayOutputStream b = new ByteArrayOutputStream(1024);
                        b.write(buffer, 0, bytesRead);
                        message += b.toString("UTF-8");
                        Log.d("kiem tra receiver:", message);
                        publishProgress(null);

                    }

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }

                if (message.equals(passSend)) {
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //textView.append("Server: " + message + "\n");

            if(message.equals(preShareKey)) {
                progressDialog.setTitle("Transfer data...");
                final Sender messageSender = new Sender(); // Initialize chat sender AsyncTask.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    messageSender.messageSend = publicKeyMobile;
                    messageSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    messageSender.execute();
                }
            } else {
                if (message.equals(passSend)) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Transfer data completed", Toast.LENGTH_SHORT).show();
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    /**
                     * Giải mã Public Key của PC bằng Private Key Mobile
                     */
                    //
                    encryptedPublicKeyPC = message;
                    long[] cipherTextPublicKeyPC = new long[100];
                    int demPublicKeyPC = 0;
                    int tPublicKeyPC = 0;
                    Log.d("encryptedPublicKeyPC", encryptedPublicKeyPC);
                    while (demPublicKeyPC < encryptedPublicKeyPC.length())
                    {
                        String temp = "";
                        for (int j = 0; j < 4; j++)
                        {
                            temp += encryptedPublicKeyPC.charAt(demPublicKeyPC + j);
                        }
                        Log.d("encryptedTemp", temp);
                        if(temp.contains("-")){
                            String[] tPP = temp.split("-");
                            temp = "-"+tPP[1];
                        }
                        cipherTextPublicKeyPC[tPublicKeyPC] = Long.parseLong(temp);
                        Log.d("encryptedPublicKeyPC", String.valueOf(cipherTextPublicKeyPC[tPublicKeyPC]));
                        tPublicKeyPC++;
                        demPublicKeyPC += 4;
                    }

                    int size = tPublicKeyPC;
                    rsaMobile.setSize(size);
                    Log.d("size", String.valueOf(size));
                    cipherTextPublicKeyPC[tPublicKeyPC] = -1;
                    rsaMobile.setEn(cipherTextPublicKeyPC);
                    rsaMobile.decrypt();
                    long[] plaintextPublicKeyPC = new long[100];
                    plaintextPublicKeyPC = rsaMobile.getM();
                    for (int i = 0; i < size; i++)
                    {
                        Log.d("plaintextPublicKeyPC", String.valueOf(plaintextPublicKeyPC[i]));
                        publicKeyPC += (char)plaintextPublicKeyPC[i];
                    }
                    Log.d("publicKeyPC", publicKeyPC);
                    //Giải mã xong chuỗi Public Key của PC
                    String[] KeyPC = publicKeyPC.split("[|]");
                    keyN_PC = Long.parseLong(KeyPC[0]);
                    keyE_PC = Long.parseLong(KeyPC[1]);

                    Log.d("keyN_PC", String.valueOf(keyN_PC));
                    Log.d("keyE_PC", String.valueOf(keyE_PC));

                    /**
                     * Khởi tạo lại RSA mã hóa từ Mobile bằng Public Key PC
                     */

                    RSACryptoSystem rsaPC = new RSACryptoSystem();
                    rsaPC.setN(keyN_PC);
                    long[] E_PC = new long[100];
                    E_PC[0] = keyE_PC;
                    rsaPC.setE(E_PC);
                    char[] passwordLogin = new char[100];
                    rsaPC.setSize(passwordLoginWindows.length());
                    passwordLogin = passwordLoginWindows.toCharArray();
                    rsaPC.setMsg(passwordLogin);
                    rsaPC.encrypt();
                    long[] ciphertextPasswordLogin = new long[100];
                    ciphertextPasswordLogin = rsaPC.getEn();

                    for (int j = 0; ciphertextPasswordLogin[j] != -1; j++)
                    {
                        String tam = String.valueOf(ciphertextPasswordLogin[j]);
                        Log.i("=====MAHOA",String.valueOf(ciphertextPasswordLogin[j]));
                        int iCount = tam.length();
                        if (iCount < 4)
                        {
                            for (int k = 0; k < 4 - iCount; k++)
                            {
                                tam = "0" + tam;
                            }
                        }
                        passSend += tam;
                    }
                    Log.i("=====MAHOA passSend", passSend);

                    final Sender messageSender = new Sender(); // Initialize chat sender AsyncTask.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        messageSender.messageSend = passSend;
                        messageSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        messageSender.execute();
                    }
                }
            }
        }

    }

    private class Sender extends AsyncTask<Void, Void, Void> {

        String messageSend;

        @Override
        protected Void doInBackground(Void... params) {

//            messageSend = "hello world!";
            printStream.print(messageSend);
            printStream.flush();
//            try {
//                outputStream.write(longBytesArray, 0, longBytesArray.length);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }

}
