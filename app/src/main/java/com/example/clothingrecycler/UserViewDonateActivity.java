package com.example.clothingrecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserViewDonateActivity extends AppCompatActivity {

    CardView addDonate;
    RecyclerView recyclerView;
    UserDonateAdapter userDonateAdapter;
    List<DonateModel> donateModels;
    FirebaseAuth auth ;
    Button view,pending,fetch,complete,rejected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_donate);

        Toolbar toolbar = findViewById(R.id.ViewDonatePageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        addDonate = findViewById(R.id.addDonation);
        view = findViewById(R.id.homeU_btnViewAll);
        fetch = findViewById(R.id.userH_btnFetch);
        pending = findViewById(R.id.userH_btnPending);
        complete = findViewById(R.id.userH_btnComplete);
        rejected = findViewById(R.id.userH_btnRejected);
        auth = FirebaseAuth.getInstance();

        addDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserViewDonateActivity.this ,UserDonateClothesActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        donateModels = new ArrayList<>();

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPending();
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadComplete();
            }
        });
        rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRejected();
            }
        });

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFetch();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAll();
            }
        });

    }

    private void loadAll() {

        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Donation")
                .orderByChild("DoEmail").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donateModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    DonateModel donateModel = ds.getValue(DonateModel.class);
                    donateModels.add(donateModel);
                    userDonateAdapter = new UserDonateAdapter(getApplicationContext(), donateModels);
                    recyclerView.setAdapter(userDonateAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadFetch() {

        String status="To Fetch";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Donation")
                .orderByChild("DoStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donateModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    DonateModel donateModel = ds.getValue(DonateModel.class);
                    donateModels.add(donateModel);
                    userDonateAdapter = new UserDonateAdapter(getApplicationContext(), donateModels);
                    recyclerView.setAdapter(userDonateAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadRejected() {

        String status="Rejected";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Donation")
                .orderByChild("DoStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donateModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    DonateModel donateModel = ds.getValue(DonateModel.class);
                    donateModels.add(donateModel);
                    userDonateAdapter = new UserDonateAdapter(getApplicationContext(), donateModels);
                    recyclerView.setAdapter(userDonateAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadComplete() {

        String status="Completed";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Donation")
                .orderByChild("DoStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donateModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    DonateModel donateModel = ds.getValue(DonateModel.class);
                    donateModels.add(donateModel);
                    userDonateAdapter = new UserDonateAdapter(getApplicationContext(), donateModels);
                    recyclerView.setAdapter(userDonateAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadPending() {

        String status="Pending";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Donation")
                .orderByChild("DoStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donateModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    DonateModel donateModel = ds.getValue(DonateModel.class);
                    donateModels.add(donateModel);
                    userDonateAdapter = new UserDonateAdapter(getApplicationContext(), donateModels);
                    recyclerView.setAdapter(userDonateAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}