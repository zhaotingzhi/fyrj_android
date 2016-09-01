package com.tianhedaoyun.lgmr.math;

public class GeoMath
{
    public static double checkStrToDouble(final String s) {
        try {
            return Double.parseDouble(s);
        }
        catch (NumberFormatException ex) {
            return 0.0;
        }
    }
    
    public static int checkStrToInt(final String s) {
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            return -1;
        }
    }
}
