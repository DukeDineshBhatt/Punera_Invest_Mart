package com.puneragroups.punerainvestmart;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class MainActivity extends BaseActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String SELECTED_ITEM = "arg_selected_item";
    int mSelectedItem;
    String user_id;
    FirebaseDatabase database;
    DatabaseReference mUserDatabase, mFeedDatabase;
    ProgressBar progressbar;
    CardView returns, plans;
    ImageView img_returns;
    Button btn_returns;
    private RecyclerView recyclerView, stock;
    int mMonth, mYear, dMonth, dYear;
    private LinearLayoutManager linearLayoutManager, linearLayoutManager1;
    private myadapter adapter;
    private myadapter1 adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressbar = findViewById(R.id.progressbar);
        returns = findViewById(R.id.returns);
        img_returns = findViewById(R.id.img_returns);
        plans = findViewById(R.id.plans);
        recyclerView = findViewById(R.id.recycler_view);
        stock = findViewById(R.id.stock);
        btn_returns = findViewById(R.id.btn_returns);

        progressbar.setVisibility(View.VISIBLE);

        SharedPreferences shared = getSharedPreferences("myAppPrefs", MODE_PRIVATE);
        user_id = (shared.getString("user_id", ""));

        database = FirebaseDatabase.getInstance();

        mUserDatabase = database.getReference("Users");

        plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextActivity = new Intent(MainActivity.this, PlanAndRevenue.class);
                nextActivity.putExtra("user_id", user_id);
                startActivity(nextActivity);


            }
        });

        Glide.with(img_returns.getContext())
                .load(R.drawable.return1)
                .placeholder(R.drawable.return1)
                .into(img_returns);

        mFeedDatabase = FirebaseDatabase.getInstance().getReference().child("Feed");

        linearLayoutManager1 = new LinearLayoutManager(MainActivity.this);
        stock.setLayoutManager(linearLayoutManager1);
        stock.setNestedScrollingEnabled(false);

        FirebaseRecyclerOptions<MarketDataSetGet> options1 =
                new FirebaseRecyclerOptions.Builder<MarketDataSetGet>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Market_indices"), MarketDataSetGet.class)
                        .build();


        adapter1 = new myadapter1(options1);
        adapter1.startListening();
        stock.setAdapter(adapter1);

        mFeedDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.hasChildren()) {


                    linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setNestedScrollingEnabled(false);

                    FirebaseRecyclerOptions<ListSetGet1> options =
                            new FirebaseRecyclerOptions.Builder<ListSetGet1>()
                                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Feed"), ListSetGet1.class)
                                    .build();


                    adapter = new myadapter(options);
                    adapter.startListening();
                    recyclerView.setAdapter(adapter);

                } else {


                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        returns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int cyear = c.get(Calendar.YEAR);
                int cmonth = c.get(Calendar.MONTH);
                int cday = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //pick_expire_date.setText((monthOfYear + 1)+"/"+year);
                                mMonth = monthOfYear + 1;
                                mYear = year;

                                Intent intent = new Intent(MainActivity.this, IntrestReturnsActivity.class);
                                intent.putExtra("month", mMonth);
                                intent.putExtra("year", mYear);
                                intent.putExtra("user_id", user_id);
                                startActivity(intent);

                            }
                        },
                        cyear, cmonth, cday);
                datePickerDialog.setTitle("View Interest return");
                datePickerDialog.setMessage("Select Month And Year");
                datePickerDialog.getDatePicker().findViewById(getResources().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                datePickerDialog.show();


            }
        });

        btn_returns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int cyear = c.get(Calendar.YEAR);
                int cmonth = c.get(Calendar.MONTH);
                int cday = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //pick_expire_date.setText((monthOfYear + 1)+"/"+year);
                                mMonth = monthOfYear + 1;
                                mYear = year;

                                Intent intent = new Intent(MainActivity.this, IntrestReturnsActivity.class);
                                intent.putExtra("month", mMonth);
                                intent.putExtra("year", mYear);
                                intent.putExtra("user_id", user_id);
                                startActivity(intent);

                            }
                        },
                        cyear, cmonth, cday);
                datePickerDialog.setTitle("View Interest return");
                datePickerDialog.setMessage("Select Month And Year");
                datePickerDialog.getDatePicker().findViewById(getResources().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                datePickerDialog.show();


            }
        });

        progressbar.setVisibility(View.GONE);
    }

    public class myadapter extends FirebaseRecyclerAdapter<ListSetGet1, myadapter.myviewholder> {
        public myadapter(@NonNull FirebaseRecyclerOptions<ListSetGet1> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull myadapter.myviewholder holder, final int position, @NonNull ListSetGet1 model) {

            final String Id = getRef(position).getKey();

            Glide.with(getApplicationContext()).
                    load(model.getImg())
                    .placeholder(R.drawable.loading)
                    .into(holder.img);

            holder.des.setText(model.getDes());

        }

        @NonNull
        @Override
        public myadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_list, parent, false);
            return new myadapter.myviewholder(view);
        }

        class myviewholder extends RecyclerView.ViewHolder {

            TextView des;
            ImageView img;

            public myviewholder(@NonNull View itemView) {
                super(itemView);

                des = (TextView) itemView.findViewById(R.id.des);
                img = (ImageView) itemView.findViewById(R.id.img);


            }
        }
    }


    public class myadapter1 extends FirebaseRecyclerAdapter<MarketDataSetGet, myadapter1.myviewholder> {
        public myadapter1(@NonNull FirebaseRecyclerOptions<MarketDataSetGet> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull myadapter1.myviewholder holder, final int position, @NonNull MarketDataSetGet model) {

            final String Id = getRef(position).getKey();


            if (model.getState().equals("up")) {

                holder.fluctuation.setText(String.valueOf(model.getFluc()));
            } else {
                holder.fluctuation.setText(String.valueOf(model.getFluc()));
                holder.fluctuation.setTextColor(Color.parseColor("#ff0000"));
                holder.fluctuation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_downward_24, 0, 0, 0);
            }

            holder.name.setText(model.getName().toString());
            holder.value.setText(String.valueOf(model.getValue()));


        }

        @NonNull
        @Override
        public myadapter1.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_list, parent, false);
            return new myadapter1.myviewholder(view);
        }

        class myviewholder extends RecyclerView.ViewHolder {

            TextView name, value, fluctuation;

            public myviewholder(@NonNull View itemView) {
                super(itemView);

                name = (TextView) itemView.findViewById(R.id.name);
                value = (TextView) itemView.findViewById(R.id.value);
                fluctuation = (TextView) itemView.findViewById(R.id.fluctuation);


            }
        }
    }


    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.homee;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();

    }


}