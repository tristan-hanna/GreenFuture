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

    //Creates a connection to the Firebase database and initializes the
    //reference to it
    FirebaseDatabase ConditionDatabase;
    DatabaseReference HumidityReference;

    //Initializes graph and series
    GraphView HumidityGraphView;
    LineGraphSeries HumiditySeries;

    //Initializes format for time
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_graph);

        //Formats graph
        HumidityGraphView = (GraphView) findViewById(R.id.HumidityGraphView);
        HumiditySeries = new LineGraphSeries();
        HumiditySeries.setDrawDataPoints(true);
        HumiditySeries.setDrawBackground(true);
        HumidityGraphView.setTitle("Humidity Graph");
        HumidityGraphView.addSeries(HumiditySeries);

        //Creates instance of database and connects to DHT node
        ConditionDatabase = FirebaseDatabase.getInstance();
        HumidityReference = ConditionDatabase.getReference("DHT");

        //Formats x axis
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

        //Populates graph with data from Firebase
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