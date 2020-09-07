package com.taotianhua.covidnews.ui.epidemic.graph;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.taotianhua.covidnews.R;
import com.taotianhua.covidnews.ui.epidemic.graph.GraphViewModel;

import java.util.ArrayList;
import java.util.List;

public class GraphFragment extends Fragment {

    private LineChart lineChart;
    private com.taotianhua.covidnews.ui.epidemic.graph.GraphViewModel graphViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        graphViewModel =
                ViewModelProviders.of(this).get(GraphViewModel.class);
        View root = inflater.inflate(R.layout.graph_fragment, container, false);

        graphViewModel = new ViewModelProvider(this).get(GraphViewModel.class);

        lineChart = (LineChart) root.findViewById(R.id.line_chart);
        setLineChartBasicFormat(lineChart);

        graphViewModel.getEpidemicData("China").observe(getViewLifecycleOwner(), epidemicData -> {
            Log.d("MyApp","StartSetting");
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//
            String region = epidemicData.getRegion();
            String begin = epidemicData.getBegin();
            List<Integer> confirmed = epidemicData.getConfirmed();
            List<Integer> cured = epidemicData.getCured();
            List<Integer> dead = epidemicData.getDead();
            List<String> labelStr = new ArrayList<>();
//
            List<Entry> confirmedEntries = new ArrayList<>();
            List<Entry> curedEntries = new ArrayList<>();
            List<Entry> deadEntries = new ArrayList<>();
            for(int i =0; i < confirmed.size(); ++i){
                labelStr.add("day" + i);
                confirmedEntries.add(new Entry(confirmed.get(i), i));
                curedEntries.add(new Entry(cured.get(i), i));
                deadEntries.add(new Entry(dead.get(i), i));
            }
//
            LineDataSet confirmedData = new LineDataSet(confirmedEntries, "确诊人数");
            confirmedData.setColor(Color.RED);
            confirmedData.setCircleColor(Color.RED);
            confirmedData.setLineWidth(3f);
            confirmedData.setCircleHoleRadius(.5f);

            LineDataSet curedData = new LineDataSet(curedEntries, "治愈人数");
            curedData.setLineWidth(3f);
            curedData.setColor(Color.BLUE);
            curedData.setCircleColor(Color.BLUE);

            LineDataSet deadData = new LineDataSet(deadEntries, "死亡人数");
            deadData.setColor(Color.BLACK);
            deadData.setCircleColor(Color.BLACK);
            deadData.setLineWidth(3f);

            dataSets.add(confirmedData);
            dataSets.add(curedData);
            dataSets.add(deadData);

            LineData mChartData = new LineData(labelStr, dataSets);

            lineChart.setDescription("开始统计日期： "+begin);

            lineChart.animateX(5000, Easing.EasingOption.EaseInOutSine);
            lineChart.setData(mChartData);
            Log.d("MyApp","here");
        });

        return root;
    }

    private void setLineChartBasicFormat(LineChart lineChart){
        lineChart.setStateDescription("这是description");
        lineChart.setNoDataText("正在读取数据");
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawZeroLine(true);
        yAxis.setZeroLineColor(0xFF9900);
        yAxis.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                int v = (int) value;
                if(v >= 1000000) {
                    return (value/1000000) + "百万人";
                }
                else if(v >= 10000){
                    return  (value/10000) + "万人";
                }
                return (int)value + "人";
            }
        });
    }
}