package com.example.clothingrecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDonateDetailsActivity extends AppCompatActivity {

    EditText txtName,txtContactNumber,txtAddress,txtEmail,txtQuantity,txtTime,txtDate
            ,txtCharity,txtService,txtFee,txtFeeStatus,txtServiceStatus,txtTextile,txtRecycle,txtStatus;
    FirebaseAuth auth ;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_donate_details);

        Toolbar toolbar = findViewById(R.id.DonateDetailsPageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        txtName = findViewById(R.id.donateDetails_etName);
        txtContactNumber = findViewById(R.id.donateDetails_etContactNumber);
        txtAddress = findViewById(R.id.donateDetails_etAddress);
        txtEmail = findViewById(R.id.donateDetails_etEmail);
        txtQuantity = findViewById(R.id.donateDetails_etQuantity);
        txtTime = findViewById(R.id.donateDetails_etTime);
        txtDate = findViewById(R.id.donateDetails_etDate);
        txtCharity = findViewById(R.id.donateDetails_etCharity);
        txtService = findViewById(R.id.donateDetails_etService);
        txtFee = findViewById(R.id.donateDetails_etFee);
        txtFeeStatus = findViewById(R.id.donateDetails_etFeeStatus);
        txtServiceStatus = findViewById(R.id.donateDetails_etServiceStatus);
        txtTextile = findViewById(R.id.donateDetails_etTextile);
        txtRecycle = findViewById(R.id.donateDetails_etRecycle);
        txtStatus = findViewById(R.id.donateDetails_etStatus);

        String postFID = getIntent().getExtras().getString("DoTimestamp");

        ref = FirebaseDatabase.getInstance().getReference("Donation")
                .child(postFID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String DeName = dataSnapshot.child("DoName").getValue().toString();
                    txtName.setText(DeName);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeEmail = dataSnapshot.child("DoEmail").getValue().toString();
                    txtEmail.setText(DeEmail);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeContact = dataSnapshot.child("DoContactNumber").getValue().toString();
                    txtContactNumber.setText(DeContact);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeAddress = dataSnapshot.child("DoAddress").getValue().toString();
                    txtAddress.setText(DeAddress);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeQuantity = dataSnapshot.child("DoQuantity").getValue().toString();
                    txtQuantity.setText(DeQuantity);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeDate = dataSnapshot.child("DoDate").getValue().toString();
                    txtDate.setText(DeDate);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeTime = dataSnapshot.child("DoTime").getValue().toString();
                    txtTime.setText(DeTime);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeStatus = dataSnapshot.child("DoStatus").getValue().toString();
                    txtStatus.setText(DeStatus);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeCharity = dataSnapshot.child("DoCharity").getValue().toString();
                    txtCharity.setText(DeCharity);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeService = dataSnapshot.child("DoService").getValue().toString();
                    txtService.setText(DeService);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DePayment = dataSnapshot.child("DoPayment").getValue().toString();
                    txtFee.setText(DePayment);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DePaymentStatus = dataSnapshot.child("DoPaymentStatus").getValue().toString();
                    txtFeeStatus.setText(DePaymentStatus);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeServiceProcess = dataSnapshot.child("DoServiceProcess").getValue().toString();
                    txtServiceStatus.setText(DeServiceProcess);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeTextile = dataSnapshot.child("DoTextile").getValue().toString();
                    txtTextile.setText(DeTextile);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeRecycle = dataSnapshot.child("DoRecycle").getValue().toString();
                    txtRecycle.setText(DeRecycle);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}