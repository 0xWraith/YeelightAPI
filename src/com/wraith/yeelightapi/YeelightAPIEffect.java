package com.wraith.yeelightapi;

import java.util.ArrayList;

public class YeelightAPIEffect
{

    private String YeelightEffect;
    private int YeelightDuration;

    public YeelightAPIEffect(String YeelightEffect, int YeelightDuration)
    {
        if(YeelightDuration < 0)
            YeelightDuration = 50;

        if(this.checkIFValidEffectName(YeelightEffect) == false)
            YeelightEffect = "sudden";

        this.YeelightEffect = YeelightEffect;
        this.YeelightDuration = YeelightDuration;
    }
    public YeelightAPIEffect()
    {
        this.YeelightEffect = "smooth";
        this.YeelightDuration = 500;
    }

    public boolean changeYeelightEffect(String YeelightEffect)
    {
        if(this.checkIFValidEffectName(YeelightEffect) == false)
            return false;

        this.YeelightEffect = YeelightEffect;

        return true;
    }

    public boolean changeYeelightEffectDuration(int YeelightDuration)
    {
        if(YeelightDuration < 50 || YeelightDuration > 10_000)
            return false;

        this.YeelightDuration = YeelightDuration;

        return true;
    }

    public String getYeelightEffect() { return this.YeelightEffect; }
    public int getYeelightEffectDuration() { return this.YeelightDuration; }

    private boolean checkIFValidEffectName(String YeelightEffect)
    {
        ArrayList<String> effects = new ArrayList<String>();

        effects.add("smooth");
        effects.add("sudden");

        for(String effect : effects)
        {
            if(YeelightEffect.contentEquals(effect))
                return true;
        }

        return false;
    }
}
