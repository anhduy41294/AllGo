package com.kun.allgo.SocketClient;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Duy on 01-Jul-16.
 */
public class SocketClientAutoLoginController extends AsyncTask<Void, Void, Void> {
    String dstAddress;
    int dstPort;
    String response = "";
    String data = "";
    String usernameEncrypted, passwordEncrypted, appType, email;
    int code;

    public SocketClientAutoLoginController(String addr, int port, String username, String password, String appType, int code, String email) {
        dstAddress = addr;
        dstPort = port;
        usernameEncrypted = username;
        passwordEncrypted = password;
        this.appType = appType;
        this.code = code;
        this.email = email;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            //Send to Server
            OutputStream outputStream = socket.getOutputStream();

            PrintStream printStream = new PrintStream(outputStream);
            if (appType.equals("Outlook")){
                printStream.print("["+code+"]|" + appType + "|" + usernameEncrypted + "|" + email + "|" + passwordEncrypted + "<EOF>");
            } else {
                printStream.print("["+code+"]|" + appType + "|" + usernameEncrypted + "|" + passwordEncrypted + "<EOF>");
            }

            outputStream.flush();
            //outputStream.close();

            while (true) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                        1024);
                byte[] buffer = new byte[1024];

                int bytesRead;

                InputStream inputStream = socket.getInputStream();
                bytesRead = inputStream.read(buffer);
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                data += byteArrayOutputStream.toString("UTF-8");

                if (data.contains("<EOF>")) {
                    break;
                }
            }
            Log.i("Socket Android: ", data.replaceAll("<EOF>",""));

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.i("Socket Android", response);
        super.onPostExecute(result);
    }
}