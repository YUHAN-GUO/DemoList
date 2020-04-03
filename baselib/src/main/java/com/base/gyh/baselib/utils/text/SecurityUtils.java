package com.base.gyh.baselib.utils.text;


/**
 * Created by alex on 16/7/2.
 */
public class SecurityUtils {
    private static final String securityKey = "2E8PObAj8HI45Qu9";
    private MD5Utils md5Utils;

    private static SecurityUtils instance = null;

    private SecurityUtils() {
        md5Utils = MD5Utils.getInstance();
    }


    public static synchronized SecurityUtils getInstance() {
        if (instance == null) {
            instance = new SecurityUtils();
        }
        return instance;
    }

    public String MD5Decode(String info) {
        String s1 = md5Utils.getMD5Result(info + securityKey);
//        if (isEmpty(s1)) {
//            return null;
//        }
//        s1 = md5Utils.getMD5Result(s1 + securityKey);
        return s1;
    }

    private boolean isEmpty(String info) {
        if (info == null || info.isEmpty()) {
            return true;
        }
        return false;
    }
}
