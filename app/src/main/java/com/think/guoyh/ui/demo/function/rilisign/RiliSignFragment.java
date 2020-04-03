package com.think.guoyh.ui.demo.function.rilisign;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.ui.demo.function.rilisign.adapter.DateAdapter;
import com.think.guoyh.ui.demo.function.rilisign.adapter.WeekAdapter;
import com.think.guoyh.ui.demo.function.rilisign.model.SignDateModel;
import com.think.guoyh.ui.demo.function.rilisign.utils.DateUtil;
import com.think.guoyh.widget.ShapeButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RiliSignFragment extends MyBaseFragment implements View.OnClickListener {

    private TextView tvCurrentDate; //今天的日期

    private TextView signTv;
    private RecyclerView rvSignWeek;
    private RecyclerView rvSignDate;
    private ShapeButton signBt;
    private DateAdapter dateAdapter;
    private List<SignDateModel> models = new ArrayList<>();
    private int hao;//今天是几号

    public RiliSignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rili_sign, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        int mothonNumDate = getMothonNumDate();//这个月有多少天
        int mothonFirstDate = getMothonFirstDate();//这个月的第一天 是星期几
        int mothonLastDate = getMothonLastDate();//这个月的最后一天 是星期几
        Logger.d("%s+++++++++++%s","guoyh3",mothonFirstDate);
        Logger.d("%s++++++++++++%s","guoyh3",mothonLastDate);
        Logger.d("%s++++++++++++%s","guoyh3",mothonNumDate);
        Date lastDate1 = DateUtil.getDateForLastDay(null, 0);
        Date lastDate13 = DateUtil.getDateForLastDay(null, mothonFirstDate);
        Logger.d("%s+++++++++%s","guoyh3",sf.format(lastDate13));
        Logger.d("%s+++++++++%s","guoyh3",String.valueOf(lastDate13.getDate()));
//        Date lastDate17 = DateUtil.getDateForLastDay(null, mothonFirstDate+16);
//        Logger.d("%s+++++++++%s","guoyh3",sf.format(lastDate17));
//        Logger.d("%s+++++++++%s","guoyh3",String.valueOf(lastDate17.getDate()));

        for (int i = 0; i < mothonFirstDate-1; i++) {
            SignDateModel model = new SignDateModel();
            model.setContent("00");
            model.setDate("00");
            models.add(model);
        }

        String s = String.valueOf(lastDate1.getDate());
        hao = Integer.valueOf(s)-1;
        Logger.d("%s+++++++++++++%s","guoyh3",hao);
        Date lastDate11 = DateUtil.getDateForLastDay(null, hao);
        Logger.d("%s+++++++++++%s","guoyh3",String.valueOf(lastDate11.getDate())+"------------"+sf.format(lastDate11)+"---------"+hao);
        for (int i =  0; i <= hao; i++) {
            SignDateModel model = new SignDateModel();
            //初始化前半部分日历数据
            Date lastDate = DateUtil.getDateForLastDay(null, hao-i);
            model.setContent(String.valueOf(lastDate.getDate()));
            model.setDate(sf.format(lastDate));
            if (lastDate.getDay() == 0 || lastDate.getDay() == 6) {
                model.setStatus(SignDateModel.STATUS_SAT_OR_SUN);
            }
            if (i == hao) {
                model.setStatus(SignDateModel.STATUS_CURRENT_DAY);
            }
            models.add(model);
        }

//        int after = hao; //今天之后需要补齐多少天
        Logger.d("%s+++++++++++++++%s","guoyh3",mothonNumDate-(hao+1));
        int riLiBu = mothonNumDate-(hao+1);//完整的日历 需要补多少天
        int wuKongRili = riLiBu+(7-mothonLastDate);// 弥补后面的空白  一周7天 故为 7
        for (int i = 1; i <= riLiBu; i++) {
            SignDateModel model = new SignDateModel();
            //初始化后半部分日历数据
            Date nextDate = DateUtil.getDateForNextDay(null, i);
            model.setContent(String.valueOf(nextDate.getDate()));
            model.setDate(sf.format(nextDate));
            if (nextDate.getDay() == 0 || nextDate.getDay() == 6) {
                model.setStatus(SignDateModel.STATUS_SAT_OR_SUN);
            }
            models.add( model);
        }


//        models.clear();
//        current = 14 + DateUtil.getCurrentDayOfWeek(null) - 1; //今天在日历中的位置
//        Logger.d("%s+++++++++++++++%s","guoyh3",current);
//        for (int i = 0; i < current + 1; i++) {
//            SignDateModel model = new SignDateModel();
//            //初始化前半部分日历数据
//            Date lastDate = DateUtil.getDateForLastDay(null, current - i);
//            model.setContent(String.valueOf(lastDate.getDate()));
//            model.setDate(sf.format(lastDate));
//            if (lastDate.getDay() == 0 || lastDate.getDay() == 6) {
//                model.setStatus(SignDateModel.STATUS_SAT_OR_SUN);
//            }
//            if (current - i == 0) {
//                model.setStatus(SignDateModel.STATUS_CURRENT_DAY);
//            }
//            models.add(i, model);
//        }


//        int after = hao; //今天之后需要补齐多少天
//        for (int i = 1; i < after + 1; i++) {
//            SignDateModel model = new SignDateModel();
//            //初始化后半部分日历数据
//            Date nextDate = DateUtil.getDateForNextDay(null, i);
//            model.setContent(String.valueOf(nextDate.getDate()));
//            model.setDate(sf.format(nextDate));
//            if (nextDate.getDay() == 0 || nextDate.getDay() == 6) {
//                model.setStatus(SignDateModel.STATUS_SAT_OR_SUN);
//            }
//            models.add(hao + i, model);
//        }
        dateAdapter.setNewData(models);

    }
    private int getMothonFirstDate(){
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Logger.d("%s+++++++++%s","guoyh3",dateFormater.format(cal.getTime()));
        return getWeek(dateFormater.format(cal.getTime()));
    }
    private int getMothonLastDate(){
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getWeek(dateFormater.format(cal.getTime()));
    }
    private int getMothonNumDate(){
        Date lastDate = DateUtil.getDateForLastDay(null, 0);
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastDate);
        int n=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return n;
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = DateUtil.getDateForLastDay(null, 0);

        signTv = (TextView) view.findViewById(R.id.signTv);
        signTv.setText(sf.format(date));
        rvSignWeek = (RecyclerView) view.findViewById(R.id.rv_sign_week);
        rvSignDate = (RecyclerView) view.findViewById(R.id.rv_sign_date);
        signBt = (ShapeButton) view.findViewById(R.id.signBt);

        signBt.setOnClickListener(this);

        GridLayoutManager weekLayoutManager = new GridLayoutManager(getActivity(), 7);
        WeekAdapter weekAdapter = new WeekAdapter();
        rvSignWeek.setLayoutManager(weekLayoutManager);
        rvSignWeek.setAdapter(weekAdapter);

        GridLayoutManager dataLayoutManager = new GridLayoutManager(getActivity(), 7);
        dateAdapter = new DateAdapter(null);
        rvSignDate.setLayoutManager(dataLayoutManager);
        rvSignDate.setAdapter(dateAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signBt:
                models.get(hao).setStatus(SignDateModel.STATUS_HAVE_SIGN);
                dateAdapter.notifyDataSetChanged();
                break;
        }
    }

    private int getWeek(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.get(Calendar.DAY_OF_WEEK);
    }
}
