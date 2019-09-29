package com.dthealth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.dthealth.dao.entity.User;
import com.dthealth.dataSource.UserListPageDataSource;
import com.dthealth.repository.UserListPageRepository;

public class UserListViewModel extends ViewModel {
    private LiveData<PagedList<User>> userPageList;


    public UserListViewModel() {
        UserListPageRepository userListPageRepository = new UserListPageRepository();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPrefetchDistance(UserListPageDataSource.DISTANCE)
                        .setPageSize(UserListPageDataSource.PAGE_SIZE).build();
        userPageList = (new LivePagedListBuilder<>(userListPageRepository, pagedListConfig)).build();
    }

    public LiveData<PagedList<User>> getUserPageList() {
        return userPageList;
    }
}
