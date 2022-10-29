package com.example.clothingrecycler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminHomePageActivity extends AppCompatActivity {

    ImageView logout;
    Button viewAll,pending,reject,complete,fetch,unPaid,paid,viewDonation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        logout = findViewById(R.id.logout);
        viewAll = findViewById(R.id.adminH_btnViewAll);
        pending = findViewById(R.id.adminH_btnPending);
        reject = findViewById(R.id.adminH_btnRejected);
        complete = findViewById(R.id.adminH_btnCompleted);
        fetch = findViewById(R.id.adminH_btnFetch);
        paid = findViewById(R.id.adminH_btnPaidDonation);
        unPaid = findViewById(R.id.adminH_btnUnPaidDonation);
        viewDonation = findViewById(R.id.adminH_btnViewAllDonation);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomePageActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this,AdminViewAllRequestActivity.class));
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this,AdminPendingRequestActivity.class));
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this,AdminCompletedRequestActivity.class));
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this,AdminRejectedRequestActivity.class));
            }
        });

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this,AdminFetchRequestActivity.class));
            }
        });

        unPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this, AdminViewUpPaidDonationsActivity.class));
            }
        });

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this, AdminViewPaidDonationsActivity.class));
            }
        });

        viewDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this, AdminViewAllDonationActivity.class));
            }
        });

    }
}