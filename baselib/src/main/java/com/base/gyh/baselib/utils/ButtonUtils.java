package com.base.gyh.baselib.utils;

import java.util.Locale;

public class ButtonUtils {
    private static long lastClickTime = 0;
    private static long DIFF = 500;
    private static int lastButtonId = -1;
    private static String lastTag="-1";

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }
    public static boolean isFastDoubleClick(long time) {
        return isFastDoubleClick(-1, time);
    }

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }


    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
    public static boolean isFastDoubleClick(String tag, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastTag.equals(tag) && lastClickTime > 0 && timeD < diff) {
            return true;
        }
        lastClickTime = time;
        lastTag = tag;
        return false;
    }

    /**
     * 为true 继续执行 false重复点击
     * @return
     */
    public static boolean onClick(){
        return  !isFastDoubleClick();
    }
    // 1
    public static boolean onClick(int buttonId){
        return  !isFastDoubleClick(buttonId,500);
    }


    private static String getSimpleStackTrace(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[5];
        return  String.format(Locale.getDefault(),"%s:%d#%s()",targetElement.getFileName(),targetElement.getLineNumber(),targetElement.getMethodName());

    }
}
