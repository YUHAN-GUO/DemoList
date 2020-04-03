package com.base.gyh.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.base.gyh.baselib.base.BaseApplication;
import com.base.gyh.baselib.utils.mylog.Logger;

import butterknife.internal.Utils;

public class BaseSP {
    private static final String USER_NAME = "userName"; //用户名
    private static final String USER_TOKEN = "userToken"; //用户名
    private static final String USER_IMG = "userImg"; //用户头像
    private static final String LOAD_KEY = "loadKey"; //加载所需要的参数
    private static final String TOTAL = "total"; //加载所需要的参数



    private static BaseSP preference = null;
    private static SharedPreferences sharedPreference;
    private String packageName = "";
    private final Context context;

    public static synchronized BaseSP getInstance(){
        if(preference == null){
            preference = new BaseSP();
        }
        return preference;
    }
    public BaseSP(){
        context = BaseApplication.getContext();
        packageName = context.getPackageName() + "_preferences";
        sharedPreference = context.getSharedPreferences(
                packageName, Context.MODE_PRIVATE);
    }

    public String getUserImg(){
        Logger.d("%s++++++++++%s","guoyh",getValue(USER_IMG));
        return sharedPreference.getString(USER_IMG,"");

//        return getValue(USER_IMG);
    }
    public void setUserImg(String userImg){
        Logger.d("%s++++++++++%s","guoyh",userImg);

        commit(USER_IMG,userImg);
    }


    public String getUserName(){
        return getValue(USER_NAME);
    }
    public void setUserName(String userName){
        commit(USER_NAME,userName);
    }

    public String getLoadKey(){
        return getValue(LOAD_KEY);
    }
    public void setLoadKey(String loadKey){
        commit(LOAD_KEY,loadKey);
    }


    public String getUserToken(){
        return getValue(USER_TOKEN);
    }
    public void setUserToken(String userToken){
        commit(USER_TOKEN,userToken);
    }

    public void setTotal(long toal){
        put(TOTAL,toal);
    }
    public long getToal(long def){
        return (long) get(TOTAL,def);
    }

    private String getValue(String key){
        return sharedPreference.getString(key, "");
    }
    private void commit(String key,String value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        boolean commit = editor.commit();
        Logger.e("guoyh+++++++++++%s",commit);
    }
    private void apply(String key,String value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }
    private void commit(String key,Long value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putLong(key, value);
        boolean commit = editor.commit();
        Logger.e("guoyh+++++++++++%s",commit);
    }
    private void apply(String key,Long value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }
    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreference.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreference.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreference.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreference.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreference.getLong(key, (Long) defaultObject);
        }

        return null;
    }

}
