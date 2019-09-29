package com.dthealth.dataSource;

import com.dthealth.model.DigitalUserModel;
import com.dthealth.util.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DigitalUserModelDataSource {
    private Gson gson = new Gson();

    public DigitalUserModel findDigitalUserModelByUserId(String userId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", userId);
        Call<ResponseBody> call = RetrofitClient.getAccInstance().getApi().findDigitalUserModelByUserId(parameters);
        Response<ResponseBody> response = null;
        try {
            response = call.execute();
            String json = new String(response.body().bytes());
            return gson.fromJson(json, new TypeToken<DigitalUserModel>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
