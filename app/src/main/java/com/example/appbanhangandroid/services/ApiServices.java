package com.example.appbanhangandroid.services;


import com.example.appbanhangandroid.model.Cart;
import com.example.appbanhangandroid.model.Product;
import com.example.appbanhangandroid.model.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServices {
    //Sử dụng máy ảo android studio thì localhost thay thành ip 10.0.2.2
    //Đối với sử dụng máy thật ta sử dụng ip của máy
    //Base_URL là url của api

    public static String BASE_URL = "http://10.0.2.2:3000/api/";

    //Annotations @GET cho method GET và url phương gọi
    //BASE_URL + @GET("get-list-distributor") = http://10.0.2.2:3000/api/get-list-distributor

//    @Multipart
//    @POST("register-send-email")
//    Call<Response<User>> register(@Part("username")RequestBody username,
//                                  @Part("password")RequestBody password,
//                                  @Part("email")RequestBody email,
//                                  @Part("name")RequestBody name,
//                                  @Part MultipartBody.Part avatar);

    @GET("get-list-product")
    Call<Response<ArrayList<Product>>> getListProduct();

    @GET("get-product-by-id/{id}")
    Call<Response<Product>> getProductById(@Path("id") String id);

    @POST("add-product")
    Call<Response<Product>> addDistributor (@Body Product product);

    //Param url sẽ bỏ vào {}
    @DELETE("delete-product-by-id/{id}")
    Call<Response<Product>> deleteProductById(@Path("id") String id);

    @PUT("update-product-by-id/{id}")
    Call<Response<Product>> updateProductById(@Path("id") String id, @Body Product product);
    @GET("get-list-cart")
    Call<Response<ArrayList<Cart>>> getListCart();

    @POST("add-to-cart")
    Call<Response<Cart>> addToCart(@Body Cart cart);

    @PUT("/update-quantity/{productId}")
    Call<Response<Cart>> updateQuantity(@Path("productId") String productId, @Body Cart cart);
}
