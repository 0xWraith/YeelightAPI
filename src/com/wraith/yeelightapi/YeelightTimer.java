package com.wraith.yeelightapi;

import java.util.TimerTask;

public class YeelightTimer extends TimerTask {

    private final YeelightBulb YeelightDevice;
    private final int YeelightType;

    YeelightTimer(YeelightBulb YeelightDevice, int YeelightType)
    {
        this.YeelightDevice = YeelightDevice;
        this.YeelightType = YeelightType;
    }

    public void run() {
        switch(this.YeelightType) {
            case 0: {
                this.YeelightDevice.setPower(false);
                break;
            }
        }
    }
}
