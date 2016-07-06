package com.kun.allgo.SocketClient;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

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
 * Created by Duy on 05-Jun-16.
 */
public class SocketClient extends AsyncTask<Void, Void, Void> {
    String dstAddress;
    int dstPort;
    String response = "";

    String nKeySend = "";
    String dKeySend = "";
    String passSend = "";

    Socket socket;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] buffer = new byte[1024];
    InputStream inputStream;
    OutputStream outputStream;
    PrintStream printStream;
    BufferedReader bufferedReader;
    long[] longArray = new long[]{65, 65, 65};
    byte[] longBytesArray;

    public SocketClient(String addr, int port) {
        dstAddress = addr;
        dstPort = port;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        /**
         * sử dụng hàm mã hóa
         */
        RSACryptoSystem rsa = new RSACryptoSystem();
        //se bo sung code random sau***
        rsa.setSize(6);
        rsa.setQ(13);
        rsa.setP(29);
        char[] text = new char[100];
        text[0] = '1';
        text[1] = '2';
        text[2] = '3';
        text[3] = '4';
        text[4] = '5';
        text[5] = '6';
        rsa.setMsg(text);
        rsa.genkey();
        rsa.encrypt();

        long[] ciphertext = new long[100];
        ciphertext = rsa.getEn();

        nKeySend = String.valueOf(rsa.getN());
        dKeySend = String.valueOf(rsa.getD()[0]);

        for (int j = 0; ciphertext[j] != -1; j++) {
            String tam = String.valueOf(ciphertext[j]);
            Log.i("=====MAHOA", String.valueOf(ciphertext[j]));
            if (tam.length() < 4) {
                for (int k = 0; k <= 4 - tam.length(); k++) {
                    tam = "0" + tam;
                }
            }
            passSend += tam;
        }
        Log.i("=====MAHOA", passSend);

        ///////

        try {
            socket = new Socket(dstAddress, dstPort);
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

            if (socket != null) {
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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
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
        //textResponse.setText(response);

        final Sender messageSender = new Sender(); // Initialize chat sender AsyncTask.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            messageSender.messageSend = dKeySend;
            //textResponse.append("bat dau gui mess \n");
            messageSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            messageSender.execute();
        }

        Receiver receiver = new Receiver(); // Initialize chat receiver AsyncTask.
        receiver.execute();
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
            //textResponse.append("Server: " + message + "\n");

            if (message.equals(dKeySend)) {
                final Sender messageSender = new Sender(); // Initialize chat sender AsyncTask.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    messageSender.messageSend = nKeySend;
                    messageSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    messageSender.execute();
                }
            } else {
                if (message.equals(nKeySend)) {
                    final Sender messageSender = new Sender(); // Initialize chat sender AsyncTask.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        messageSender.messageSend = passSend;
                        messageSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        messageSender.execute();
                    }
                }
                if (message.equals(passSend)) {
                    try {
                        socket.close();
                        //textResponse.append("close socket");
                    } catch (IOException e) {
                        e.printStackTrace();
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
            //textResponse.append("Client: " + messageSend + Integer.toString(messageSend.length()) + "\n");

        }
    }

}