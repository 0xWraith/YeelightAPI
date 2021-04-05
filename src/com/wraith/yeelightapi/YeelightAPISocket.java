package com.wraith.yeelightapi;

import java.net.Socket;
import java.net.InetSocketAddress;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class YeelightAPISocket
{
    private String IP;
    private int Port;

    private Socket socket;
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;

    public YeelightAPISocket(String IP, int Port)
    {
        try
        {
            this.IP = IP;
            this.Port = Port;

            InetSocketAddress iSA = new InetSocketAddress(IP, Port);

            this.socket = new Socket();
            this.socket.connect(iSA, 500);

            this.socketReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.socketWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        }
        catch (Exception exception)
        {
            System.out.println(exception);
        }
    }
    public void YeelightSocketSend(String datas)
    {
        try
        {
            this.socketWriter.write(datas);
            this.socketWriter.flush();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public String YeelightSocketRead()
    {
        try
        {
            String datas = this.socketReader.readLine();
            return datas;
        }
        catch (Exception exception)
        {
            System.out.println(exception);
        }
        return null;
    }

    public static void DiscoverDevices()
    {

    }
}
