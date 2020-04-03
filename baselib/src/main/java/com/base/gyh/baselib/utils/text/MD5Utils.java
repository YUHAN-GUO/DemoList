package com.base.gyh.baselib.utils.text;

/**
 * Created by newbiechen on 2018/1/1.
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *@Description: 将字符串转化为MD5
 */

public class MD5Utils {
    private static MD5Utils instance = null;

    private MD5Utils() {
    }

    public static synchronized MD5Utils getInstance() {
        if (instance == null) {
            instance = new MD5Utils();
        }
        return instance;
    }

    public String getMD5Result(String info){
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(info.getBytes("UTF-8"));
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++){
                if (Integer.toHexString(0xFF & messageDigest[i]).length() == 1)
                    hexString.append("0").append(Integer.toHexString(0xFF & messageDigest[i]));
                else
                    hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }
    public static String strToMd5By32(String str){
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes){
                int bt = b&0xff;
                if (bt < 16){
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    public static String strToMd5By16(String str){
        String reStr = strToMd5By32(str);
        if (reStr != null){
            reStr = reStr.substring(8, 24);
        }
        return reStr;
    }
}
