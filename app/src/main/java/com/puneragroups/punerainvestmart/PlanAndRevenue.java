package com.puneragroups.punerainvestmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlanAndRevenue extends AppCompatActivity {

    ImageView close, ok;
    TextView plan, rupee;
    ProgressBar progressbar;
    FirebaseDatabase database;
    DatabaseReference mUserDatabase;
    String user_id;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_and_revenue);

        close = findViewById(R.id.close);
        ok = findViewById(R.id.ok);
        plan = findViewById(R.id.plan);
        rupee = findViewById(R.id.rupee);
        progressbar = findViewById(R.id.progressbar);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        Intent mIntent = getIntent();
        user_id = mIntent.getStringExtra("user_id");


        database = FirebaseDatabase.getInstance();

        mUserDatabase = database.getReference("Users").child(user_id);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressbar.setVisibility(View.VISIBLE);

                plan.setText(snapshot.child("plan").getValue().toString());
                rupee.setText(snapshot.child("revenue").getValue().toString());
                progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressbar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }
}