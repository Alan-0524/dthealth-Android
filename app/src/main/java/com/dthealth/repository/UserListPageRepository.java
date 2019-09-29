package com.dthealth.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.dthealth.dao.entity.User;
import com.dthealth.dataSource.UserListPageDataSource;

public class UserListPageRepository extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, User>> sourceLiveData = new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        UserListPageDataSource userListPageDataSource = new UserListPageDataSource();
        sourceLiveData.postValue(userListPageDataSource);
        return userListPageDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, User>> getSourceLiveData() {
        return sourceLiveData;
    }
//
//    public void doAction(){
//
//    }
}
