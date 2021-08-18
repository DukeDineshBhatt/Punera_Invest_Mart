package com.puneragroups.punerainvestmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class OTPLogin extends AppCompatActivity {

    ProgressBar progressBar;
    Button btncnt;
    int otp;
    String newToken;
    EditText editTextOtp;
    String password, username, editOtp, mobile;
    private PreferenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_login);

        prefManager = new PreferenceManager(this);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        btncnt = (Button) findViewById(R.id.buttonContinue);
        editTextOtp = (EditText) findViewById(R.id.et_otp);

        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference usersRef = database.getReference("Users");

        Intent mIntent = getIntent();
        otp = mIntent.getIntExtra("OTP", 0);
        mobile = mIntent.getStringExtra("mobile");

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(OTPLogin.this, instanceIdResult -> {
            newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
        });

        Toast.makeText(this, ""+otp, Toast.LENGTH_LONG).show();

        btncnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                editOtp = editTextOtp.getText().toString();

                if (editOtp.isEmpty()) {

                    progressBar.setVisibility(View.GONE);
                    editTextOtp.setError("Enter valid OTP");
                    editTextOtp.requestFocus();

                } else if (newToken.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Something wrong. Try Again", Toast.LENGTH_LONG).show();

                } else {


                    if (otp == Integer.valueOf(editTextOtp.getText().toString())) {

                        SharedPreferences mPrefs = getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPrefs.edit();
                        editor.putString("user_id", mobile);
                        editor.putString("token", newToken);
                        editor.putBoolean("is_logged_before", true); //this line will do trick
                        editor.commit();

                        prefManager.setFirstTimeLaunch(false);
                        Toast.makeText(OTPLogin.this, "success.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OTPLogin.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();

                        usersRef.child(mobile).child("token").setValue(newToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    prefManager.setFirstTimeLaunch(false);
                                    Toast.makeText(OTPLogin.this, "success.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(OTPLogin.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();

                                } else {

                                    Toast.makeText(OTPLogin.this, "Something wrong.Try again.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "wrong OTP", Toast.LENGTH_LONG).show();
                    }


                }


            }
        });

    }
}