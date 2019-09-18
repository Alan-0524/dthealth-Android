package com.dthealth.util;

import android.annotation.SuppressLint;
import android.util.Log;

import com.dthealth.service.model.DetectResult;
import com.dthealth.service.model.DetectResultItem;
import com.dthealth.service.model.DigitalUserModel;
import com.dthealth.service.model.PhysicalIndex;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhysicalIndexListener {
    private static volatile PhysicalIndexListener physicalIndexListener;
    private static List<DetectResultItem> list = new ArrayList<>();
    private static DigitalUserModel digitalUserModel = new DigitalUserModel();
    private static Field[] field = PhysicalIndex.class.getDeclaredFields();
    private static DetectResultItem detectResultItem = null;
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private HeartbeatCalculator heartbeatCalculator = new HeartbeatCalculator();

    public static synchronized PhysicalIndexListener getInstance() {
        if (physicalIndexListener == null) {
            physicalIndexListener = new PhysicalIndexListener();
            return physicalIndexListener;
        }
        return physicalIndexListener;
    }

    public DetectResult detectAbnormalStatus(PhysicalIndex physicalIndex) {
        list.clear();
        try {
            for (Field item : field) {
                String indexName = item.getName();
                indexName = upperCase(indexName);
                Method method = physicalIndex.getClass().getMethod("get" + indexName);
                float value = (float) method.invoke(physicalIndex);
                detectResultItem = catchAbnormalStatus(indexName, value);
                if (detectResultItem != null) {
                    list.add(detectResultItem);
                }
            }
            detectResultItem = catchAbnormalStatus("Heartbeat", heartbeatCalculator.calculate());
            if (detectResultItem != null) {
                list.add(detectResultItem);
            }
            return new DetectResult("5d53b73492f6e331bc118715", getCurrentTime(), list);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Log.e("detectAbnormalStatus", e.getMessage());
        }
        return null;
    }

    private DetectResultItem catchAbnormalStatus(String indexName, float value) {
        try {
            // TODO: 9/16/2019 登陆后给 DigitalUserModel 更新赋值

            Method getMin = digitalUserModel.getClass().getMethod("getMin" + indexName);
            Method getMax = digitalUserModel.getClass().getMethod("getMax" + indexName);
            float minValue = (float) getMin.invoke(digitalUserModel);
            float maxValue = (float) getMax.invoke(digitalUserModel);
            if (!(minValue <= value && value <= maxValue)) {
                return new DetectResultItem(indexName, String.valueOf(value), String.valueOf(minValue), String.valueOf(maxValue));
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Log.e("catchNormalStatus", e.getMessage());
        }
        return null;
    }


    private String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    private String getCurrentTime() {
        return format.format(new Date().getTime());
    }

}
