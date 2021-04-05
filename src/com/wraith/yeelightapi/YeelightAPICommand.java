package com.wraith.yeelightapi;

import com.google.gson.Gson;

public class YeelightAPICommand {

    private static int CommandID = 1;

    private int id;
    private String method;
    private Object[] params;

    public YeelightAPICommand(String Command, Object... Params)
    {
        this.id         = YeelightAPICommand.CommandID++;
        this.method     = Command;
        this.params     = Params;
    }

    public String getCommandString()
    {
        Gson gson = new Gson();
        String json = gson.toJson(this);

        return json + "\r\n";
    }

    public int getCommandID()
    {
        return this.id;
    }
}
