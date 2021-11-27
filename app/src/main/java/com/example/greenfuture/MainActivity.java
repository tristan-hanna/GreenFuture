package com.example.greenfuture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //Creates a connection to the Firebase database and attaches the
    //database references to it
    FirebaseDatabase ConditionDatabase;

    DatabaseReference SetMoistureReference;
    DatabaseReference SetTempReference;
    DatabaseReference SetHumidityReference;
    DatabaseReference SetPlantTime;

    //Initializes buttons that take the user to the different
    //graphs
    Button ToMoistureGraph;
    Button ToTemperatureGraph;
    Button ToHumidityGraph;

    //Initializes textviews that set the current readings from the
    //sensors
    TextView CurrentMoisture;
    TextView CurrentTemp;
    TextView CurrentHumidity;
    TextView PlantTime;

    //Initializes format for time
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConditionDatabase = FirebaseDatabase.getInstance();

        //Connects the references to specific points in the database
        SetMoistureReference = ConditionDatabase.getReference("Set_Moisture");
        SetTempReference = ConditionDatabase.getReference("Set_TH");
        SetHumidityReference = ConditionDatabase.getReference("Set_TH");
        SetPlantTime = ConditionDatabase.getReference("Set_Time");

        //Connects textviews
        CurrentMoisture = findViewById(R.id.CurrentMoisture);
        CurrentTemp = findViewById(R.id.CurrentTemp);
        CurrentHumidity = findViewById(R.id.CurrentHumidity);
        PlantTime = findViewById(R.id.PlantTime);

        //Sets buttons to take users to graphs
        ToMoistureGraph = findViewById(R.id.ToMoistureGraph);
        ToMoistureGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MoistureGraph.class);
                startActivity(intent);
            }
        });

        ToTemperatureGraph = findViewById(R.id.ToTemperatureGraph);
        ToTemperatureGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, TemperatureGraph.class);
                startActivity((intent2));
            }
        });

        ToHumidityGraph = findViewById(R.id.ToHumidityGraph);
        ToHumidityGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, HumidityGraph.class);
                startActivity(intent3);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Retrieves realtime data from database so that it could be passed to the
        //textviews
        SetMoistureReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double cur_moisture = (Double) dataSnapshot.child("moisture").getValue();
                CurrentMoisture.setText(Integer.toString((int) cur_moisture) + " g/m³");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SetTempReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long cur_temp = (Long) dataSnapshot.child("temperature").getValue();
                CurrentTemp.setText(Integer.toString((int) cur_temp) + "C°");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SetHumidityReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long cur_humidity = (Long) dataSnapshot.child("humidity").getValue();
                CurrentHumidity.setText(Integer.toString((int) cur_humidity) + "%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SetPlantTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long fire_time = (Long) dataSnapshot.child("plantTime").getValue();
                fire_time = fire_time * 1000;   //Converts time from python in seconds to milliseconds
                String cur_time = sdf.format(new Date((long) fire_time)); //Formats time as a String
                PlantTime.setText(cur_time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}