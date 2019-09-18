package com.dthealth.service.repository;

import com.dthealth.service.dataSource.DigitalUserModelDataSource;
import com.dthealth.service.model.DigitalUserModel;

public class DigitalUserModelDataRepository {
    private static DigitalUserModelDataRepository instance;
    private DigitalUserModelDataSource digitalUserModelDataSource;

    public static DigitalUserModelDataRepository getInstance(DigitalUserModelDataSource digitalUserModelDataSource) {
        if(instance == null){
            instance = new DigitalUserModelDataRepository(digitalUserModelDataSource);
        }
        return instance;
    }

    public DigitalUserModelDataRepository(DigitalUserModelDataSource digitalUserModelDataSource) {
        this.digitalUserModelDataSource = digitalUserModelDataSource;
    }

    public DigitalUserModel findDigitalUserModelByUserId(String userId){
        return digitalUserModelDataSource.findDigitalUserModelByUserId(userId);
    }
}
