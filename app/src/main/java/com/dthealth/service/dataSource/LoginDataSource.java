package com.dthealth.service.dataSource;

import com.dthealth.service.callback.SocketResultInterface;
import com.dthealth.service.model.Result;
import com.dthealth.service.model.User;
import com.dthealth.util.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    public static volatile boolean isLogining = true;
    private Gson gson = new Gson();

    public void login(String username, String password, SocketResultInterface socketResultInterface) {
        Call<ResponseBody> call = RetrofitClient.getAccInstance().getApi().login(username, password);
        try {
            Response<ResponseBody> response = call.execute();
            assert response.body() != null;
            String json = new String(response.body().bytes());
            User user = gson.fromJson(json, new TypeToken<User>() {
            }.getType());
            socketResultInterface.processResult(new Result.Success<>(user));
        } catch (IOException e) {
            socketResultInterface.processResult(new Result.Error(new IOException("Error logging in", e)));
        }
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                assert response.body() != null;
//                String json = null;
//                try {
//                    json = new String(response.body().bytes());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                User user = gson.fromJson(json, new TypeToken<User>() {
//                }.getType());
//                socketResultInterface.processResult(new Result.Success<>(user));
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                socketResultInterface.processResult(new Result.Error(new IOException("Error logging in", t)));
//            }
//        });
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
