package com.dthealth.background;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.dthealth.callback.MessageResultInterface;
import com.dthealth.callback.PhysicalIndexResultInterface;
import com.dthealth.callback.SocketResultInterface;
import com.dthealth.dao.entity.LoggedInUser;
import com.dthealth.dao.entity.Message;
import com.dthealth.dataSource.CurrentStatusDataSource;
import com.dthealth.dataSource.MessageDataSource;
import com.dthealth.dataSource.StompConnection;
import com.dthealth.model.DetectResult;
import com.dthealth.model.PhysicalIndex;
import com.dthealth.model.Result;
import com.dthealth.repository.CurrentStatusRepository;
import com.dthealth.repository.MessageRepository;
import com.dthealth.repository.StompConnectionRepository;
import com.dthealth.util.HeartbeatCalculator;
import com.dthealth.util.PhysicalIndexListener;
import com.dthealth.util.RoomDbClient;
import com.dthealth.view.ui.fragment.MessageNotification;
import com.google.gson.Gson;

import java.util.UUID;

public class BackgroundService extends IntentService {
    private static volatile MutableLiveData<String> socketConnectionResultLiveData = new MutableLiveData<>();
    private static volatile MutableLiveData<Message> messageMutableLiveData = new MutableLiveData<>();
    private static volatile MutableLiveData<PhysicalIndex> physicalIndexMutableLiveData = new MutableLiveData<>();
    private CurrentStatusRepository currentStatusRepository;
    private MessageRepository messageRepository;
    private StompConnectionRepository stompConnectionRepository;

    private PhysicalIndex physicalIndex;
    private HeartbeatCalculator heartbeatCalculator = new HeartbeatCalculator();
    private Gson gson = new Gson();
    private LoggedInUser loggedInUser = RoomDbClient.getInstance()
            .getRoomDb()
            .loggedInUserDao()
            .getLoggedInUser()
            .get(0);

    public BackgroundService() {
        super("BackgroundService");
        currentStatusRepository = CurrentStatusRepository.getInstance(new CurrentStatusDataSource());
        messageRepository = MessageRepository.getInstance(new MessageDataSource());
        stompConnectionRepository = StompConnectionRepository.getInstance(new StompConnection());
    }

    public static MutableLiveData<String> getSocketConnectionResultLiveData() {
        return socketConnectionResultLiveData;
    }

//    private void initSocket() {
//        stompConnectionViewModel = ViewModelProviders.of(this, new StompConnectionViewModelFactory())
//                .get(StompConnectionViewModel.class);
//        currentStatusViewModel = ViewModelProviders.of(this, new CurrentStatusViewModelFactory())
//                .get(CurrentStatusViewModel.class);
//        messageViewModel = ViewModelProviders.of(this, new MessageViewModelFactory())
//                .get(MessageViewModel.class);
//
//        stompConnectionViewModel.getSocketConnectionResult().observeForever(new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                showSocketConnectionResult(s);
//            }
//        });
//        currentStatusViewModel.getLiveDataPhysicalIndex().observeForever(new Observer<PhysicalIndex>() {
//            @Override
//            public void onChanged(PhysicalIndex physicalIndex) {
//                DetectResult detectResult = PhysicalIndexListener.getInstance().detectAbnormalStatus(physicalIndex);
//                MessageNotification.notify(getApplicationContext(), detectResult.getContent(), 0);
//            }
//        });
//        currentStatusViewModel.getSocketConnectionResult().observeForever(new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                showSocketConnectionResult(s);
//            }
//        });
//        messageViewModel.getMessageMutableLiveData().observeForever(new Observer<Message>() {
//            @Override
//            public void onChanged(Message message) {
//                message.setObjectId(message.getMyId());
//                message.setMyId(loggedInUser.getId());
//            }
//        });
//        messageViewModel.getMessageSendResult().observeForever( new Observer<String>() {
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
//    }

    public static MutableLiveData<Message> getMessageMutableLiveData() {
        return messageMutableLiveData;
    }

    public static MutableLiveData<PhysicalIndex> getPhysicalIndexMutableLiveData() {
        return physicalIndexMutableLiveData;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        stompConnectionRepository.socketConnect(new SocketResultInterface() {
            @Override
            public void processResult(Result result) {
                if (result instanceof Result.Success) {
                    socketConnectionResultLiveData.postValue(((Result.Success<String>) result).getData());
                }
                if (result instanceof Result.Error) {
                    socketConnectionResultLiveData.postValue(((Result.Error) result).getError().getMessage());
                }
                if (result instanceof Result.Failed) {
                    socketConnectionResultLiveData.postValue(((Result.Failed<String>) result).getData());
                }
            }
        });

        currentStatusRepository.physicalIndexSubscribe(new PhysicalIndexResultInterface() {
            @Override
            public void assemblePhysicalIndex(String message) {
                physicalIndex = gson.fromJson(message, PhysicalIndex.class);
                physicalIndex.setHeartbeat(heartbeatCalculator.calculate());
                physicalIndexMutableLiveData.postValue(physicalIndex);
                DetectResult detectResult = PhysicalIndexListener.getInstance().detectAbnormalStatus(physicalIndex);
                MessageNotification.notify(getApplicationContext(), detectResult.getContent(), 0);
            }

            @Override
            public void processException(Throwable throwable) {
                socketConnectionResultLiveData.postValue("Connection failed");
            }
        });

        messageRepository.messageSubscribe(new MessageResultInterface() {
            @Override
            public void processMessage(Message message) {
                messageMutableLiveData.postValue(message);
                RoomDbClient.getInstance().getRoomDb().messageDao()
                        .insert(new com.dthealth.dao.entity.Message(UUID.randomUUID().toString()
                                , loggedInUser.getId(), message.getMyId(), message.getCreateTime(),
                                '1', '1', message.getContent()));
            }

            @Override
            public void processException(Throwable throwable) {
                socketConnectionResultLiveData.postValue(throwable.getMessage());
            }
        });
    }
}
