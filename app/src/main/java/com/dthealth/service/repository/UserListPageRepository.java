package com.dthealth.service.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.dthealth.service.dataSource.UserListPageDataSource;
import com.dthealth.service.model.User;

public class UserListPageRepository extends DataSource.Factory {
    public MutableLiveData<PageKeyedDataSource<Integer, User>> sourceLiveData =new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        UserListPageDataSource userListPageDataSource = new UserListPageDataSource();
        sourceLiveData.postValue(userListPageDataSource);
        return userListPageDataSource;
    }


//
//    public void doAction(){
//
//    }
}
