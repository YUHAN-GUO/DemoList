package com.think.guoyh.data.module.retrofit;

import com.think.guoyh.data.bean.text.TextContentBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BookService {
    //获取书籍章节详情
    @GET()
    Observable<TextContentBean> getBookArticleDetail(@Url String id);
}
