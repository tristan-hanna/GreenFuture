package com.example.greenfuture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HumidityGraph extends AppCompatActivity {

    FirebaseDatabase ConditionDatabase;
    DatabaseReference HumidityReference;

    GraphView HumidityGraphView;
    LineGraphSeries HumiditySeries;

    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_graph);

        HumidityGraphView = (GraphView) findViewById(R.id.HumidityGraphView);
        HumiditySeries = new LineGraphSeries();
        HumiditySeries.setDrawDataPoints(true);
        HumiditySeries.setDrawBackground(true);
        //HumidityGraphView.getViewport().setYAxisBoundsManual(true);
        //HumidityGraphView.getViewport().setMinY(0);
        HumidityGraphView.addSeries(HumiditySeries);

        ConditionDatabase = FirebaseDatabase.getInstance();
        HumidityReference = ConditionDatabase.getReference("DHT");

        HumidityGraphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX){
                    return sdf.format(new Date((long) value));
                }
                else{
                    return super.formatLabel(value, isValueX);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        HumidityReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;

                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    PointValues pointValues = myDataSnapshot.getValue(PointValues.class);
                    dp[index] = new DataPoint(pointValues.getM_time(), pointValues.getHumidity());
                    index++;
                }
                HumiditySeries.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}