package com.think.guoyh.base;

import android.util.Log;


import com.base.gyh.baselib.utils.text.SecurityUtils;

import java.util.HashMap;

public class BaseModule {


    public BaseModule() {

    }

    public HashMap<String, String> getHeard() {
//       if (builder==null||builder.size()<=0){
        String authKey = "Android";
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        HashMap<String, String> builder = new HashMap<>();
        builder.put("Content-Type", "application/json");
        builder.put("UID", "16568");
        builder.put("AUTHKEY", authKey);
        builder.put("TIMESTAMP", timeStamp);
        builder.put("SIGN", SecurityUtils.getInstance().MD5Decode(authKey + timeStamp).toUpperCase());

//        Log.d("guoyh2",s1);
        Log.d("guoyh2",timeStamp);
        Log.d("guoyh2",authKey);


//       }
        return builder;
    }
}
