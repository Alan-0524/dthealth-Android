package com.dthealth.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dthealth.dataSource.DigitalUserModelDataSource;
import com.dthealth.repository.DigitalUserModelDataRepository;
import com.dthealth.viewmodel.DigitalUserModelDataViewModel;

public class DigitalUserModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DigitalUserModelDataViewModel.class)) {
            return (T) new DigitalUserModelDataViewModel(DigitalUserModelDataRepository.getInstance(new DigitalUserModelDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
