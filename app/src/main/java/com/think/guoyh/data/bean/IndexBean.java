package com.think.guoyh.data.bean;

import me.yokeyword.indexablerv.IndexableEntity;

public class IndexBean implements IndexableEntity {
    private String city;

    public IndexBean(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getFieldIndexBy() {
        return city;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.city = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {

    }
}
