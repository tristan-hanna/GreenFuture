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

public class MoistureGraph extends AppCompatActivity {

    FirebaseDatabase MoistureDatabase;
    DatabaseReference MoistureReference;

    GraphView MoistureGraphView;
    LineGraphSeries MoistureSeries;

    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        MoistureGraphView = (GraphView) findViewById(R.id.MoistureGraphView);
        MoistureSeries = new LineGraphSeries();
        MoistureGraphView.addSeries(MoistureSeries);

        MoistureDatabase = FirebaseDatabase.getInstance();
        MoistureReference = MoistureDatabase.getReference("PCF");

        MoistureGraphView.getGridLabelRenderer().setNumHorizontalLabels(3);
        MoistureGraphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
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

        MoistureReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;

                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    PointValues pointValues = myDataSnapshot.getValue(PointValues.class);
                    dp[index] = new DataPoint(pointValues.getM_time(), pointValues.getMoisture());
                    index++;
                }
                MoistureSeries.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}