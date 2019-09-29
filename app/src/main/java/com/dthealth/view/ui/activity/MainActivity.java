package com.dthealth.view.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.dthealth.R;
import com.dthealth.background.BackgroundService;
import com.dthealth.dao.entity.LoggedInUser;
import com.dthealth.util.RoomDbClient;
import com.dthealth.view.ui.fragment.CurrentIndexFragment;
import com.dthealth.view.ui.fragment.CurrentStatusFragment;
import com.dthealth.view.ui.fragment.UserListFragment;
import com.dthealth.viewmodel.CurrentStatusViewModel;
import com.dthealth.viewmodel.MessageViewModel;
import com.dthealth.viewmodel.StompConnectionViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements Thread.UncaughtExceptionHandler {

    private Fragment[] fragments;
    private int lastFragment;
    private BottomNavigationView navView;



    protected void showNormalDialog(View v) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Thread.setDefaultUncaughtExceptionHandler(this);
        initSocket();
        initFragment();
//        BadgeTextView mBadgeView = new BadgeTextView(this);
//        mBadgeView.setTargetView(findViewById(R.id.navigation_notifications));
//        mBadgeView.setBadgeCount(92)
//                .setmDefaultRightPadding(40)
//                .setmDefaultTopPadding(20);
        //mBadgeView.setVisibility(View.INVISIBLE);

    }


    private void initSocket() {
        BackgroundService.getSocketConnectionResultLiveData().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showSocketConnectionResult(s);
            }
        });
        Intent intent = new Intent(MainActivity.this, BackgroundService.class);
        startService(intent);


//        stompConnectionViewModel = ViewModelProviders.of(this, new StompConnectionViewModelFactory())
//                .get(StompConnectionViewModel.class);
//        currentStatusViewModel = ViewModelProviders.of(this, new CurrentStatusViewModelFactory())
//                .get(CurrentStatusViewModel.class);
//        messageViewModel = ViewModelProviders.of(this, new MessageViewModelFactory())
//                .get(MessageViewModel.class);
//
//        stompConnectionViewModel.getSocketConnectionResult().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                showSocketConnectionResult(s);
//            }
//        });
//        currentStatusViewModel.getLiveDataPhysicalIndex().observe(this, new Observer<PhysicalIndex>() {
//            @Override
//            public void onChanged(PhysicalIndex physicalIndex) {
//                DetectResult detectResult = PhysicalIndexListener.getInstance().detectAbnormalStatus(physicalIndex);
//                MessageNotification.notify(MainActivity.this, detectResult.getContent(), 0);
//            }
//        });
//        currentStatusViewModel.getSocketConnectionResult().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                showSocketConnectionResult(s);
//            }
//        });
//        messageViewModel.getMessageMutableLiveData().observe(this, new Observer<Message>() {
//            @Override
//            public void onChanged(Message message) {
//                message.setObjectId(message.getMyId());
//                message.setMyId(loggedInUser.getId());
//            }
//        });
//        messageViewModel.getMessageSendResult().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                showSocketConnectionResult(s);
//            }
//        });
//        if (CompositeDisposableUtil.getCompositeDisposable() == null) {
//            stompConnectionViewModel.socketConnect();
//            currentStatusViewModel.physicalIndexSubscribe();
//            messageViewModel.messageSubscribe();
//        }
    }

    private void showSocketConnectionResult(String result) {
        Toast.makeText(this.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }

    private void initFragment() {
        CurrentIndexFragment currentIndexFragment = CurrentIndexFragment.newInstance();
        CurrentStatusFragment currentStatusFragment = CurrentStatusFragment.newInstance();
        UserListFragment userListFragment = UserListFragment.newInstance();
        fragments = new Fragment[]{currentIndexFragment, currentStatusFragment, userListFragment};
        lastFragment = 0;
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, currentIndexFragment).show(currentIndexFragment).commit();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        if (lastFragment != 0) {
                            switchFragment(lastFragment, 0);
                            lastFragment = 0;
                        }
                        return true;
                    case R.id.navigation_dashboard:
                        if (lastFragment != 1) {
                            switchFragment(lastFragment, 1);
                            lastFragment = 1;
                        }
                        return true;
                    case R.id.navigation_notifications:
                        if (lastFragment != 2) {
                            switchFragment(lastFragment, 2);
                            lastFragment = 2;
                        }
                        return true;
                }
                return false;
            }
        });
    }

    private void switchFragment(int lastFragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastFragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.main_content, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        Log.i("AAA", "uncaughtException   " + throwable);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
