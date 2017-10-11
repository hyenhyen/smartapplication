package com.monad.kpu.Activty;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.monad.kpu.HttpAsync;
import com.monad.kpu.Model.ResultScoreModel;
import com.monad.kpu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ResultActivity extends AppCompatActivity {
    private PieChart pieChart, pieChart2;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView result_2015_hight, result_2015_average, result_2015_low, result_2016_hight,
            result_2016_average, result_2016_low, result_2017_hight, result_2017_average, result_2017_low;
    private TextView result_2015_hight_difference, result_2015_average_difference, result_2015_low_difference,
            result_2016_hight_difference, result_2016_average_difference, result_2016_low_difference,
            result_2017_hight_difference, result_2017_average_difference, result_2017_low_difference;
    private TextView result_2015_rank, result_2016_rank, result_2017_rank;

    private TextView tvHeader;
    private TextView desc2015, desc2016, desc2017;

    private String major;
    private float score;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_result);

        major = getIntent().getExtras().getString("major");
        type = getIntent().getExtras().getString("type");
        score = getIntent().getExtras().getFloat("score");

        init();
        setPieChart();
        setPieChart2();
    }

    private void init() {
        findLayout();
        network();
    }


    private void network() {

        RequestParams params = new RequestParams();
        params.put("score", score);
        params.put("major", major);
        params.put("type", type);

        HttpAsync.get("result_detail.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /*json으로 다 받아왔으면 2016-2014년도의 결과 까지 상세하게 보여주는 부분*/

                if (statusCode == 200) {
                    try {
                        JSONArray arr = response.getJSONArray("result");
                        for(int i = 0 ; i < arr.length(); i++) {
                            JSONObject v = arr.getJSONObject(i);
                            if(v.getString("year").equals("2014")) {
                                desc2015.setText("모집인원 : " + v.getString("recruit") + " 경쟁률 : " + v.getString("compete"));

                                result_2015_rank.setText(v.getString("grade"));

                                result_2015_hight.setText(v.getString("last_max"));
                                result_2015_average.setText(v.getString("last_avr"));
                                result_2015_low.setText(v.getString("last_min"));

                                result_2015_hight_difference.setText(String.format("%.1f",
                                        score - Float.parseFloat(v.getString("last_max"))));

                                result_2015_average_difference.setText(String.format("%.1f",
                                        score - Float.parseFloat(v.getString("last_avr"))));

                                result_2015_low_difference.setText(String.format("%.1f",
                                        score - Float.parseFloat(v.getString("last_min"))));

                            } else if(v.getString("year").equals("2015")) {
                                desc2016.setText("모집인원 : " + v.getString("recruit") + " 경쟁률 : " + v.getString("compete"));

                                result_2016_rank.setText(v.getString("grade"));

                                result_2016_hight.setText(v.getString("last_max"));
                                result_2016_average.setText(v.getString("last_avr"));
                                result_2016_low.setText(v.getString("last_min"));

                                result_2016_hight_difference.setText(String.format("%.1f",
                                        score - Float.parseFloat(v.getString("last_max"))));

                                result_2016_average_difference.setText(String.format("%.1f",
                                        score - Float.parseFloat(v.getString("last_avr"))));

                                result_2016_low_difference.setText(String.format("%.1f",
                                        score - Float.parseFloat(v.getString("last_min"))));

                            } else if(v.getString("year").equals("2016")) {
                                desc2017.setText("모집인원 : " + v.getString("recruit") + " 경쟁률 : " + v.getString("compete"));

                                result_2017_rank.setText(v.getString("grade"));

                                result_2017_hight.setText(v.getString("last_max"));
                                result_2017_average.setText(v.getString("last_avr"));
                                result_2017_low.setText(v.getString("last_min"));

                                result_2017_hight_difference.setText(String.format("%.1f",
                                        score - Float.parseFloat(v.getString("last_max"))));

                                result_2017_average_difference.setText(String.format("%.1f",
                                        score - Float.parseFloat(v.getString("last_avr"))));

                                result_2017_low_difference.setText(String.format("%.1f",
                                        score - Float.parseFloat(v.getString("last_min"))));
                            }
                        }
                    }catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                // 에러 코드별로 잘 코딩
                Toast.makeText(ResultActivity.this, "데이터를 받아오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findLayout() {
        pieChart = (PieChart) findViewById(R.id.chart);
        pieChart2 = (PieChart) findViewById(R.id.chart2);

        tvHeader = (TextView) findViewById(R.id.tvHeader);

        desc2015 = (TextView) findViewById(R.id.desc2015);
        desc2016 = (TextView) findViewById(R.id.desc2016);
        desc2017 = (TextView) findViewById(R.id.desc2017);

        result_2015_rank = (TextView) findViewById(R.id.result_2015_rank);
        result_2016_rank = (TextView) findViewById(R.id.result_2016_rank);
        result_2017_rank = (TextView) findViewById(R.id.result_2017_rank);

        result_2015_hight = (TextView) findViewById(R.id.result_2015_hight);
        result_2015_average = (TextView) findViewById(R.id.result_2015_average);
        result_2015_low = (TextView) findViewById(R.id.result_2015_low);
        result_2016_hight = (TextView) findViewById(R.id.result_2016_hight);
        result_2016_average = (TextView) findViewById(R.id.result_2016_average);
        result_2016_low = (TextView) findViewById(R.id.result_2016_low);
        result_2017_hight = (TextView) findViewById(R.id.result_2017_hight);
        result_2017_average = (TextView) findViewById(R.id.result_2017_average);
        result_2017_low = (TextView) findViewById(R.id.result_2017_low);

        result_2015_hight_difference = (TextView) findViewById(R.id.result_2015_hight_difference);
        result_2015_average_difference = (TextView) findViewById(R.id.result_2015_average_difference);
        result_2015_low_difference = (TextView) findViewById(R.id.result_2015_low_difference);
        result_2016_hight_difference = (TextView) findViewById(R.id.result_2016_hight_difference);
        result_2016_average_difference = (TextView) findViewById(R.id.result_2016_average_difference);
        result_2016_low_difference = (TextView) findViewById(R.id.result_2016_low_difference);
        result_2017_hight_difference = (TextView) findViewById(R.id.result_2017_hight_difference);
        result_2017_average_difference = (TextView) findViewById(R.id.result_2017_average_difference);
        result_2017_low_difference = (TextView) findViewById(R.id.result_2017_low_difference);

        tvHeader.setText("전년도 입시결과 [" + type + "] " + major);
    }

    private void setPieChart() { //piechart로 국어 영어 수학 탐구 수능반영비율을 확인하는 부분
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);

        List<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(8f, "수능"));


        PieDataSet dataSet = new PieDataSet(yvalues, "수능 반영 비율");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("수능");

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    private void setPieChart2() {
        pieChart2.setUsePercentValues(true);
        pieChart2.getDescription().setEnabled(true);
        List<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(12f, "국어"));
        yvalues.add(new PieEntry(15f, "영어"));
        yvalues.add(new PieEntry(12f, "수학"));
        yvalues.add(new PieEntry(12f, "탐구"));
        PieDataSet dataSet = new PieDataSet(yvalues, "과목 반영 비율");
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart2.setData(data);

        // undo all highlights
        pieChart2.highlightValues(null);

        pieChart2.invalidate();
    }
}
