package com.dthealth.view.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dthealth.R;
import com.dthealth.service.dataSource.CurrentStatusDataSource;
import com.dthealth.service.factory.CurrentStatusViewModelFactory;
import com.dthealth.util.LineDataSetUtil;
import com.dthealth.viewmodel.CurrentStatusViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class CurrentStatusActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    protected Typeface tfLight;
    private LineChart chart;
    private static CurrentStatusViewModel currentStatusViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_current);
        if(currentStatusViewModel == null) {
            currentStatusViewModel = ViewModelProviders.of(this, new CurrentStatusViewModelFactory())
                    .get(CurrentStatusViewModel.class);
        }
        chart = findViewById(R.id.chart_current);
        chart.invalidate(); // refresh
        chart.setOnChartValueSelectedListener(this);
        // enable description text
        chart.getDescription().setEnabled(true);
        // enable touch gestures
        chart.setTouchEnabled(true);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
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
        leftAxis.setAxisMaximum(150);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        currentStatusViewModel.getSocketConnectionResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showSocketConnectionResult(s);
            }
        });

        currentStatusViewModel.getLineDataMutableLiveData().observe(this, new Observer<LineData>() {
            @Override
            public void onChanged(LineData lineData) {
                lineData.notifyDataChanged();
                // let the chart know it's data has changed
                chart.notifyDataSetChanged();

                // limit the number of visible entries
                chart.setVisibleXRangeMaximum(20);
                // chart.setVisibleYRange(30, AxisDependency.LEFT);

                // move to the latest entry
                chart.moveViewToX(lineData.getEntryCount());
            }
        });
        if (CurrentStatusDataSource.getCompositeDisposable()==null) {
            currentStatusViewModel.socketConnect();
            currentStatusViewModel.physicalIndexSubscribe(LineDataSetUtil.getInstance().getLineData());
        }
        chart.setData(LineDataSetUtil.getInstance().getLineData());
    }

    private void showSocketConnectionResult(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
