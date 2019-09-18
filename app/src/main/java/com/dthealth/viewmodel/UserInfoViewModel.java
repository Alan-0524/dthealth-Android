package com.dthealth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.dthealth.service.dataSource.UserListPageDataSource;
import com.dthealth.service.model.User;
import com.dthealth.service.repository.UserListPageRepository;

public class UserInfoViewModel extends ViewModel {
    public LiveData<PagedList<User>> userPageList;
    public LiveData<PageKeyedDataSource<Integer, User>> liveDataSource;

    public UserInfoViewModel() {
        UserListPageRepository userListPageRepository = new UserListPageRepository();
        liveDataSource = userListPageRepository.sourceLiveData;
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPrefetchDistance(UserListPageDataSource.DISTANCE)
                        .setPageSize(UserListPageDataSource.PAGE_SIZE).build();
        userPageList = (new LivePagedListBuilder<>(userListPageRepository, pagedListConfig)).build();
    }

}
