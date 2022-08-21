package com.example.simple_pocket_money_entry.chart;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.simple_pocket_money_entry.DBHelper;
import com.example.simple_pocket_money_entry.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ChartFragment extends Fragment implements View.OnClickListener {

    private String chartMonth;
    private TextView listMonthView, rollingView;
    PieChart pieChart;

    Calendar calendar = Calendar.getInstance();
    DateFormat monthFormat = new SimpleDateFormat("yyyy/MM", Locale.KOREA);

    private final int[] chartColor =
            {
                    Color.parseColor("#ffd28c"),
                    Color.parseColor("#8cebff"),
                    Color.parseColor("#eededf"),
                    Color.parseColor("#c1c9e0"),
                    Color.parseColor("#e2bce0"),
                    Color.parseColor("#d9ebe0"),
                    Color.parseColor("#e4e2ee"),
                    Color.parseColor("#fff78c"),
                    Color.parseColor("#c5ff8c"),
                    Color.parseColor("#ffd9e3"),
                    Color.parseColor("#e2e7f3"),
                    Color.parseColor("#ffead2"),
                    Color.parseColor("#afe9fc"),
                    Color.parseColor("#f6abab"),
                    Color.parseColor("#dddbe7"),
                    Color.parseColor("#d0cbfa")
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        ImageView leftButton = view.findViewById(R.id.change_month_left);
        ImageView rightButton = view.findViewById(R.id.change_month_right);
        listMonthView = view.findViewById(R.id.change_month_center);
        rollingView = view.findViewById(R.id.chart_rolling_text);
        pieChart = view.findViewById(R.id.pie_chart);

        Date date = new Date();
        chartMonth = monthFormat.format(date);
        listMonthView.setText(chartMonth);

        showChart(chartMonth);

        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.change_month_left:        // 이전 달 전환
                calendar.add (calendar.MONTH, - 1);
                chartMonth = monthFormat.format(calendar.getTime());
                listMonthView.setText(chartMonth);
                showChart(chartMonth);
                break;
            case R.id.change_month_right:       // 다음 달 전환
                calendar.add (calendar.MONTH, + 1);
                chartMonth = monthFormat.format(calendar.getTime());
                listMonthView.setText(chartMonth);
                showChart(chartMonth);
                break;
        }
    }

    private void showChart(String month) {
        ArrayList<PieEntry> dataValue = getData(month);
        PieDataSet dataSet = new PieDataSet(dataValue, "");       // 그래프에 들어갈 데이터셋

        dataSet.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setUsePercentValues(true);                                 // value 값을 백분율로 환산

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int c: chartColor) colors.add(c);
        dataSet.setColors(colors);                                          // 항목별 색상

        // 차트 밖으로 선 빼서 항목 표시
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        dataSet.setUsingSliceColorAsValueLineColor(true);
        dataSet.setValueLineVariableLength(true);

        dataSet.setValueLinePart1Length((float) 0.5);
        dataSet.setValueLinePart2Length(0);

        pieChart.setMinAngleForSlices(15f);                                    // 항목 사이에 거리두기
        pieChart.setExtraLeftOffset(25f);
        pieChart.setExtraRightOffset(25f);
        pieChart.getDescription().setEnabled(false);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(15f);                                         // 항목 비율 크기
        data.setValueTextColor(Color.BLACK);                                // 항목 비율 색상 (23%와 같은 비율)
        pieChart.setEntryLabelColor(Color.BLACK);

        // 차트 가운데 글
        if(getData(month).isEmpty()) {
            pieChart.setCenterText("내역이 없습니다.");
            rollingView.setVisibility(View.INVISIBLE);
        } else {
            pieChart.setCenterText("이달의\n 소비 분석");
            rollingView.setVisibility(View.VISIBLE);
        }
        pieChart.setCenterTextSize(20f);
        pieChart.setCenterTextColor(Color.parseColor("#104E82"));

        // 범례 크기 및 위치 설정
        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setDrawInside(false);
        legend.setTextSize(12);         // Should you wish to set the size
        legend.setFormSize(13);         // Should you wish to set the form size

        pieChart.setData(data);
        pieChart.animateY(1000);
        pieChart.invalidate();                                              // 회전 및 터치 효과 사라짐
    }

    private ArrayList<PieEntry> getData(String month) {
        ArrayList<PieEntry> dataValue = new ArrayList<PieEntry>();
        int foodSum = 0, cafeSum = 0, enterSum = 0, trafficSum = 0, eduSum = 0,
            petSum = 0, fashionSum = 0, necessitySum = 0, healthSum = 0, salarySum = 0, pocketSum = 0,
            savingSum = 0, utilitySum = 0, insuranceSum = 0, congSum = 0, etcSum = 0;

        DBHelper helper = new DBHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM myList ORDER BY full_date DESC",null);
        if(cursor!= null) {
            while(cursor.moveToNext()) {
                if((cursor.getString(3).contains(month)) && (cursor.getString(1).contains("지출"))) {
                    switch (cursor.getString(5)) {
                        case "식비":
                            foodSum = cursor.getInt(6) + foodSum;
                            break;
                        case "카페/간식":
                            cafeSum = cursor.getInt(6) + cafeSum;
                            break;
                        case "문화/오락":
                            enterSum = cursor.getInt(6) + enterSum;
                            break;
                        case "교통":
                            trafficSum = cursor.getInt(6) + trafficSum;
                            break;
                        case "교육":
                            eduSum = cursor.getInt(6) + eduSum;
                            break;
                        case "저축":
                            savingSum = cursor.getInt(6) + savingSum;
                            break;
                        case "반려동물":
                            petSum = cursor.getInt(6) + petSum;
                            break;
                        case "패션/미용":
                            fashionSum = cursor.getInt(6) + fashionSum;
                            break;
                        case "생필품":
                            necessitySum = cursor.getInt(6) + necessitySum;
                            break;
                        case "건강/병원":
                            healthSum = cursor.getInt(6) + healthSum;
                            break;
                        case "월급":
                            salarySum = cursor.getInt(6) + salarySum;
                            break;
                        case "용돈":
                            pocketSum = cursor.getInt(6) + pocketSum;
                            break;
                        case "공과금":
                            utilitySum = cursor.getInt(6) + utilitySum;
                            break;
                        case "보험":
                            insuranceSum = cursor.getInt(6) + insuranceSum;
                            break;
                        case "경조사비":
                            congSum = cursor.getInt(6) + congSum;
                            break;
                        case "기타":
                            etcSum = cursor.getInt(6) + etcSum;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + cursor.getString(5));
                    }
                }
            }
        }

        if(foodSum != 0) {
            dataValue.add(new PieEntry(foodSum, "식비"));
        }
        if(cafeSum != 0) {
            dataValue.add(new PieEntry(cafeSum, "카페/간식"));
        }
        if(enterSum != 0) {
            dataValue.add(new PieEntry(enterSum, "문화/오락"));
        }
        if(trafficSum != 0) {
            dataValue.add(new PieEntry(trafficSum, "교통"));
        }
        if(eduSum != 0) {
            dataValue.add(new PieEntry(eduSum, "교육"));
        }
        if(salarySum != 0) {
            dataValue.add(new PieEntry(savingSum, "저축"));
        }
        if(petSum != 0) {
            dataValue.add(new PieEntry(petSum, "반려동물"));
        }
        if(fashionSum != 0) {
            dataValue.add(new PieEntry(fashionSum, "패션/미용"));
        }
        if(necessitySum != 0) {
            dataValue.add(new PieEntry(necessitySum, "생필품"));
        }
        if(healthSum != 0) {
            dataValue.add(new PieEntry(healthSum, "건강/병원"));
        }
        if(utilitySum != 0) {
            dataValue.add(new PieEntry(utilitySum, "공과금"));
        }
        if(insuranceSum != 0) {
            dataValue.add(new PieEntry(insuranceSum, "보험"));
        }
        if(congSum != 0) {
            dataValue.add(new PieEntry(congSum, "경조사비"));
        }
        if(etcSum != 0) {
            dataValue.add(new PieEntry(etcSum, "기타"));
        }

        return dataValue;
    }
}