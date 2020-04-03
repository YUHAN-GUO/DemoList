package com.base.gyh.baselib.data.remote.retrofit;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/*
 * created by taofu on 2018/11/27
 **/
//public interface WanAndroidService {

//    /***
//     * 获取首页banner数据
//     * @return
//     */
//    @GET("/banner/json")
//    Observable<BaseBean<List<BannerDataItem>>> getHomeBannerData();

//    /***
//     * 获取首页文章列表数据
//     * @return
//     */
//    @GET("article/list/{pageNo}/json")
//    Observable<BaseBean<ArticleList>> getArticlesData(@Path("pageNo") int num);
//
//    /***
//     * 收藏文章
//     * @param id
//     * @return
//     */
//    @POST("lg/collect/{id}/json")
//    Observable<BaseBean<Object>> collectArticle(@Path("id") int id);
//
//    /***
//     * 文章取消收藏
//     * @param id
//     * @return
//     */
//    @POST("lg/uncollect_originId/{id}/json")
//    Observable<BaseBean<Object>> cancleCollectArticle(@Path("id") int id);
//
//    @GET("/article/list/{pageNo}/json")
//    Observable<BaseBean<ArticleList>> getKnowledgeArticles(@Path("pageNo") int pageNo, @Query("cid") int cid);
//
//    @GET("/lg/collect/list/{pageNo}/json")
//    Observable<BaseBean<ArticleList>> getCollections(@Path("pageNo") int pageNo);
//
//    @POST("lg/uncollect/{id}/json")
//    @FormUrlEncoded
//    Observable<BaseBean<Object>> cancelCollectArticle(@Path("id") int id, @Field("originId") int originid);
//
//    @GET("/user/logout/json")
//    Observable<BaseBean<Object>> logout();
//
//    @GET("/hotkey/json")
//    Observable<BaseBean<List<HotKeyBean>>> getHotKeys();
//
//    @POST("/article/query/{page}/json")
//    @FormUrlEncoded
//    Observable<BaseBean<ArticleList>> queryArticlesByKey(@Path("page") int page, @Field("k") String key);
//
//    @GET("/tree/json")
//    Observable<BaseBean<List<KnowledgeBean>>> getKnowledgeData();
//
//    @GET("/navi/json")
//    Observable<BaseBean<List<NavigationBean>>> getNavigationData();
//
//    @POST("/user/login")
//    @FormUrlEncoded
//    Observable<BaseBean<LoginBean>> login(@Field("username") String userName, @Field("password") String password);
//
//    @POST("/user/register")
//    @FormUrlEncoded
//    Observable<BaseBean<LoginBean>> register(@Field("username") String userName, @Field("password") String password, @Field("repassword") String repssword);
//
//    @GET("/project/tree/json")
//    Observable<BaseBean<List<ProjectCategory>>> getProjectsCategory();
//
//    @GET("/project/list/{pageNo}/json")
//    Observable<BaseBean<ArticleList>> getProjectArticles(@Path("pageNo") int pageNo, @Query("cid") int cid);
//}
