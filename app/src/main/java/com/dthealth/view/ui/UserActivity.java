package com.dthealth.view.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dthealth.R;
import com.dthealth.service.model.User;
import com.dthealth.view.adapter.UserAdapter;
import com.dthealth.viewmodel.UserInfoViewModel;

public class UserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        UserInfoViewModel userInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        final UserAdapter userAdapter = new UserAdapter(this);
        recyclerView.setAdapter(userAdapter);
//        userInfoViewModel.invalidateDataSource();
        userInfoViewModel.userPageList.observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(PagedList<User> users) {
                userAdapter.submitList(users);
            }
        });
//        StompClient.init();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                StompClient.connect(new ProcessSocketMessage() {
//                    @Override
//                    public void processStompMessage(StompMessage message) {
//
//                    }
//                    @Override
//                    public void processMessage(String message) {
//                        if (message.equals("isConnected")) {
//                            subscribe();
//                        }
//                    }
//                });
//            }
//        }).start();
    }

//    public void subscribe() {
//        try {
//            StompClient.subscribeTopic("greetings", new ProcessSocketMessage() {
//                @Override
//                public void processStompMessage(StompMessage message) {
//                    Log.i("AppCompatActivity", message.getPayload());
//                }
//
//                @Override
//                public void processMessage(String message) {
//                    Log.i("AppCompatActivity", message);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        sendMessage();
//    }
//
//    public void sendMessage(){
//        StompClient.sendMessage("/queue/toPlatform","hello");
//        StompClient.sendMessage("/queue/toUser","hello");
//    }
//
//    public void showMessage(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
}
