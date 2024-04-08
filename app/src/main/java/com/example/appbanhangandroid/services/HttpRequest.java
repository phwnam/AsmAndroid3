package com.example.appbanhangandroid.services;

import static com.example.appbanhangandroid.services.ApiServices.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    //Biến Interface ApiServices
    private ApiServices requestInterface;

    //Hàm tạo
    public HttpRequest(){
        //Create Retrofit
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiServices.class);
    }

    public ApiServices callAPI(){
        //Get Retrofit
        return requestInterface;
    }
}
