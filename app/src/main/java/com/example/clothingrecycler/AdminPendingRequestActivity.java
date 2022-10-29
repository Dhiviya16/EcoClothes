package com.example.clothingrecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminPendingRequestActivity extends AppCompatActivity {

    EditText txtDate;
    ImageView pickDate,search;
    RecyclerView recyclerView;
    AdminRequestAdapter adminRequestAdapter;
    List<RequestModel> requestModels;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending_request);

        Toolbar toolbar = findViewById(R.id.AdminPendingPageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        txtDate = findViewById(R.id.etDate);
        pickDate = findViewById(R.id.PickDate);
        search = findViewById(R.id.search);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        requestModels = new ArrayList<>();

        loadPending();

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForDatePicker();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchDate = txtDate.getText().toString();

                DateFind(searchDate);
            }
        });

    }

    private void DateFind(String searchDate) {

        Toast.makeText(AdminPendingRequestActivity.this, "Searching", Toast.LENGTH_SHORT).show();

        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApDate").equalTo(searchDate);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    adminRequestAdapter = new AdminRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(adminRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ForDatePicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    private void loadPending() {

        String status = "Pending";
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApStatus").equalTo(status);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    adminRequestAdapter = new AdminRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(adminRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}