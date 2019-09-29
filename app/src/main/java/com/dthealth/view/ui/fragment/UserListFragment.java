package com.dthealth.view.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dthealth.R;
import com.dthealth.dao.entity.User;
import com.dthealth.util.RoomDbClient;
import com.dthealth.view.adapter.UserListAdapter;
import com.dthealth.view.ui.activity.MessengerActivity;
import com.dthealth.viewmodel.UserListViewModel;
import com.google.gson.Gson;

public class UserListFragment extends Fragment {
    private UserListAdapter userListAdapter;
    private UserListViewModel mViewModel;
    private RecyclerView recyclerView;
    private Gson gson = new Gson();

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userListAdapter = new UserListAdapter(getActivity());
        userListAdapter.setItemClickListener(new UserListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Intent intent = new Intent(getActivity(), MessengerActivity.class);
                intent.putExtra("user", gson.toJson(user));
                startActivity(intent);
            }
        });
        recyclerView = getActivity().findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(userListAdapter);
        if (mViewModel == null) {
            mViewModel = ViewModelProviders.of(this).get(UserListViewModel.class);
        }
//        userListViewModel.invalidateDataSource();
        mViewModel.getUserPageList().observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(PagedList<User> users) {
                userListAdapter.submitList(users);
                for (User user : users) {
                    if (RoomDbClient.getInstance().getRoomDb().userDao().findUserList().size() == 0) {
                        RoomDbClient.getInstance().getRoomDb().userDao().insertUser(user);
                    } else {
                        RoomDbClient.getInstance().getRoomDb().userDao().updateUser(user);
                    }
                }
            }
        });
    }

}
