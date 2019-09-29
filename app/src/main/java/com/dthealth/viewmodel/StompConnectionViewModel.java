package com.dthealth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dthealth.callback.SocketResultInterface;
import com.dthealth.model.Result;
import com.dthealth.repository.StompConnectionRepository;

public class StompConnectionViewModel extends ViewModel {
    private StompConnectionRepository stompConnectionRepository;
    private MutableLiveData<String> socketConnectionResult = new MutableLiveData<>();

    public StompConnectionViewModel(StompConnectionRepository stompConnectionRepository) {
        this.stompConnectionRepository = stompConnectionRepository;
    }

    public void socketConnect() {
        stompConnectionRepository.socketConnect(new SocketResultInterface() {
            @Override
            public void processResult(Result result) {
                if (result instanceof Result.Success) {
                    socketConnectionResult.postValue(((Result.Success<String>) result).getData());
                }
                if (result instanceof Result.Error) {
                    socketConnectionResult.postValue(((Result.Error) result).getError().getMessage());
                }
                if (result instanceof Result.Failed) {
                    socketConnectionResult.postValue(((Result.Failed<String>) result).getData());
                }
            }
        });
    }

    public LiveData<String> getSocketConnectionResult() {
        return socketConnectionResult;
    }
}
