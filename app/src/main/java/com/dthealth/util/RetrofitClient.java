package com.dthealth.util;

import android.util.Log;

import retrofit2.Retrofit;

public class RetrofitClient {
    private static final String SERVER_ADDRESS=PropertiesUtil.getProperties().getProperty("SERVER_URL");
    private static final String INTERACTION_PORT = PropertiesUtil.getProperties().getProperty("INTERACTION_PORT");
    private static final String SYSTEM_ACCESS_PORT = PropertiesUtil.getProperties().getProperty("SYSTEM_ACCESS_PORT");
    private static RetrofitClient accInstance;
    private static RetrofitClient interInstance;
    private Retrofit retrofit;


    private RetrofitClient(String URL) {
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .build();
        }catch (Exception e){
            Log.e("RetrofitClient",e.getMessage());
        }
    }

    public static synchronized RetrofitClient getAccInstance() {
        if (accInstance == null) {
            accInstance = new RetrofitClient(SERVER_ADDRESS+SYSTEM_ACCESS_PORT);
        }
        return accInstance;
    }
    public static synchronized RetrofitClient getInterInstance(){
        if(interInstance == null){
            interInstance = new RetrofitClient(SERVER_ADDRESS+INTERACTION_PORT);
        }
        return interInstance;
    }
    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
