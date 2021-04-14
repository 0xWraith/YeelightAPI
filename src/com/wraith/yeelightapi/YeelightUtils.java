package com.wraith.yeelightapi;

public class YeelightUtils
{
    static final String YeelightActDecrease = "decrease";
    static final String YeelightActIncrease = "increase";
    static final String YeelightActCircle   = "circle";

    static final String YeelightPropBright  = "bright";
    static final String YeelightPropCT      = "ct";
    static final String YeelightPropColor   = "color";

    static int YeelightRGBToHex(int YeelightR, int YeelightG, int YeelightB) { return ((YeelightR * 65536) + (YeelightG*256) + YeelightB); }

    static int[] YeelightHSVToRGB(int YeelightH, int YeelightS, int YeelightV)
    {
        int
                YeelightR,
                YeelightG,
                YeelightB,

                YeelightI,
                YeelightF,
                YeelightP,
                YeelightQ,
                YeelightT;

        int[] YeelightRGB = new int[3];

        YeelightH = Math.max(0, Math.min(360, YeelightH));
        YeelightS = Math.max(0, Math.min(100, YeelightS));
        YeelightV = Math.max(0, Math.min(100, YeelightV));

        YeelightS /= 100;
        YeelightV /= 100;

        if(YeelightS == 0)
        {
            YeelightR =
                    YeelightG =
                            YeelightB =
                                    YeelightV;

            YeelightRGB[0] = Math.round(YeelightR * 255);
            YeelightRGB[1] = Math.round(YeelightG * 255);
            YeelightRGB[2] = Math.round(YeelightB * 255);

            return YeelightRGB;
        }

        YeelightH /= 60;
        YeelightI = (int)Math.floor(YeelightH);
        YeelightF = YeelightH - YeelightI;
        YeelightP = YeelightV * (1 - YeelightS);
        YeelightQ = YeelightV * (1 - YeelightS * YeelightF);
        YeelightT = YeelightV * (1 - YeelightS * (1 - YeelightF));

        switch(YeelightI)
        {
            case 0: {

                YeelightR = YeelightV;
                YeelightG = YeelightT;
                YeelightB = YeelightP;

                break;
            }
            case 1: {

                YeelightR = YeelightQ;
                YeelightG = YeelightV;
                YeelightB = YeelightP;

                break;
            }
            case 2: {

                YeelightR = YeelightP;
                YeelightG = YeelightV;
                YeelightB = YeelightT;

                break;
            }
            case 3: {

                YeelightR = YeelightP;
                YeelightG = YeelightQ;
                YeelightB = YeelightV;

                break;
            }
            case 4: {

                YeelightR = YeelightT;
                YeelightG = YeelightP;
                YeelightB = YeelightV;

                break;
            }
            default: {

                YeelightR = YeelightV;
                YeelightG = YeelightP;
                YeelightB = YeelightQ;

                break;
            }
        }

        YeelightRGB[0] = Math.round(YeelightR * 255);
        YeelightRGB[1] = Math.round(YeelightG * 255);
        YeelightRGB[2] = Math.round(YeelightB * 255);

        return YeelightRGB;
    }
}
