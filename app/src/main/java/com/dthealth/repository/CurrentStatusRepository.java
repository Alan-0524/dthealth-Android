package com.dthealth.repository;


import com.dthealth.callback.MessageResultInterface;
import com.dthealth.callback.PhysicalIndexResultInterface;
import com.dthealth.callback.SocketResultInterface;
import com.dthealth.dataSource.CurrentStatusDataSource;


public class CurrentStatusRepository {

    private static volatile CurrentStatusRepository instance;
    private CurrentStatusDataSource currentStatusDataSource;

    public CurrentStatusRepository(CurrentStatusDataSource currentStatusDataSource) {
        this.currentStatusDataSource = currentStatusDataSource;
    }

    public static CurrentStatusRepository getInstance(CurrentStatusDataSource currentStatusDataSource) {
        if (instance == null) {
            instance = new CurrentStatusRepository(currentStatusDataSource);
        }
        return instance;
    }

    public void physicalIndexSubscribe(PhysicalIndexResultInterface physicalIndexResultInterface) {
        currentStatusDataSource.physicalIndexSubscribe(physicalIndexResultInterface);
    }



}
