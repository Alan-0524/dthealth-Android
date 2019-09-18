package com.dthealth.service.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dthealth.service.dataSource.CurrentStatusDataSource;
import com.dthealth.service.repository.CurrentStatusRepository;
import com.dthealth.viewmodel.CurrentStatusViewModel;

public class CurrentStatusViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CurrentStatusViewModel.class)) {
            return (T) new CurrentStatusViewModel(CurrentStatusRepository.getInstance(new CurrentStatusDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
