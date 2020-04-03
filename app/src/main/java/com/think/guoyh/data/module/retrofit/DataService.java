package com.think.guoyh.data.module.retrofit;

import com.base.gyh.baselib.data.remote.retrofit.BaseDataService;

/**
 * Created by GUOYH on 2019/5/24.
 */
public class DataService extends BaseDataService {
    private static final String BASE_BOOK_URL = "http://novel.duoduvip.com/";

    public static BookService getBookService() {
        return (BookService) getService(BookService.class, BASE_BOOK_URL);
    }


}
