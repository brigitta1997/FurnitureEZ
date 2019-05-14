package com.example.user.furnitureproject2.Helper;

import com.example.user.furnitureproject2.Model.ResponseMessage;
import com.example.user.furnitureproject2.Model.SavedObject;
import com.example.user.furnitureproject2.Model.Slider;
import com.example.user.furnitureproject2.Model.User;
import com.example.user.furnitureproject2.Model.VrObject;
import com.example.user.furnitureproject2.Model.Company;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApiSearch {

    @GET("api/furni/{id}")
    Call<VrObject> getObj(@Path("id") int obj_id);

    @POST("api/search")
    Call<List<VrObject>> createPost(@Body VrObject vro);

    @GET("api/savedObj/{id}")
    Call<List<VrObject>> getSavedObj(@Path("id") int user_id);

    @POST("api/userCred")
    Call<User> getUserAPI(@Body User user);

    @POST("api/savedObj/save")
    Call<ResponseMessage> postSavedObj(@Body SavedObject savedObject);

    @GET("api/sfb/{id}")
    Call<List<VrObject>> getSfbObj(@Path("id") int user_id);

    @GET("api/company/logo")
    Call<List<Company>> getAllLogo();

    @GET("api/company/{name}")
    Call<List<VrObject>> getItemFromCompany(@Path("name") String company);

    @GET("api/furniType/{type}")
    Call<List<VrObject>> getFurniByType(@Path("type") String type);

    @POST("api/register")
    Call<ResponseMessage> register(@Body User user);

    @GET("api/savedObj/delete/{user_id}/{obj_id}")
    Call<ResponseMessage> deleteSavedObj(@Path("user_id") int user_id, @Path("obj_id") int obj_id);

    @GET("api/savedObj/exist/{user_id}/{obj_id}")
    Call<ResponseMessage> is_savedObject(@Path("user_id") int user_id, @Path("obj_id") int obj_id);

    @GET("api/slider/{objId}")
    Call<List<Slider>> getImgSlider(@Path("objId") int obj_id);
}
