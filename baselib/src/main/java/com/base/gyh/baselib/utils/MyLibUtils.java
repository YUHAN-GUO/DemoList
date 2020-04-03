package com.base.gyh.baselib.utils;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.telecom.Call;
import android.widget.Toast;

import com.base.gyh.baselib.callback.BaseLibIcallBack;
import com.base.gyh.baselib.data.bean.city.City;
import com.base.gyh.baselib.data.bean.city.HotCity;
import com.base.gyh.baselib.data.bean.city.LocatedCity;
import com.base.gyh.baselib.widgets.dialog.CityPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUOYH on 2019/4/30.
 */
public class MyLibUtils {
    /**
     *
     * @param hotCities  热门城市
     * @param loadCity   所在城市
     * @param city        默认城市，所在城市为null，才会显示默认城市
     * @param activity   要展示的activity fragment就getActivity
     * @param fragmentManager  fragment事务管理器
     */
    public static void showCityDiaLogFragment(ArrayList<HotCity> hotCities, String loadCity, String city, final Activity activity, FragmentManager fragmentManager, final BaseLibIcallBack<String> baseLibIcallBack){

        if (loadCity != null&&loadCity.length()>0){
            CityPicker.getInstance().setLocatedCity(new LocatedCity(loadCity));
        }else if (city != null&&city.length()>0){
            CityPicker.getInstance().setLocatedCity(new LocatedCity(city));
        } else {
            CityPicker.getInstance().setLocatedCity(null);
        }
        if (!activity.isFinishing()) {
            CityPicker.getInstance()
                    .setFragmentManager(fragmentManager)
                    .enableAnimation(true)
                    .setHotCities(hotCities)
                    .setOnPickListener(new CityPicker.OnPickListener() {
                        @Override
                        public void onPick(int position, City data) {
                            //返回点击 show 之前的那个页面，可以给search text 设置值
                            if (data != null) {
                                baseLibIcallBack.onSuccess(data.getName());
                            }
                        }

                        @Override
                        public void onLocate() {
                            baseLibIcallBack.onFail("没有获取到");

                        }
                    })
                    .show();
        }
    }
}
