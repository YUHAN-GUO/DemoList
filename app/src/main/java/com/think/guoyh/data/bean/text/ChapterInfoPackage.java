package com.think.guoyh.data.bean.text;


import com.think.guoyh.data.module.db.ChapterInfoBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChapterInfoPackage implements Serializable {

    private List<ChapterInfoBean> article = new ArrayList<>();

    public List<ChapterInfoBean> getArticle() {
        return article;
    }

    public void setArticle(List<ChapterInfoBean> article) {
        this.article = article;
    }


}
