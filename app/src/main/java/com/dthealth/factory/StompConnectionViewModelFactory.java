package com.dthealth.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dthealth.dataSource.StompConnection;
import com.dthealth.repository.StompConnectionRepository;
import com.dthealth.viewmodel.StompConnectionViewModel;

public class StompConnectionViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StompConnectionViewModel.class)) {
            return (T) new StompConnectionViewModel(StompConnectionRepository.getInstance(new StompConnection()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
