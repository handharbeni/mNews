package illiyin.mhandharbeni.databasemodule.services;

import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseFeaturedPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetAbout;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetAllPost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetComment;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetKategori;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetMenus;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetPostByTags;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetPostKategori;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseGetSinglePost;
import illiyin.mhandharbeni.databasemodule.model.mnews.response.ResponseSearchPost;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Beni on 24/03/2018.
 */

public interface MnewsServices {
    String mnewstoken = "$2y$10$tT8YV7d0bN.h7hQiW8ZBuuxkMJOwL1ZlKd91lUhOOLhMjgBbcCfk2";
    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @GET("posts")
    Call<ResponseGetAllPost> getAllPost(@Query("page") String page);

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @GET("post/{page}")
    Call<ResponseGetSinglePost> getSinglePost(@Path("page") String page);

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @FormUrlEncoded
    @GET("categories")
    Call<ResponseGetKategori> getCategory();

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @FormUrlEncoded
    @GET("category/{post}")
    Call<ResponseGetPostKategori> getPostCategory(@Path("post") String post, @Query("page") String page);

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @FormUrlEncoded
    @GET("tags")
    Call<ResponseGetKategori> getTags(@Query("page") String page);

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @FormUrlEncoded
    @GET("tag/{tags}")
    Call<ResponseGetPostByTags> getPostByTags(@Path("tags") String tags, @Query("page") String page);

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @FormUrlEncoded
    @GET("search/{posts}")
    Call<ResponseSearchPost> searchPosts(@Path("posts") String posts, @Query("page") String page);

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @GET("featured/post")
    Observable<ResponseFeaturedPost> getFeaturedPosts();

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @GET("comments/list/{posts}")
    Call<ResponseGetComment> getComment(@Path("posts") String posts, @Query("page") String page);

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @GET("menus/list")
    Observable<ResponseGetMenus> getMenus();

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @GET("tos")
    Call<ResponseGetKategori> getTOS();

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @GET("about")
    Call<ResponseGetAbout> getAbout();

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @FormUrlEncoded
    @GET("categories")
    Call<ResponseGetKategori> postComment(@Header("usertoken") String usertoken, @Field("post_slug") String post_slug, @Field("Comment") String comment);

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @FormUrlEncoded
    @GET("comments/report/{id}")
    Call<ResponseGetKategori> reportComment(@Header("usertoken") String usertoken, @Path("id") String id);

    @Headers({
            "Content-Type: application/json",
            "mnewstoken: "+mnewstoken
    })
    @FormUrlEncoded
    @GET("comments/delete/{id}")
    Call<ResponseGetKategori> deleteComment(@Header("usertoken") String usertoken, @Path("id") String id);
}
