package com.think.guoyh.data;

import com.think.guoyh.utils.text.FileUtils;

import java.io.File;

public interface Const {

    interface GuoDemo {
        int ButtonDemo = 0;
        int MoveLayout = 1;
        int CoordDemo = 2;
        int IndexDemo = 3;
//        int AliDemo = 4;
        int ProcessDemo = 5;
        int SaveImgDemo = 6;
        int SendNotiDemo = 7;
        int RiliSignDemo = 8;
        int ShadowDemo = 9;
        int MMKVDemo = 10;
        int TextReadDemo = 11;
        int EncryDemo = 12;
        int TikTokDemo = 13;
        int SecondTaoDemo = 14;
        int RlvScrollDemo = 15;
    }

    interface SuanDemo {
        int YIWEIDEMO = 0;
    }

    String FORMAT_TIME = "HH:mm";
    String FORMAT_BOOK_DATE = "yyyy-MM-dd HH:mm:ss";
    String BOOK_CACHE_PATH = (FileUtils.getCachePath() + File.separator + "book_cache" + File.separator);
    String BookSort = "BookSort";

}
