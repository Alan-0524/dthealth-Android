package com.dthealth.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.dthealth.view.ui.CurrentStatusActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class LineDataSetUtil{
    private static LineDataSetUtil instance;
    private static ILineDataSet heartbeatStrength;
    private static ILineDataSet bloodPressureSet;
    private static ILineDataSet bloodFatSet;
    private static ILineDataSet bloodGlucoseSet;
    private static ILineDataSet temperatureSet;
    private static LineData lineData = null;
    public static synchronized LineDataSetUtil getInstance() {
        if (instance == null) {
            instance = new LineDataSetUtil();
        }
        return instance;
    }

    public LineData getLineData(){
        if(lineData == null) {
            lineData = new LineData();
            lineData.setValueTextColor(Color.WHITE);
            heartbeatStrength = lineData.getDataSetByIndex(0);
            bloodPressureSet = lineData.getDataSetByIndex(1);
            bloodFatSet = lineData.getDataSetByIndex(2);
            bloodGlucoseSet = lineData.getDataSetByIndex(3);
            temperatureSet = lineData.getDataSetByIndex(4);
            if (heartbeatStrength == null) {
                heartbeatStrength = createSet("Heartbeat Strength", Color.RED);
                lineData.addDataSet(heartbeatStrength);
            }
            if (bloodPressureSet == null) {
                bloodPressureSet = createSet("Blood Pressure", Color.YELLOW);
                lineData.addDataSet(bloodPressureSet);
            }
            if (bloodFatSet == null) {
                bloodFatSet = createSet("Blood Glucose", Color.GREEN);
                lineData.addDataSet(bloodFatSet);
            }
            if (bloodGlucoseSet == null) {
                bloodGlucoseSet = createSet("Blood Fat", Color.BLACK);
                lineData.addDataSet(bloodGlucoseSet);
            }
            if (temperatureSet == null) {
                temperatureSet = createSet("Temperature", Color.MAGENTA);
                lineData.addDataSet(temperatureSet);
            }
        }
        return lineData;
    }

    private LineDataSet createSet(String type, int color) {
        LineDataSet set = new LineDataSet(null, type);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(color);
        set.setCircleColor(color);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(color);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(color);
        set.setValueTextSize(9f);
        set.setDrawValues(false);

        return set;
    }

}
