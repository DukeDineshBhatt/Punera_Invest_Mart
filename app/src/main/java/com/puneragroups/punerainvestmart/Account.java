package com.puneragroups.punerainvestmart;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends BaseActivity {

    ImageView image, edit,edit1,edit2;
    Button logout;
    String user_id;
    TextView name, mobile, alt_mobile, email,area,city,house_no,pincode,state;
    ProgressBar progressbar;
    private final int PICK_IMAGE_REQUEST = 22;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private Uri filePath;
    DatabaseReference usersRef;
    FirebaseDatabase database;
    private PreferenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        logout = findViewById(R.id.logout);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        alt_mobile = findViewById(R.id.alt_mobile);
        email = findViewById(R.id.email);
        progressbar = findViewById(R.id.progressbar);
        //edit = findViewById(R.id.edit);
        //edit1 = findViewById(R.id.edit1);
        //edit2 = findViewById(R.id.edit2);
        area = findViewById(R.id.area);
        house_no = findViewById(R.id.house_no);
        city = findViewById(R.id.city);
        pincode = findViewById(R.id.pincode);
        state = findViewById(R.id.state);

        SharedPreferences shared = getSharedPreferences("myAppPrefs", MODE_PRIVATE);
        user_id = (shared.getString("user_id", ""));

        prefManager = new PreferenceManager(this);

        mobile.setText(user_id);
        progressbar.setVisibility(View.VISIBLE);

        database  = FirebaseDatabase.getInstance();

        usersRef  = database.getReference("Users");

        usersRef.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               /* if (dataSnapshot.hasChild("image")) {

                    Glide.with(getApplicationContext()).
                            load(dataSnapshot.child("image").getValue().toString())
                            .placeholder(R.drawable.avtar)
                            .circleCrop()
                            .into(image);
                } else {
                    Glide.with(getApplicationContext()).
                            load(R.drawable.avtar)
                            .circleCrop()
                            .into(image);

                }*/

                if (dataSnapshot.hasChild("name")) {

                    name.setText(dataSnapshot.child("name").getValue().toString());
                } else {

                    name.setText(" ");
                }
                if (dataSnapshot.hasChild("alt_number")) {

                    alt_mobile.setText(dataSnapshot.child("alt_number").getValue().toString());
                } else {

                    alt_mobile.setText(" ");
                }
                if (dataSnapshot.hasChild("email")) {

                    email.setText(dataSnapshot.child("email").getValue().toString());
                } else {

                    email.setText(" ");
                }

                area.setText(dataSnapshot.child("Address").child("area").getValue().toString());
                house_no.setText(dataSnapshot.child("Address").child("house_no").getValue().toString());
                city.setText(dataSnapshot.child("Address").child("city").getValue().toString());
                pincode.setText(dataSnapshot.child("Address").child("pincode").getValue().toString());
                state.setText(dataSnapshot.child("Address").child("state").getValue().toString());


                progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressbar.setVisibility(View.GONE);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Account.this)
                        .setTitle("Sign Out")
                        .setMessage("are you sure want to logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                Account.super.onBackPressed();

                                prefManager.setFirstTimeLaunch(true);

                                SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("hasLoggedIn", false);


                                SharedPreferences mPrefs = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = mPrefs.edit();
                                editor1.remove("user_id");

                                editor.commit();
                                editor1.commit();


                                Intent intent = new Intent(Account.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }).create().show();


            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Account.this, MainActivity.class);
        startActivity(intent);
        finish();


    }

    @Override
    int getContentViewId() {
        return R.layout.activity_account;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.acount;
    }
}