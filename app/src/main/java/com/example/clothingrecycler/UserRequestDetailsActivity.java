package com.example.clothingrecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRequestDetailsActivity extends AppCompatActivity {

    EditText txtName,txtContactNumber,txtAddress,txtEmail,txtQuantity,txtTime,txtDate,txtStatus,txtRemark;
    ImageView imageView;
    FirebaseAuth auth ;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request_details);

        Toolbar toolbar = findViewById(R.id.RequestDetailsPageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        imageView = findViewById(R.id.details_ImageView);
        txtName = findViewById(R.id.details_etName);
        txtContactNumber = findViewById(R.id.details_etContactNumber);
        txtAddress = findViewById(R.id.details_etAddress);
        txtEmail = findViewById(R.id.details_etEmail);
        txtQuantity = findViewById(R.id.details_etQuantity);
        txtTime = findViewById(R.id.details_etTime);
        txtDate = findViewById(R.id.details_etDate);
        txtStatus = findViewById(R.id.details_etStatus);
        txtRemark = findViewById(R.id.details_etRemark);

        String postFID = getIntent().getExtras().getString("ApFid");

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .child(postFID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String pImage = dataSnapshot.child("ApImage").getValue().toString();
                    Glide.with(UserRequestDetailsActivity.this).load(pImage).into(imageView);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeName = dataSnapshot.child("ApName").getValue().toString();
                    txtName.setText(DeName);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeEmail = dataSnapshot.child("ApEmail").getValue().toString();
                    txtEmail.setText(DeEmail);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeContact = dataSnapshot.child("ApContactNumber").getValue().toString();
                    txtContactNumber.setText(DeContact);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeAddress = dataSnapshot.child("ApAddress").getValue().toString();
                    txtAddress.setText(DeAddress);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeQuantity = dataSnapshot.child("ApQuantity").getValue().toString();
                    txtQuantity.setText(DeQuantity);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeDate = dataSnapshot.child("ApDate").getValue().toString();
                    txtDate.setText(DeDate);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeTime = dataSnapshot.child("ApTime").getValue().toString();
                    txtTime.setText(DeTime);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeStatus = dataSnapshot.child("ApStatus").getValue().toString();
                    txtStatus.setText(DeStatus);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeRemark = dataSnapshot.child("ApRemark").getValue().toString();
                    txtRemark.setText(DeRemark);
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