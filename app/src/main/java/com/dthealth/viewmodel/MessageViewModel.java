package com.dthealth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dthealth.callback.MessageResultInterface;
import com.dthealth.callback.SocketResultInterface;
import com.dthealth.dao.entity.Message;
import com.dthealth.model.Result;
import com.dthealth.repository.MessageRepository;

public class MessageViewModel extends ViewModel {
    private MutableLiveData<String> messageSendResult = new MutableLiveData<>();
    private MutableLiveData<Message> messageMutableLiveData = new MutableLiveData<>();
    private MessageRepository messageRepository;

    public MessageViewModel(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void messageSubscribe() {
        messageRepository.messageSubscribe(new MessageResultInterface() {
            @Override
            public void processMessage(Message message) {
                messageMutableLiveData.postValue(message);
            }

            @Override
            public void processException(Throwable throwable) {
                messageSendResult.postValue(throwable.getMessage());
            }
        });
    }

//    public void sendMessageToPlatform(Object message) {
//        messageRepository.sendMessageToPlatform(message, new SocketResultInterface() {
//            @Override
//            public void processResult(Result result) {
//                if (result instanceof Result.Success) {
//                    messageSendResult.postValue(((Result.Success<String>) result).getData());
//                }
//                if (result instanceof Result.Error) {
//                    messageSendResult.postValue(((Result.Error) result).getError().getMessage());
//                }
//                if (result instanceof Result.Failed) {
//                    messageSendResult.postValue(((Result.Failed<String>) result).getData());
//                }
//            }
//        });
//    }

//    public void sendMessageToUser(String targetUserId, Object message) {
//        messageRepository.sendMessageToUser(targetUserId, message, new SocketResultInterface() {
//            @Override
//            public void processResult(Result result) {
//                if (result instanceof Result.Success) {
//                    messageSendResult.postValue(((Result.Success<String>) result).getData());
//                }
//                if (result instanceof Result.Error) {
//                    messageSendResult.postValue(((Result.Error) result).getError().getMessage());
//                }
//                if (result instanceof Result.Failed) {
//                    messageSendResult.postValue(((Result.Failed<String>) result).getData());
//                }
//            }
//        });
//    }

    public LiveData<String> getMessageSendResult() {
        return messageSendResult;
    }

    public MutableLiveData<Message> getMessageMutableLiveData() {
        return messageMutableLiveData;
    }
}
