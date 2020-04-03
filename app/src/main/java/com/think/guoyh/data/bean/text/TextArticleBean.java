package com.think.guoyh.data.bean.text;

import java.util.List;

//目录
public class TextArticleBean {

    /**
     * code : 1
     * msg : success
     * time : 1577687929
     * data : {"article":[{"id":2603,"volume":"第一卷 上","title":"彩图","words":0,"create_time":1572219632},{"id":2606,"volume":"第一卷 上","title":"人物介绍","words":0,"create_time":1572219632},{"id":2610,"volume":"第一卷 上","title":"用语解说","words":0,"create_time":1572219632},{"id":2616,"volume":"第一卷 上","title":"历史","words":0,"create_time":1572219632},{"id":2621,"volume":"第一卷 上","title":"校则法","words":0,"create_time":1572219632},{"id":2638,"volume":"第一卷 上","title":"序章 『境界线前的整列者们』","words":0,"create_time":1572219632},{"id":2640,"volume":"第一卷 上","title":"历史","words":0,"create_time":1572219633},{"id":2645,"volume":"第一卷 上","title":"第一章 『店前的邂逅者』","words":0,"create_time":1572219633},{"id":2647,"volume":"第一卷 上","title":"武蔵","words":0,"create_time":1572219633},{"id":2654,"volume":"第一卷 上","title":"第二章 『教室的破坏者们』","words":0,"create_time":1572219633},{"id":2657,"volume":"第一卷 上","title":"武藏的行政分布","words":0,"create_time":1572219633},{"id":2663,"volume":"第一卷 上","title":"第三章 『餐厅的清纯者』","words":0,"create_time":1572219634},{"id":2669,"volume":"第一卷 上","title":"第四章 『部外的王』","words":0,"create_time":1572219634},{"id":2677,"volume":"第一卷 上","title":"第五章 『晴天下的再会者』","words":0,"create_time":1572219634},{"id":2684,"volume":"第一卷 上","title":"第六章 『门对面的命运者』","words":0,"create_time":1572219634},{"id":2690,"volume":"第一卷 上","title":"三河周边概要图","words":0,"create_time":1572219634},{"id":2698,"volume":"第一卷 上","title":"第七章 『阶梯上的哲学者们』","words":0,"create_time":1572219634},{"id":2708,"volume":"第一卷 上","title":"第八章 『谷底的疑问者』","words":0,"create_time":1572219634},{"id":2712,"volume":"第一卷 上","title":"第九章 『门对面的等候者』","words":0,"create_time":1572219635},{"id":2716,"volume":"第一卷 上","title":"第十章 『镇中的游击手』","words":0,"create_time":1572219635}]}
     */

    private int code;
    private String msg;
    private String time;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ArticleBean> article;

        public List<ArticleBean> getArticle() {
            return article;
        }

        public void setArticle(List<ArticleBean> article) {
            this.article = article;
        }

        public static class ArticleBean {
            /**
             * id : 2603
             * volume : 第一卷 上
             * title : 彩图
             * words : 0
             * create_time : 1572219632
             */

            private int id;
            private String volume;
            private String title;
            private int words;
            private int create_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getVolume() {
                return volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getWords() {
                return words;
            }

            public void setWords(int words) {
                this.words = words;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }
        }
    }
}
