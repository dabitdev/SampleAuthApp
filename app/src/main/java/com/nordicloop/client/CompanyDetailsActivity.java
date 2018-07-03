package com.nordicloop.client;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class CompanyDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details_activity);

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setData(generatePieData());

      BarChart barChart = findViewById(R.id.barChart);
          barChart.setData(generateBarData(6, 3.5f, 7 ));
    }

    /**
     * generates less data (1 DataSet, 4 values)
     */
    protected PieData generatePieData() {

        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Quarter " + (i+1)));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2017");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);

        return d;
    }

  protected BarData generateBarData(int dataSets, float range, int count) {

    ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();

    for(int i = 0; i < dataSets; i++) {

      ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

//            entries = FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "stacked_bars.txt");

      for(int j = 0; j < count; j++) {
        entries.add(new BarEntry(j, (float) (Math.random() * range) + range / 4));
      }

      BarDataSet ds = new BarDataSet(entries, getLabel(i));
      ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
      sets.add(ds);
    }

    BarData d = new BarData(sets);
    return d;
  }

  private String[] mLabels = new String[] { "January", "Feb", "March", "May", "June", "July" };
//    private String[] mXVals = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec" };

  private String getLabel(int i) {
    return mLabels[i];
  }

}