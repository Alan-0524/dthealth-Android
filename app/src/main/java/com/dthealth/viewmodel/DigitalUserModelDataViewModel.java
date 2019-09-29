package com.dthealth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dthealth.model.DigitalUserModel;
import com.dthealth.repository.DigitalUserModelDataRepository;

public class DigitalUserModelDataViewModel extends ViewModel {
    private MutableLiveData<DigitalUserModel> modelMutableLiveData = new MutableLiveData<>();
    private DigitalUserModelDataRepository digitalUserModelDataRepository;

    public DigitalUserModelDataViewModel(DigitalUserModelDataRepository digitalUserModelDataRepository) {
        this.digitalUserModelDataRepository = digitalUserModelDataRepository;
    }

    public LiveData<DigitalUserModel> findDigitalUserModelByUserId(String userId){
        DigitalUserModel digitalUserModel = digitalUserModelDataRepository.findDigitalUserModelByUserId(userId);
        modelMutableLiveData.postValue(digitalUserModel);
        return modelMutableLiveData;
    }
}
