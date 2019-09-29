package com.dthealth.util;

import io.reactivex.disposables.CompositeDisposable;

public class CompositeDisposableUtil {
    private static CompositeDisposableUtil compositeDisposableUtil;
    private static volatile CompositeDisposable compositeDisposable;

    public static void initCompositeDisposable(){
        if(compositeDisposableUtil == null){
            compositeDisposableUtil = new CompositeDisposableUtil();
            compositeDisposable = new CompositeDisposable();
        }
    }

    public static CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public static void renewCompositeDisposable(){
        compositeDisposable = new CompositeDisposable();
    }
}
