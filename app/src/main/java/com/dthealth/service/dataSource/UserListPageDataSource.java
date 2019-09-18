package com.dthealth.service.dataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.dthealth.service.model.ResponseBodyInRows;
import com.dthealth.service.model.User;
import com.dthealth.util.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class UserListPageDataSource extends PageKeyedDataSource<Integer, User> {
    public final static int DISTANCE = 3;
    public final static int PAGE_SIZE = 10;
    private Gson gson = new Gson();
    private List<User> userList; // this userList must be newed, because when the adapter have not finished process,
    // and once the userList will be loaded again, or changed, the userList in processing in adapter will be modified.
    private List<User> userListTemp;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, User> callback) {
        userList = new ArrayList<>();
        userList = loadUserList(1);
        callback.onResult(userList, null, 2);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {
        userList = new ArrayList<>();
        userList = loadUserList(params.key);
        Integer key = (params.key > 1) ? params.key - 1 : null;
        if (userList.size() > 0) {
            //passing the loaded data
            //and the previous page key
            callback.onResult(userList, key);
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {
        userList = new ArrayList<>();
        userList = loadUserList(params.key);
        if (userList.size() > 0) {
            //if the response has next page
            //incrementing the next page number
            Integer key = (userList.size() < PAGE_SIZE) ? null : params.key + 1;

            //passing the loaded data and next page value
            callback.onResult(userList, key);
        }
    }

    private List<User> loadUserList(int page) {
        userListTemp = new ArrayList<>();
        Map<String, Integer> parameters = new HashMap<>();
        parameters.put("page", page);
        parameters.put("rows", PAGE_SIZE);
        Call<ResponseBody> call = RetrofitClient.getInterInstance().getApi().findAllByPage(parameters);
        Response<ResponseBody> response = null;
        try {
            response = call.execute();
            assert response.body() != null;
            String json = new String(response.body().bytes());
            ResponseBodyInRows<User> responseBodyInRows = gson.fromJson(json, new TypeToken<ResponseBodyInRows<User>>() {
            }.getType());
            userListTemp = responseBodyInRows.getRows();
        } catch (IOException e) {
            Log.e("loadUserList-----------", e.getMessage());
        }
        return userListTemp;
    }
}
