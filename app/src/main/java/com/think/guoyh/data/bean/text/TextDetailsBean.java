package com.think.guoyh.data.bean.text;

import com.think.guoyh.data.module.db.CollBookBean;

//首页详情
public class TextDetailsBean {

    /**
     * code : 1
     * msg : success
     * time : 1577685031
     * data : {"book":{"id":7,"title":"境界线上的地平线","words":2094000,"cover":"http://novel.duoduvip.com//uploads/20191028/b53b3feb92e8c1428a8fede7d16483d9.jpg","description":"各国分割统治的中世纪的神州\u2014\u2014日本。在那空中上方由八艘船组成的都市舰\u201c武藏\u201d航行着。  \n在遥远的未来。经过了\u201c重奏统合争乱\u201d，并把联系着人类的命运的\u201c圣谱\u201d作为原本历史的再现而执行的各国。然后，是各式各样把迷惑与决意藏于胸口，前进开拓着未来的人们。\n　　把互相重合的中世纪世界作为舞台，学生们在学园国家间的抗争正式上演了！\n　　描绘AHEAD系列\u201c终焉的年代记\u201d与都市系列之间的时代的，壮大的物语\u201cGENESIS\u201d系列，终于开始！","hot":51065,"like":20,"author":"川上稔","include_image":1,"create_time":1572193142},"last_article":{"id":87754,"volume":"第七卷 中","title":"第五十二章『山谷间的仰望者』","create_time":1575176441}}
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
        /**
         * book : {"id":7,"title":"境界线上的地平线","words":2094000,"cover":"http://novel.duoduvip.com//uploads/20191028/b53b3feb92e8c1428a8fede7d16483d9.jpg","description":"各国分割统治的中世纪的神州\u2014\u2014日本。在那空中上方由八艘船组成的都市舰\u201c武藏\u201d航行着。  \n在遥远的未来。经过了\u201c重奏统合争乱\u201d，并把联系着人类的命运的\u201c圣谱\u201d作为原本历史的再现而执行的各国。然后，是各式各样把迷惑与决意藏于胸口，前进开拓着未来的人们。\n　　把互相重合的中世纪世界作为舞台，学生们在学园国家间的抗争正式上演了！\n　　描绘AHEAD系列\u201c终焉的年代记\u201d与都市系列之间的时代的，壮大的物语\u201cGENESIS\u201d系列，终于开始！","hot":51065,"like":20,"author":"川上稔","include_image":1,"create_time":1572193142}
         * last_article : {"id":87754,"volume":"第七卷 中","title":"第五十二章『山谷间的仰望者』","create_time":1575176441}
         */

        private BookBean book;
        private LastArticleBean last_article;
        private CollBookBean collBookBean;

        public BookBean getBook() {
            return book;
        }

        public void setBook(BookBean book) {
            this.book = book;
        }

        public LastArticleBean getLast_article() {
            return last_article;
        }

        public void setLast_article(LastArticleBean last_article) {
            this.last_article = last_article;
        }

        public static class BookBean {
            /**
             * id : 7
             * title : 境界线上的地平线
             * words : 2094000
             * cover : http://novel.duoduvip.com//uploads/20191028/b53b3feb92e8c1428a8fede7d16483d9.jpg
             * description : 各国分割统治的中世纪的神州——日本。在那空中上方由八艘船组成的都市舰“武藏”航行着。
             * 在遥远的未来。经过了“重奏统合争乱”，并把联系着人类的命运的“圣谱”作为原本历史的再现而执行的各国。然后，是各式各样把迷惑与决意藏于胸口，前进开拓着未来的人们。
             * 　　把互相重合的中世纪世界作为舞台，学生们在学园国家间的抗争正式上演了！
             * 　　描绘AHEAD系列“终焉的年代记”与都市系列之间的时代的，壮大的物语“GENESIS”系列，终于开始！
             * hot : 51065
             * like : 20
             * author : 川上稔
             * include_image : 1
             * create_time : 1572193142
             */

            private int id;
            private String title;
            private int words;
            private String cover;
            private String description;
            private int hot;
            private int like;
            private String author;
            private int include_image;
            private int create_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }

            public int getLike() {
                return like;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getInclude_image() {
                return include_image;
            }

            public void setInclude_image(int include_image) {
                this.include_image = include_image;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }
        }

        public static class LastArticleBean {
            /**
             * id : 87754
             * volume : 第七卷 中
             * title : 第五十二章『山谷间的仰望者』
             * create_time : 1575176441
             */

            private int id;
            private String volume;
            private String title;
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

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }
        }

        public CollBookBean getCollBookBean() {
            if (collBookBean == null) {
                collBookBean = createCollBookBean();
            }
            return collBookBean;
        }

        public CollBookBean createCollBookBean() {
            CollBookBean bean = new CollBookBean();
            bean.setId(String.valueOf(getBook().getId()));
            bean.setTitle(getBook().getTitle());
            bean.setAuthor(getBook().getAuthor());
            bean.setShortIntro(getBook().getDescription());
            bean.setCover(getBook().getCover());
            bean.setInclude_image(getBook().getInclude_image());
//        bean.setHasCp(getBook().isHasCp());
//        bean.setLatelyFollower(getBook().getLatelyFollower());
//        bean.setRetentionRatio(Double.parseDouble(getBook().getRetentionRatio()));
            if (getLast_article() != null) {
                bean.setUpdated(String.valueOf(getLast_article().getCreate_time()));
                bean.setLastChapter(getLast_article().getTitle());
            }
//        bean.setChaptersCount(getBook().getChaptersCount());
            return bean;
        }
    }

}
