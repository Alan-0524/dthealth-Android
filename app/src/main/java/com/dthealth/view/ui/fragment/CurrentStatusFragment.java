package com.dthealth.view.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dthealth.R;
import com.dthealth.background.BackgroundService;
import com.dthealth.model.PhysicalIndex;
import com.dthealth.util.LineDataSetUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import static com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTH_SIDED;
import static com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM_INSIDE;


public class CurrentStatusFragment extends Fragment implements OnChartValueSelectedListener {

    protected Typeface tfLight;
    private LineChart chart;

    public static CurrentStatusFragment newInstance() {
        return new CurrentStatusFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.current_status_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chart = getActivity().findViewById(R.id.chart_current);
        chart.invalidate(); // refresh
        chart.setOnChartValueSelectedListener(this);
        // enable description text
        chart.getDescription().setEnabled(true);
        // enable touch gestures
        chart.setTouchEnabled(true);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);
        // set an alternative background color
        chart.setBackgroundColor(Color.LTGRAY);
        // get the legend (only possible after setting data)
        Legend legend = chart.getLegend();

        // modify the legend ...
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTypeface(tfLight);
        legend.setTextColor(Color.BLUE);
        legend.setTextSize(12f);
        legend.setWordWrapEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTypeface(tfLight);
        xAxis.setTextColor(Color.BLUE);
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.BLUE);
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        BackgroundService.getPhysicalIndexMutableLiveData().observeForever(new Observer<PhysicalIndex>() {
            @Override
            public void onChanged(PhysicalIndex physicalIndex) {
                int count = LineDataSetUtil.getInstance().getLineData().getDataSetByIndex(0).getEntryCount();
                LineDataSetUtil.getInstance().getLineData().addEntry(new Entry(count, physicalIndex.getHeartbeatStrength()), 0);
                LineDataSetUtil.getInstance().getLineData().addEntry(new Entry(count, physicalIndex.getBloodPressure()), 1);
                LineDataSetUtil.getInstance().getLineData().addEntry(new Entry(count, physicalIndex.getBloodFat()), 2);
                LineDataSetUtil.getInstance().getLineData().addEntry(new Entry(count, physicalIndex.getBloodGlucose()), 3);
                LineDataSetUtil.getInstance().getLineData().addEntry(new Entry(count, physicalIndex.getTemperature()), 4);
                LineDataSetUtil.getInstance().getLineData().notifyDataChanged();
                chart.notifyDataSetChanged();
                chart.setVisibleXRangeMaximum(20);
                chart.moveViewToX(LineDataSetUtil.getInstance().getLineData().getEntryCount());
            }
        });
        chart.setData(LineDataSetUtil.getInstance().getLineData());
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
