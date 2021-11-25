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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase MoistureDatabase;
    DatabaseReference MoistureReference;
    DatabaseReference dbref;

    Button ToMoistureGraph;
    TextView CurrentMoisture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoistureDatabase = FirebaseDatabase.getInstance();
        MoistureReference = MoistureDatabase.getReference("PCF");

        dbref = FirebaseDatabase.getInstance().getReference();

        CurrentMoisture = findViewById(R.id.CurrentMoisture);

        ToMoistureGraph = findViewById(R.id.ToMoistureGraph);
        ToMoistureGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MoistureGraph.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//
//        Query lastQuery = dbref.child("mp").orderByKey().limitToLast(1);
//        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//             @Override
//             public void onDataChange(DataSnapshot dataSnapshot) {
//                 double value = dataSnapshot.child("moisture").getValue(Double.class);
//                 CurrentMoisture.setText(Double.toString((int) value));
//             }
//
//             @Override
//             public void onCancelled(@NonNull DatabaseError databaseError) {
//
//             }
//         });

//        MoistureReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //double value = dataSnapshot.getValue(Double.class);
//                //CurrentMoisture.setText(Integer.toString((int) value));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}