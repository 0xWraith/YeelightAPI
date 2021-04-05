package com.wraith.yeelightapi;

import com.wraith.yeelightapi.YeelightAPISocket;
import com.wraith.yeelightapi.YeelightAPISocket;

public class YeelightBulb
{
    private final YeelightAPISocket socket;

    private YeelightAPIEffect YeelightEffect;

    private boolean YeelightPowerState;
    private int YeelightBrightnessLevel;

    {
        this.YeelightEffect         = new YeelightAPIEffect();
    }

    public YeelightBulb(String IP, int Port)
    {
        this.socket = new YeelightAPISocket(IP, Port);
        this.setPower(true);
    }

    public YeelightBulb(String IP)
    {
        this.socket = new YeelightAPISocket(IP, 55443);
        this.setPower(true);
    }

    public boolean setPower(boolean YeelightPowerState)
    {
        if(this.YeelightPowerState == YeelightPowerState)
            return false;

        this.YeelightPowerState = YeelightPowerState;

        YeelightAPICommand command = new YeelightAPICommand("set_power", YeelightPowerState == false ? "off" : "on", this.YeelightEffect.getYeelightEffect(), this.YeelightEffect.getYeelightEffectDuration());
        this.YeelightSendCommand(command);

        return true;
    }

    public boolean getPowerState() { return this.YeelightPowerState; }

    /**/

    public boolean setBrightness(int YeelightBrightnessLevel)
    {
        if(YeelightBrightnessLevel < 1 || YeelightBrightnessLevel > 100)
            return false;

        this.YeelightBrightnessLevel = YeelightBrightnessLevel;

        if(this.YeelightPowerState == false)
            this.setPower(true);

        YeelightAPICommand command = new YeelightAPICommand("set_bright", YeelightBrightnessLevel, this.YeelightEffect.getYeelightEffect(), this.YeelightEffect.getYeelightEffectDuration());
        this.YeelightSendCommand(command);

        return true;
    }
    public int getBrightnessLevel() { return this.YeelightBrightnessLevel; }

    /**/
    public void saveBulbSettings()
    {
        YeelightAPICommand command = new YeelightAPICommand("set_default");
        this.YeelightSendCommand(command);
    }
    /**/
    public boolean renameBulb(String YeelightName)
    {
        if(YeelightName.length() > 64)
            return false;

        YeelightAPICommand command = new YeelightAPICommand("set_name", YeelightName);
        this.YeelightSendCommand(command);

        return true;
    }
    /**/
    public boolean setColorTemperature(int YeelightTemperature)
    {
        if(YeelightTemperature < 1700 || YeelightTemperature > 6500)
            return false;

        YeelightAPICommand command = new YeelightAPICommand("set_ct_abx", YeelightTemperature, this.YeelightEffect.getYeelightEffect(), this.YeelightEffect.getYeelightEffectDuration());
        this.YeelightSendCommand(command);

        return true;
    }

    /**/
    public void setRGBColor(int YeelightR, int YeelightG, int YeelightB)
    {

        int YeelightColor = YeelightUtils.YeelightRGBToHex(YeelightR, YeelightG, YeelightB);

        YeelightAPICommand command = new YeelightAPICommand("set_rgb", YeelightColor, this.YeelightEffect.getYeelightEffect(), this.YeelightEffect.getYeelightEffectDuration());
        this.YeelightSendCommand(command);

    }

    private void YeelightSendCommand(YeelightAPICommand YeelightCommand)
    {
        this.socket.YeelightSocketSend(YeelightCommand.getCommandString());
    }
}
