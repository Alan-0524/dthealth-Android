package com.dthealth.view.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dthealth.R;
import com.dthealth.service.model.PhysicalIndex;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;

public class ChartCurrentActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    protected Typeface tfRegular;
    protected Typeface tfLight;
    private LineChart chart;
    protected int type;
    private HttpURLConnection connection = null;
    private BufferedReader reader = null;
    private String urlAddress = "";
    private String line;
    private Thread thread;
    private Gson gson = new Gson();
    private PhysicalIndex physicalIndex;
    private LineData lineData;
    private ILineDataSet set0;
    private ILineDataSet set1;
    private ILineDataSet set2;
    private ILineDataSet set3;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_current);
        chart = (LineChart) findViewById(R.id.chart_current);
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

        LineData data0 = new LineData();
        data0.setValueTextColor(Color.WHITE);
        LineData data1 = new LineData();
        data1.setValueTextColor(Color.WHITE);
        LineData data2 = new LineData();
        data2.setValueTextColor(Color.WHITE);
        LineData data3 = new LineData();
        data3.setValueTextColor(Color.WHITE);
        // add empty data
        chart.setData(data0);
        chart.setData(data1);
        chart.setData(data2);
        chart.setData(data3);

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
//        xAxis.setGranularity(1f);
//        xAxis.setValueFormatter(new ValueFormatter() {
//
//            private final SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
//
//            @Override
//            public String getFormattedValue(float value) {
////                Log.i("value-----------",String.valueOf(value));
////                long millis = TimeUnit.SECONDS.toMillis((long)value);
//                long millis = (long) value*100000000;
//                Log.i("value-----------",String.valueOf(millis));
//                return mFormat.format(new Date(millis));
//            }
//        });


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.BLUE);
        leftAxis.setAxisMaximum(150);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        line = "";
        type = getIntent().getIntExtra("type", 0);
        urlAddress = initConnection(type);
        thread = null;
        initThread();
    }

    private String initConnection(int type) {
        String url = "http://172.28.105.58:8082/currentState/";
        switch (type) {
            case 0:
                url = url + "loadingCurrentHeartbeat/5d53b73492f6e331bc118715";
                break;
            case 1:
                url = url + "loadingCurrentIndex/5d53b73492f6e331bc118715";
                break;
        }
        return url;
    }


    private void addEntry(String value) {
        if(!value.equals("") && value.startsWith("data:")){
            value = value.replace("data:", "");
            physicalIndex = readMessage(value);
            lineData= chart.getData();
            if (lineData != null) {
                if (type == 0) {
                    set0 = lineData.getDataSetByIndex(0);
                    // set.addEntry(...); // can be called as well
                    if (set0 == null) {
                        set0 = createSet("Heartbeat",Color.RED);
                        lineData.addDataSet(set0);
                    }
                    lineData.addEntry(new Entry(set0.getEntryCount(), physicalIndex.getHeartbeatStrength()), 0);
                } else {
                    set0 = lineData.getDataSetByIndex(0);
                    set1 = lineData.getDataSetByIndex(1);
                    set2 = lineData.getDataSetByIndex(2);
                    set3 = lineData.getDataSetByIndex(3);
                    // set.addEntry(...); // can be called as well
                    if (set0 == null) {
                        set0 = createSet("Blood Pressure",Color.RED);
                        lineData.addDataSet(set0);
                    }
                    if (set1 == null) {
                        set1 = createSet("Blood Glucose",Color.GREEN);
                        lineData.addDataSet(set1);
                    }
                    if (set2 == null) {
                        set2 = createSet("Blood Fat",Color.BLACK);
                        lineData.addDataSet(set2);
                    }
                    if (set3 == null) {
                        set3 = createSet("Temperature",Color.MAGENTA);
                        lineData.addDataSet(set3);
                    }

                    lineData.addEntry(new Entry(set0.getEntryCount(), physicalIndex.getBloodPressure()), 0);
                    lineData.addEntry(new Entry(set1.getEntryCount(), physicalIndex.getBloodGlucose()), 1);
                    lineData.addEntry(new Entry(set2.getEntryCount(), physicalIndex.getBloodFat()), 2);
                    lineData.addEntry(new Entry(set3.getEntryCount(), physicalIndex.getTemperature()), 3);
                }
                lineData.notifyDataChanged();
                // let the chart know it's data has changed
                chart.notifyDataSetChanged();

                // limit the number of visible entries
                chart.setVisibleXRangeMaximum(20);
                // chart.setVisibleYRange(30, AxisDependency.LEFT);

                // move to the latest entry
                chart.moveViewToX(lineData.getEntryCount());
                // this automatically refreshes the chart (calls invalidate())
                // chart.moveViewTo(data.getXValCount()-7, 55f,
                // AxisDependency.LEFT);
            }
        }
    }

    private LineDataSet createSet(String type,int color) {
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


    public void initThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlAddress);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    String token = "";
                    connection.setRequestProperty("token", token);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    line = reader.readLine();
                    while ((line = reader.readLine()) != null) {
                        // Don't generate garbage runnable inside the loop.
                        if (thread.isInterrupted()) {
                            reader.close();
                            connection.disconnect();
                            break;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addEntry(line);
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    Log.e("MalformedURLException", Objects.requireNonNull(e.getMessage()));
                } catch (ProtocolException e) {
                    Log.e("ProtocolException", Objects.requireNonNull(e.getMessage()));
                } catch (IOException e) {
                    Log.e("IOException", Objects.requireNonNull(e.getMessage()));
                }finally {
                    closeThread();
                }
            }
        });
        thread.start();
    }

    public PhysicalIndex readMessage(String data) {
        return gson.fromJson(data, PhysicalIndex.class);
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

    private void closeThread() {
        if (thread != null) {
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }
    }

    @Override
    protected void onPause() {
        closeThread();
        super.onPause();
    }
}
