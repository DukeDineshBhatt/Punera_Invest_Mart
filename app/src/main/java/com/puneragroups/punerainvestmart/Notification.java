package com.puneragroups.punerainvestmart;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Notification extends BaseActivity {

    LinearLayout empty_layout;
    private DatabaseReference mNotificationDatabase;
    private LinearLayoutManager linearLayoutManager;
    String user_id;
    private RecyclerView recyclerView;
    private myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        empty_layout = findViewById(R.id.empty_layout);
        recyclerView = (RecyclerView) findViewById(R.id.upload_list);

        SharedPreferences shared = getSharedPreferences("myAppPrefs", MODE_PRIVATE);
        user_id = (shared.getString("user_id", ""));

        mNotificationDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).child("Notifications");

        mNotificationDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.hasChildren()) {

                    empty_layout.setVisibility(View.GONE);

                    linearLayoutManager = new LinearLayoutManager(Notification.this);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    FirebaseRecyclerOptions<ListSetGet1> options =
                            new FirebaseRecyclerOptions.Builder<ListSetGet1>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).child("Notifications"), ListSetGet1.class)
                                    .build();


                    adapter = new myadapter(options);
                    adapter.startListening();
                    recyclerView.setAdapter(adapter);

                } else {

                    empty_layout.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Notification.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_notification;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.notification;
    }

    public class myadapter extends FirebaseRecyclerAdapter<ListSetGet1, myadapter.myviewholder> {
        public myadapter(@NonNull FirebaseRecyclerOptions<ListSetGet1> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull myadapter.myviewholder holder, final int position, @NonNull ListSetGet1 model) {

            final String Id = getRef(position).getKey();

            mNotificationDatabase.child(Id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    holder.txt.setText(snapshot.child("text").getValue().toString());

                    Date c = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);

                    if (snapshot.child("date").getValue().toString().equals(formattedDate)) {

                        holder.date.setText("Today");

                    } else {

                        holder.date.setText(snapshot.child("date").getValue().toString());
                    }


                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   /* Intent intent = new Intent(CustomersListActivity.this, CustomerDetails.class);
                    intent.putExtra("user_id", Id);
                    intent.putExtra("plan_name", plan_name);
                    startActivity(intent);*/

                }
            });
        }

        @NonNull
        @Override
        public myadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticfication_list, parent, false);
            return new myadapter.myviewholder(view);
        }

        class myviewholder extends RecyclerView.ViewHolder {

            TextView txt, date;

            public myviewholder(@NonNull View itemView) {
                super(itemView);

                txt = (TextView) itemView.findViewById(R.id.txt);
                date = (TextView) itemView.findViewById(R.id.date);


            }
        }
    }
}