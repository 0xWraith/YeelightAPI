package com.wraith.yeelightapi;

import java.awt.*;

public class YeelightUtils
{
    static int YeelightRGBToHex(int YeelightR, int YeelightG, int YeelightB) { return ((YeelightR * 65536) + (YeelightG*256) + YeelightB); }
}
