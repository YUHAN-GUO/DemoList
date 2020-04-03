package com.think.guoyh.data.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.bean.ParamsBuilder;
import com.base.gyh.baselib.data.remote.okhttp.ModelSuperImpl;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.base.BaseModule;
import com.think.guoyh.data.bean.text.TextArticleBean;
import com.think.guoyh.data.bean.text.TextContentBean;
import com.think.guoyh.data.bean.text.TextDetailsBean;
import com.think.guoyh.data.module.db.ChapterInfoBean;
import com.think.guoyh.data.module.db.dbManage.BookRepository;
import com.think.guoyh.widget.page.TxtChapter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class TextModule extends BaseModule {
    private Subscription mChapterSub;


    private static volatile TextModule module;

    public static TextModule getModule() {
        if (module == null) {
            synchronized (TextModule.class) {
                if (module == null) {
                    module = new TextModule();
                }
            }
        }
        return module;
    }

    public void getListText(Fragment fragment, IBaseHttpResultCallBack<String> callBack) {
        ModelSuperImpl.netWork().gankGet(ParamsBuilder.build().url("http://novel.duoduvip.com/api/rank/getRecommendList/?type=1").heads(getHeard()).fragment(fragment), callBack);
    }

    public void getBookDetails(String id, Fragment fragment, IBaseHttpResultCallBack<TextDetailsBean> callBack) {
        ModelSuperImpl.netWork().gankGet(ParamsBuilder.build().url("http://novel.duoduvip.com/api/book/getBookDetail/?book_id=" + id).heads(getHeard()).fragment(fragment), TextDetailsBean.class, callBack);
    }

    public void getBookArticle(String id, Activity activity, IBaseHttpResultCallBack<TextArticleBean> callBack) {
        ModelSuperImpl.netWork().gankGet(ParamsBuilder.build().url("http://novel.duoduvip.com/api/book/getBookArticle/?has_content=2&limit=10000&book_id=" + id + "&page=1").heads(getHeard()).activity(activity), TextArticleBean.class, callBack);

    }

    public void getBookArticleDetail(String bookId, List<TxtChapter> requestChapters, Activity activity, IBaseHttpResultCallBack<ChapterInfoBean> callBack) {
        int size = requestChapters.size();
        //取消上次的任务，防止多次加载
        if (mChapterSub != null) {
            mChapterSub.cancel();;
        }
        ArrayList<Single<ChapterInfoBean>> chapterInfos = new ArrayList<>();
        ArrayDeque<String> titles = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            TxtChapter txtChapter = requestChapters.get(i);
            Single<ChapterInfoBean> chapterInfoSingle = getBookContent(txtChapter.getChapterId() + "",activity);
            chapterInfos.add(chapterInfoSingle);
            titles.add(txtChapter.getTitle());
        }
        Single.concat(chapterInfos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChapterInfoBean>() {
                    private String title = titles.poll();

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                        mChapterSub = s;

                    }

                    @Override
                    public void onNext(ChapterInfoBean chapterInfoBean) {
                        Logger.dd(bookId+"-----"+title);
                        BookRepository.getInstance().saveChapterInfo(bookId, title, chapterInfoBean.getBody());
                        title = titles.poll();
                        callBack.onSuccess(chapterInfoBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (requestChapters.get(0).getTitle() == title) {
                            callBack.onError(t);
                        }
                        Logger.e("%s+++++++%s", "tag123", t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Single<ChapterInfoBean> getBookContent(String id,Activity activity) {
        Single<ChapterInfoBean> single = Single.create(new SingleOnSubscribe<ChapterInfoBean>() {
            @Override
            public void subscribe(SingleEmitter<ChapterInfoBean> emitter) throws Exception {
                ModelSuperImpl.netWork().gankGet(ParamsBuilder.build().url("http://novel.duoduvip.com/api/book/getArticleDetail?article_id=" +id).heads(getHeard()).activity(activity), TextContentBean.class,new IBaseHttpResultCallBack<TextContentBean>() {
                    @Override
                    public void onSuccess(TextContentBean data) {
                        TextContentBean.DataBean.ArticleBean article = data.getData().getArticle().get(0);
                        emitter.onSuccess(new ChapterInfoBean(article.getTitle(),article.getContent(),article.getWords()));
                        Logger.dd("tagg123",data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        emitter.onError(e);
                        Logger.d("%s++++++++%s","tagg123",e.getMessage());
                    }
                });
            }
        });
        return single;
//        return null;
    }

    public Observable<ChapterInfoBean> getChapterInfo(String id, Activity activity) {
        Observable<ChapterInfoBean> observable = Observable.create(new ObservableOnSubscribe<ChapterInfoBean>() {
            @Override
            public void subscribe(ObservableEmitter<ChapterInfoBean> e) throws Exception {
//                Log.d(TAG, "netObservable 执行了");
                ModelSuperImpl.netWork().gankGet(ParamsBuilder.build().url("http://novel.duoduvip.com/api/book/getArticleDetail?article_id=" + id).heads(getHeard()).activity(activity), TextContentBean.class, new IBaseHttpResultCallBack<TextContentBean>() {
                    @Override
                    public void onSuccess(TextContentBean data) {
//                        e.onNext(new ChapterInfoBean(data.getData().getArticle().get(0).getContent()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
//                e.onNext(e);
//                e.onComplete();
            }
        });
        return observable;
    }

    /**
     * get方法拼接字符串
     */
    private String getUrlString(String path, HashMap<String, String>query){
        String mypath = path;
        if (query != null && query.size() > 0) {
            StringBuilder  pathWithQuery = new StringBuilder(path);
            if (!path.contains("?")) {
                pathWithQuery.append("?");
            } else {
                pathWithQuery.append("&");
            }
            Set<Map.Entry<String, String>> entries = query.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entries.iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String val = next.getValue();
                pathWithQuery.append(key);
                pathWithQuery.append("=");
                pathWithQuery.append(val);
                pathWithQuery.append("&");
            }
            pathWithQuery.deleteCharAt(pathWithQuery.length() - 1);
            mypath = pathWithQuery.toString();
        }

        return mypath;
    }

}
