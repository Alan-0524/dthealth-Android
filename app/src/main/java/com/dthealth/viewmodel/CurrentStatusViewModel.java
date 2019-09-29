package com.dthealth.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dthealth.callback.PhysicalIndexResultInterface;
import com.dthealth.model.PhysicalIndex;
import com.dthealth.repository.CurrentStatusRepository;
import com.dthealth.util.HeartbeatCalculator;
import com.google.gson.Gson;

public class CurrentStatusViewModel extends ViewModel {
    private static Gson gson = new Gson();


    private MutableLiveData<String> socketConnectionResult = new MutableLiveData<>();

    private MutableLiveData<PhysicalIndex> liveDataPhysicalIndex = new MutableLiveData<>();
    private PhysicalIndex physicalIndex;
    private CurrentStatusRepository currentStatusRepository;
    private HeartbeatCalculator heartbeatCalculator = new HeartbeatCalculator();

    public CurrentStatusViewModel(CurrentStatusRepository currentStatusRepository) {
        this.currentStatusRepository = currentStatusRepository;
    }


    public void physicalIndexSubscribe() {
        currentStatusRepository.physicalIndexSubscribe(new PhysicalIndexResultInterface() {
            @Override
            public void assemblePhysicalIndex(String message) {
                physicalIndex = gson.fromJson(message, PhysicalIndex.class);
                physicalIndex.setHeartbeat(heartbeatCalculator.calculate());
                liveDataPhysicalIndex.postValue(physicalIndex);
            }

            @Override
            public void processException(Throwable throwable) {
                socketConnectionResult.postValue("Connection failed");
            }
        });
    }

    public MutableLiveData<PhysicalIndex> getLiveDataPhysicalIndex() {
        return liveDataPhysicalIndex;
    }

    public MutableLiveData<String> getSocketConnectionResult() {
        return socketConnectionResult;
    }
}
