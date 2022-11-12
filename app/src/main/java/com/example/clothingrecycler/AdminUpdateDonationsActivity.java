package com.example.clothingrecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminUpdateDonationsActivity extends AppCompatActivity {

    EditText txtName,txtContactNumber,txtAddress,txtEmail,txtQuantity,txtTime,txtDate
            ,txtCharity,txtService,txtFee,txtFeeStatus,txtServiceStatus,txtTextile,txtRecycle,txtStatus;
    FirebaseAuth auth ;
    DatabaseReference ref;
    Button update,whatsApp,map;
    RadioButton rbPaid,rbUnPaid,rbFetch,rbRejected,rbComplete,rbIn,rbInComplete;
    ImageView fee,status,laundry;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_donations);

        Toolbar toolbar = findViewById(R.id.AdminDonateDetailsPageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        txtName = findViewById(R.id.donateAdmin_etName);
        txtContactNumber = findViewById(R.id.donateAdmin_etContactNumber);
        txtAddress = findViewById(R.id.donateAdmin_etAddress);
        txtEmail = findViewById(R.id.donateAdmin_etEmail);
        txtQuantity = findViewById(R.id.donateAdmin_etQuantity);
        txtTime = findViewById(R.id.donateAdmin_etTime);
        txtDate = findViewById(R.id.donateAdmin_etDate);
        txtCharity = findViewById(R.id.donateAdmin_etCharity);
        txtService = findViewById(R.id.donateAdmin_etService);
        txtFee = findViewById(R.id.donateAdmin_etFee);
        txtFeeStatus = findViewById(R.id.donateAdmin_etFeeStatus);
        txtServiceStatus = findViewById(R.id.donateAdmin_etServiceStatus);
        txtTextile = findViewById(R.id.donateAdmin_etTextile);
        txtRecycle = findViewById(R.id.donateAdmin_etRecycle);
        txtStatus = findViewById(R.id.donateAdmin_etStatus);
        update = findViewById(R.id.Admin_BtnUpdate);
        rbPaid = findViewById(R.id.rbPaid);
        rbUnPaid = findViewById(R.id.rbUnPaid);
        rbFetch = findViewById(R.id.rbFetch);
        rbRejected = findViewById(R.id.rbRejected);
        rbComplete = findViewById(R.id.rbCompleted);
        rbIn = findViewById(R.id.rbInLaundromat);
        rbInComplete = findViewById(R.id.rbCompletedLaundromat);
        fee = findViewById(R.id.Admin_ConfirmFee);
        laundry = findViewById(R.id.Admin_ConfirmLaundry);
        status = findViewById(R.id.Admin_ConfirmType);
        whatsApp = findViewById(R.id.whatsappBtn);
        map = findViewById(R.id.mapBtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

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

        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = txtContactNumber.getText().toString().trim();

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" + number + "?body=" + ""));
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String map = txtAddress.getText().toString().trim();

                Uri uri = Uri.parse("http://maps.google.co.in/maps?q="+map);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String complete = "Completed";
                String fetched = "To Fetch";
                String reject = "Rejected";

                if (rbComplete.isChecked()){
                    txtStatus.setText(complete);
                }
                else if (rbFetch.isChecked()){
                    txtStatus.setText(fetched);
                }
                else if (rbRejected.isChecked()){
                    txtStatus.setText(reject);
                }
                else if (!rbComplete.isChecked() && !rbFetch.isChecked() && !rbRejected.isChecked()){
                    txtStatus.setError("Please select the current status");
                    txtStatus.requestFocus();
                }
            }
        });

        fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String complete = "Paid";
                String fetched = "UnPaid";

                if (rbPaid.isChecked()){
                    txtFeeStatus.setText(complete);
                }
                else if (rbUnPaid.isChecked()){
                    txtFeeStatus.setText(fetched);
                }
                else if (!rbPaid.isChecked() && !rbUnPaid.isChecked()){
                    txtFeeStatus.setError("Please select the payment status");
                    txtFeeStatus.requestFocus();
                }
            }
        });

        laundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String in = "In Laundromat";
                String complete = "Completed";

                if (rbIn.isChecked()){
                    txtServiceStatus.setText(in);
                }
                else if (rbInComplete.isChecked()){
                    txtServiceStatus.setText(complete);
                }
            }
        });

        String timestamp = getIntent().getExtras().getString("DoTimestamp");
        String uid = getIntent().getExtras().getString("DoUid");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String DeDoUid = uid;
                String DeDoTimestamp = timestamp;

                validateData(DeDoUid,DeDoTimestamp);
            }
        });

    }

    private String DeName="",DeContactNumber="",DeAddress="",DeEmail="",DeQuantity="",DeTime="",DeDate=""
            ,DeCharity="",DeService="",DeFee="",DeFeeStatus="",DeServiceStatus="",DeTextile="",DeRecycle="",DeStatus="",DeUid=""
            ,DeTimeStamp="";

    private void validateData(String deDoUid, String deDoTimestamp) {

        DeUid = deDoUid;
        DeTimeStamp = deDoTimestamp;
        DeName = txtName.getText().toString().trim();
        DeContactNumber = txtContactNumber.getText().toString().trim();
        DeAddress = txtAddress.getText().toString().trim();
        DeEmail = txtEmail.getText().toString().trim();
        DeQuantity = txtQuantity.getText().toString().trim();
        DeDate = txtDate.getText().toString().trim();
        DeTime = txtTime.getText().toString().trim();
        DeCharity = txtCharity.getText().toString().trim();
        DeService = txtService.getText().toString().trim();
        DeFee = txtFee.getText().toString().trim();
        DeFeeStatus = txtFeeStatus.getText().toString().trim();
        DeServiceStatus = txtServiceStatus.getText().toString().trim();
        DeTextile = txtTextile.getText().toString().trim();
        DeRecycle = txtRecycle.getText().toString().trim();
        DeStatus = txtStatus.getText().toString().trim();

        if (TextUtils.isEmpty(DeStatus)){
            Toast.makeText(this, "Please select the current status", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(DeFee)){
            Toast.makeText(this, "Please enter the payment", Toast.LENGTH_SHORT).show();
        }
        else {
            uploadData();
        }

    }

    private void uploadData() {

        progressDialog.setMessage("Saving Donation Details");
        progressDialog.show();


        String timestamp = String.valueOf(System.currentTimeMillis());

        String StatusUid = DeUid + DeStatus;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("DoUid",DeUid);
        hashMap.put("DoName",DeName);
        hashMap.put("DoContactNumber", DeContactNumber);
        hashMap.put("DoAddress", DeAddress);
        hashMap.put("DoEmail", DeEmail);
        hashMap.put("DoQuantity", DeQuantity);
        hashMap.put("DoDate", DeDate);
        hashMap.put("DoTime", DeTime);
        hashMap.put("DoCharity", DeCharity);
        hashMap.put("DoService", DeService);
        hashMap.put("DoStatusUid", StatusUid);
        hashMap.put("DoStatus", DeStatus);
        hashMap.put("DoPayment", DeFee);
        hashMap.put("DoPaymentStatus", DeFeeStatus);
        hashMap.put("DoServiceProcess", DeServiceStatus);
        hashMap.put("DoTextile", DeTextile);
        hashMap.put("DoRecycle", DeRecycle);
        hashMap.put("DoTimestamp" ,DeTimeStamp);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Donation");
        ref.child(DeTimeStamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        progressDialog.dismiss();
                        Toast.makeText(AdminUpdateDonationsActivity.this,
                                "Data is updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminUpdateDonationsActivity.this,
                                AdminHomePageActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(AdminUpdateDonationsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}