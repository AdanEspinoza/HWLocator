package com.helloworld.hwlocator.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.helloworld.hwlocator.model.LocationObject;

/**
 * Created by snakelogan on 2/7/16.
 */
public class DeviceUtils {

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    /**
     * @param context
     * @return the screen width in pixels
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /**
     * Return true if string is not null or empty
     *
     * @param str
     * @return
     */
    public static boolean checkStringNullOrEmpty(String str) {
        if (str == null || str.isEmpty()) return false;
        else return true;
    }

    /**
     * Return true if str has info and not cero
     *
     * @param str
     * @return
     */
    public static boolean checkStringCero(String str) {
        if (str == null || str.length() <= 1 || str.contentEquals("0") || str.isEmpty())
            return false;
        else return true;
    }

    /**
     * Validate a numeric string
     *
     * @param str
     * @return true if it is number
     */
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * @param str
     * @return integer mayor a cero sino -1 (error)
     */
    public static int getIntFromString(String str) {
        int number = -1;
        try {
            number = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return number;
        }
        return number;
    }

    /**
     * positive numbers only
     *
     * @param str
     * @return
     */
    public static double getDoubleFromString(String str) {
        double number = 0;
        try {
            number = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return number;
        }
        return number;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static String getFullAddress(LocationObject locationObject){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locationObject.getAddress())
                .append(", ")
                .append(locationObject.getAddress2())
                .append(", ")
                .append(locationObject.getCity())
                .append(", ")
                .append(locationObject.getState())
                .append(", ")
                .append(locationObject.getZipPostalCode());
        return stringBuilder.toString();
    }

    public static String validateUrl(String url){
        String newUrl = url;
        if(url.contains("https")){
            //NOP
        }else if(url.contains("http")){
            newUrl = url.substring(0,4)+"s"+url.substring(4,url.length());
        }
        return newUrl;
    }
}