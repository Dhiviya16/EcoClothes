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
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomePageActivity extends AppCompatActivity {

    CircleImageView profile;
    TextView txtEmail;
    FirebaseAuth auth ;
    DatabaseReference ref;
    RecyclerView recyclerView;
    UserRequestAdapter userRequestAdapter;
    List<RequestModel> requestModels;
    Button view,pending,fetch,complete,rejected;
    CardView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        Toolbar toolbar = findViewById(R.id.RecyclePageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        add = findViewById(R.id.addRequest);
        view = findViewById(R.id.homeU_btnViewAll);
        fetch = findViewById(R.id.userH_btnFetch);
        pending = findViewById(R.id.userH_btnPending);
        complete = findViewById(R.id.userH_btnComplete);
        rejected = findViewById(R.id.userH_btnRejected);
        auth = FirebaseAuth.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomePageActivity.this, UserAddRequestActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        requestModels = new ArrayList<>();

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

        all();


    }

    private void all() {

        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApEmail").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadAll() {

        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApEmail").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
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

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
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

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
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

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
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

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}