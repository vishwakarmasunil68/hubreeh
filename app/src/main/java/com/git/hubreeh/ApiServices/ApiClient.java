package com.git.hubreeh.ApiServices;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static OkHttpClient clientJob;
    private static OkHttpClient clientBusiness;
    private static Retrofit retrofitJob = null;
    private static Retrofit retrofitBusiness = null;

    public static Retrofit getJobClient() {
        if (retrofitJob == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientJob = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.MINUTES)
                    .addInterceptor(logging)
                    .build();
            retrofitJob = new Retrofit.Builder()
                    .baseUrl("https://speedonews.com/demo/appiqo_hubreeh/webservice_v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientJob)
                    .build();
        }
        return retrofitJob;
    }

    public static Retrofit getBusinessClient() {
        if (retrofitBusiness == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBusiness = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.MINUTES)
                    .addInterceptor(logging)
                    .build();
            retrofitBusiness = new Retrofit.Builder()
                    .baseUrl("https://speedonews.com/demo/appiqo_hubreeh/webservice_v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBusiness)
                    .build();
        }
        return retrofitBusiness;
    }
}
