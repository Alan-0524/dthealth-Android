package com.dthealth.view.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.dthealth.R;
import com.dthealth.background.BackgroundService;
import com.dthealth.callback.SocketResultInterface;
import com.dthealth.dao.entity.LoggedInUser;
import com.dthealth.dao.entity.User;
import com.dthealth.dataSource.MessageDataSource;
import com.dthealth.model.Result;
import com.dthealth.repository.MessageRepository;
import com.dthealth.util.RoomDbClient;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class MessengerActivity extends AppCompatActivity {
    private ChatView mChatView;
    private Gson gson = new Gson();
    private LoggedInUser loggedInUser;
    private Message message;
    private MessageRepository messageRepository = MessageRepository.getInstance(new MessageDataSource());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);

        myToolbar.setNavigationIcon(R.drawable.ic_action_stat_reply);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        User user = gson.fromJson(intent.getStringExtra("user"), User.class);
        myToolbar.setTitle(user.getFullName());
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        List<LoggedInUser> loggedInUserList = RoomDbClient.getInstance().getRoomDb().loggedInUserDao().getLoggedInUser();
        loggedInUser = loggedInUserList.get(0);
        mChatView = (ChatView) findViewById(R.id.chat_view);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.design_default_color_primary));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);
        try {
            List<com.dthealth.dao.entity.Message> list = RoomDbClient.getInstance().getRoomDb().messageDao()
                    .findMessageById(loggedInUser.getId(), user.getId());
            if (list != null) {
                for (com.dthealth.dao.entity.Message m : list) {
                    message = new Message.Builder()
                            .setUser(m.getType() == '0' ? loggedInUser : user)
                            .setRight(m.getType() == '1' ? true : false)
                            .setText(m.getContent())
                            .setSendTime(stringToCalendar(m.getCreateTime()))
                            .hideIcon(true)
                            .build();
                    mChatView.send(message);
                }
                mChatView.setInputText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new message
                message = new Message.Builder()
                        .setUser(loggedInUser)
                        .setRight(false)
                        .setText(mChatView.getInputText())
                        .hideIcon(true)
                        .build();
                //Set to chat view
                com.dthealth.dao.entity.Message m = new com.dthealth.dao.entity.Message(UUID.randomUUID().toString()
                        , loggedInUser.getId(), user.getId(), message.getSendTime().getTime().toString(),
                        '1', '0', mChatView.getInputText());
                RoomDbClient.getInstance().getRoomDb().messageDao()
                        .insert(m);
                messageRepository.sendMessageToUser(m, new SocketResultInterface() {
                    @Override
                    public void processResult(Result result) {
                        showSocketConnectionResult(result.toString());
                    }
                });
                mChatView.send(message);
                //Reset edit text
                mChatView.setInputText("");
            }
        });
        BackgroundService.getMessageMutableLiveData().observeForever(new Observer<com.dthealth.dao.entity.Message>() {
            @Override
            public void onChanged(com.dthealth.dao.entity.Message message) {
                User user = RoomDbClient.getInstance().getRoomDb().userDao().findUserById(message.getMyId());
                mChatView.receive(new Message.Builder()
                        .setUser(user)
                        .setRight(true)
                        .setText(message.getContent())
                        .build());
            }
        });
    }

    private void showSocketConnectionResult(String result) {
        Toast.makeText(this.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }

    private Calendar stringToCalendar(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(date));
        return calendar;
    }

}
