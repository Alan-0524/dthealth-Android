package com.dthealth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dthealth.service.callback.MessageResultInterface;
import com.dthealth.service.callback.PhysicalIndexResultInterface;
import com.dthealth.service.callback.SocketResultInterface;
import com.dthealth.service.model.DetectResult;
import com.dthealth.service.model.PhysicalIndex;
import com.dthealth.service.model.Result;
import com.dthealth.service.repository.CurrentStatusRepository;
import com.dthealth.util.PhysicalIndexListener;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.google.gson.Gson;

public class CurrentStatusViewModel extends ViewModel {
    private static Gson gson = new Gson();


    private MutableLiveData<String> socketConnectionResult = new MutableLiveData<>();
    private MutableLiveData<String> messageSendResult = new MutableLiveData<>();
    private MutableLiveData<LineData> lineDataMutableLiveData = new MutableLiveData<>();
    private CurrentStatusRepository currentStatusRepository;

    public CurrentStatusViewModel(CurrentStatusRepository currentStatusRepository) {
        this.currentStatusRepository = currentStatusRepository;
    }

    public void socketConnect() {
        currentStatusRepository.socketConnect(new SocketResultInterface() {
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

    public void physicalIndexSubscribe(LineData lineData) {

        currentStatusRepository.physicalIndexSubscribe(new PhysicalIndexResultInterface() {
            @Override
            public void assemblePhysicalIndex(String message) {
                PhysicalIndex physicalIndex = gson.fromJson(message, PhysicalIndex.class);
                int count = lineData.getDataSetByIndex(0).getEntryCount();

                lineData.addEntry(new Entry(count, physicalIndex.getHeartbeatStrength()), 0);
                lineData.addEntry(new Entry(count, physicalIndex.getBloodPressure()), 1);
                lineData.addEntry(new Entry(count, physicalIndex.getBloodFat()), 2);
                lineData.addEntry(new Entry(count, physicalIndex.getBloodGlucose()), 3);
                lineData.addEntry(new Entry(count, physicalIndex.getTemperature()), 4);

                lineDataMutableLiveData.postValue(lineData);
                DetectResult detectResult = PhysicalIndexListener.getInstance().detectAbnormalStatus(physicalIndex);

            }

            @Override
            public void processException(Throwable throwable) {
                socketConnectionResult.postValue("Connection failed");
            }
        });
    }

    public LiveData<LineData> getLineDataMutableLiveData() {
        return lineDataMutableLiveData;
    }

    public void messageSubscribe() {
        currentStatusRepository.messageSubscribe(new MessageResultInterface() {
            @Override
            public void processMessage(String message) {

            }

            @Override
            public void processException(Throwable throwable) {

            }
        });
    }

    public void sendMessageToPlatform(Object message) {
        currentStatusRepository.sendMessageToPlatform(message, new SocketResultInterface() {
            @Override
            public void processResult(Result result) {
                if (result instanceof Result.Success) {
                    messageSendResult.postValue(((Result.Success<String>) result).getData());
                }
                if (result instanceof Result.Error) {
                    messageSendResult.postValue(((Result.Error) result).getError().getMessage());
                }
                if (result instanceof Result.Failed) {
                    messageSendResult.postValue(((Result.Failed<String>) result).getData());
                }
            }
        });
    }

    public void sendMessageToUser(String targetUserId, Object message) {
        currentStatusRepository.sendMessageToUser(targetUserId, message, new SocketResultInterface() {
            @Override
            public void processResult(Result result) {
                if (result instanceof Result.Success) {
                    messageSendResult.postValue(((Result.Success<String>) result).getData());
                }
                if (result instanceof Result.Error) {
                    messageSendResult.postValue(((Result.Error) result).getError().getMessage());
                }
                if (result instanceof Result.Failed) {
                    messageSendResult.postValue(((Result.Failed<String>) result).getData());
                }
            }
        });
    }

    public LiveData<String> getMessageSendResult() {
        return messageSendResult;
    }
}
