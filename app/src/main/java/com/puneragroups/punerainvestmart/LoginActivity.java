package com.puneragroups.punerainvestmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    ProgressBar progressbar;
    int flags;
    EditText editTextMobile;
    Button btn_continue;
    int randomNumber;
    String base;
    DatabaseReference mOtpdatabase, mUserDatabase;
    String sender_id, message, authorization;
    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextMobile = findViewById(R.id.mobile);
        btn_continue = findViewById(R.id.buttonContinue);
        progressbar = findViewById(R.id.progressbar);

        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        mUserDatabase = database.getReference("Users");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /*mOtpdatabase = database.getReference("Admin").child("OTP");


        mOtpdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                sender_id = dataSnapshot.child("sender_id").getValue().toString();
                message = dataSnapshot.child("message").getValue().toString();
                authorization = dataSnapshot.child("authorization").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressbar.setVisibility(View.VISIBLE);

                mobile = editTextMobile.getText().toString().trim();

                if (mobile.isEmpty() || mobile.length() < 10) {

                    progressbar.setVisibility(View.GONE);
                    editTextMobile.setError("Enter a valid mobile number");
                    editTextMobile.requestFocus();
                    return;

                } else {

                    mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if (dataSnapshot.hasChild(mobile)) {

                                Random random = new Random();
                                randomNumber = random.nextInt(99999);

                                Intent intent = new Intent(LoginActivity.this, OTPLogin.class);
                                intent.putExtra("OTP", randomNumber);
                                intent.putExtra("mobile", mobile);
                                startActivity(intent);

                                /*Bean b = (Bean) getApplicationContext();

                                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                                logging.level(HttpLoggingInterceptor.Level.HEADERS);
                                logging.level(HttpLoggingInterceptor.Level.BODY);

                                OkHttpClient client = new OkHttpClient.Builder().writeTimeout(1000, TimeUnit.SECONDS).readTimeout(1000, TimeUnit.SECONDS).connectTimeout(1000, TimeUnit.SECONDS).addInterceptor(logging).build();

                                base = b.baseurl;

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .client(client)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                Call<OtpBean> call = cr.getOtp(sender_id, "english", "qt", editTextMobile.getText().toString(),
                                        message, "{#AA#}", String.valueOf(randomNumber), authorization);
                                call.enqueue(new Callback<OtpBean>() {
                                    @Override
                                    public void onResponse(@NotNull Call<OtpBean> call, @NotNull Response<OtpBean> response) {

                                        if (response.body().getMessage().get(0).equals("Message sent successfully")) {


                                            Intent intent = new Intent(Login.this, OTPLogin.class);
                                            intent.putExtra("OTP", randomNumber);
                                            intent.putExtra("mobile", mobile);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(Login.this, "Please try again", Toast.LENGTH_SHORT).show();

                                        }

                                        progressbar.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onFailure(Call<OtpBean> call, Throwable t) {
                                        progressbar.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();

                                    }
                                });*/


                            } else {

                                Toast.makeText(LoginActivity.this, "This Number is not Registered. ", Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(View.GONE);


                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            progressbar.setVisibility(View.GONE);
                        }
                    });

                }


            }
        });

    }
}