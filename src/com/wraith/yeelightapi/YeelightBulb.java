package com.wraith.yeelightapi;

import java.util.Timer;
import java.util.TimerTask;

public class YeelightBulb
{
    private final YeelightAPISocket socket;
    private YeelightAPIEffect YeelightEffect;
    private Timer YeelightTimer;


    private boolean YeelightPowerState;
    private int YeelightBrightnessLevel;


    private int[] YeelightRGBColor = new int[3];

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

        return this.YeelightSendCommand(command);
    }

    public boolean getPowerState() { return this.YeelightPowerState; }

    /**/

    public boolean setBrightness(int YeelightBrightnessLevel)
    {
        if(YeelightBrightnessLevel < 1 || YeelightBrightnessLevel > 100)
            return false;

        this.YeelightBrightnessLevel = YeelightBrightnessLevel;

        if(!this.YeelightPowerState)
            this.setPower(true);

        YeelightAPICommand command = new YeelightAPICommand("set_bright", YeelightBrightnessLevel, this.YeelightEffect.getYeelightEffect(), this.YeelightEffect.getYeelightEffectDuration());

        return this.YeelightSendCommand(command);
    }
    public int getBrightnessLevel() { return this.YeelightBrightnessLevel; }

    /**/
    public boolean saveBulbSettings()
    {
        YeelightAPICommand command = new YeelightAPICommand("set_default");
        return this.YeelightSendCommand(command);
    }
    /**/
    public boolean renameBulb(String YeelightName)
    {
        if(YeelightName.length() > 64)
            return false;

        YeelightAPICommand command = new YeelightAPICommand("set_name", YeelightName);

        return this.YeelightSendCommand(command);
    }
    /**/
    public boolean setColorTemperature(int YeelightTemperature)
    {
        if(YeelightTemperature < 1700 || YeelightTemperature > 6500)
            return false;

        YeelightAPICommand command = new YeelightAPICommand("set_ct_abx", YeelightTemperature, this.YeelightEffect.getYeelightEffect(), this.YeelightEffect.getYeelightEffectDuration());

        return this.YeelightSendCommand(command);
    }

    /**/

    public boolean setRGBColor(int YeelightR, int YeelightG, int YeelightB)
    {
        if((YeelightR > 255 || YeelightG > 255 || YeelightB > 255) || (YeelightR < 0 || YeelightG < 0 || YeelightB < 0))
            return false;

        this.YeelightRGBColor[0] = YeelightR;
        this.YeelightRGBColor[1] = YeelightG;
        this.YeelightRGBColor[2] = YeelightB;


        int YeelightColor = YeelightUtils.YeelightRGBToHex(YeelightR, YeelightG, YeelightB);

        YeelightAPICommand command = new YeelightAPICommand("set_rgb", YeelightColor, this.YeelightEffect.getYeelightEffect(), this.YeelightEffect.getYeelightEffectDuration());

        return this.YeelightSendCommand(command);
    }

    /**
     * Get lamp RGB color
     * @return Array of RGB
     */
    public int[] getRGBColor() { return this.YeelightRGBColor; }

    /**/

    public boolean setHSVColor(int YeelightHUE, int YeelightSAT, int ...YeelightValue)
    {
        if((YeelightHUE < 0 || YeelightHUE > 359) || (YeelightSAT < 0 || YeelightSAT > 100)/* || (YeelightValue < 0 || YeelightValue > 100)*/)
            return false;

        int[] YeelightRGB = new int[3];

        //YeelightRGB = YeelightUtils.YeelightHSVToRGB(YeelightHUE, YeelightSAT, YeelightValue);
        //System.out.printf("%d, %d, %d", YeelightRGB[0], YeelightRGB[1], YeelightRGB[2]);

        YeelightAPICommand command = new YeelightAPICommand("set_hsv", YeelightHUE, YeelightSAT, this.YeelightEffect.getYeelightEffect(), this.YeelightEffect.getYeelightEffectDuration());

        return this.YeelightSendCommand(command);
    }

    /**/

    public boolean stopColorFlow()
    {
        YeelightAPICommand command = new YeelightAPICommand("stop_cf");
        return this.YeelightSendCommand(command);
    }

    /**/

    public boolean setAdjust(String YeelightAction, String YeelightProperty)
    {
        if(YeelightAction.length() == 0 || YeelightProperty.length() == 0)
            return false;

        if(YeelightProperty.equals("color") && !YeelightAction.equals("circle"))
            return false;

        YeelightAPICommand command = new YeelightAPICommand("set_adjust", YeelightAction, YeelightProperty);
        return this.YeelightSendCommand(command);
    }

    /**/

    public boolean setCron(int YeelightType, int YeelightTime)
    {
        if(YeelightType != 0 && this.YeelightPowerState == false)
            return false;

        Timer powerTimer = new Timer();
        this.YeelightTimer = powerTimer;

        powerTimer.schedule(new YeelightTimer(this, YeelightType), YeelightTime*1000);

        YeelightAPICommand command = new YeelightAPICommand("cron_add", YeelightType, YeelightTime);
        return this.YeelightSendCommand(command);
    }

    public boolean deleteCron(int YeelightType)
    {
        if(YeelightType != 0 && this.YeelightPowerState == false)
            return false;

        if(this.YeelightTimer != null)
        {
            this.YeelightTimer.cancel();
            this.YeelightTimer = null;
        }

        YeelightAPICommand command = new YeelightAPICommand("cron_del", YeelightType);
        return this.YeelightSendCommand(command);
    }

    /**/

    private boolean YeelightSendCommand(YeelightAPICommand YeelightCommand)
    {
        this.socket.YeelightSocketSend(YeelightCommand.getCommandString());
        return true;
    }
}
