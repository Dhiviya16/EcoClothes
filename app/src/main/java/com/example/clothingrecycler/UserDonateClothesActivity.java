package com.example.clothingrecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class UserDonateClothesActivity extends AppCompatActivity {

    ImageView confirm,time,date;
    EditText txtName,txtContactNumber,txtAddress,txtEmail,txtQuantity,txtTime,txtDate
            ,txtCharity,txtService;
    Button sent;
    RadioButton rb1,rb2;
    DatabaseReference ref;
    ProgressDialog progressDialog;
    private int mYear, mMonth, mDay;
    private int mHour, mMinute;
    int i=0;
    FirebaseAuth auth ;
    String [] cht = {"Select A Charity","Together Charity For Special Home","One Hope Charity & Welfare","Persatuan Kebajikan Shammah"
            ,"Syukur Penyayang","Shan Children's Home","Yee Ran Jing Sheh Handicapped Centre","Pusat Jagaan Permata Kasih Mengkuang","Tahfiz Quran Pusat Jagaan Anak Yatim"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_donate_clothes);

        Toolbar toolbar = findViewById(R.id.AddDonatePageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        time = findViewById(R.id.donate_PickTime);
        confirm = findViewById(R.id.donate_ConfirmType);
        date = findViewById(R.id.donate_PickDate);
        txtName = findViewById(R.id.donate_etName);
        txtContactNumber = findViewById(R.id.donate_etContactNumber);
        txtAddress = findViewById(R.id.donate_etAddress);
        txtEmail = findViewById(R.id.donate_etEmail);
        txtQuantity = findViewById(R.id.donate_etQuantity);
        txtTime = findViewById(R.id.donate_etTime);
        txtDate = findViewById(R.id.donate_etDate);
        txtCharity = findViewById(R.id.donate_etCharity);
        txtService = findViewById(R.id.donate_etService);
        sent = findViewById(R.id.donate_BtnSent);
        rb1 = findViewById(R.id.rbService);
        rb2 = findViewById(R.id.rbNoService);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String emailName = user.getEmail();
        txtEmail.setText(emailName);

        ref = FirebaseDatabase.getInstance().getReference("Profile")
                .child(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try{
                    String prName = snapshot.child("prName").getValue().toString();
                    txtName.setText(prName);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try{
                    String prContactNumber = snapshot.child("prContactNumber").getValue().toString();
                    txtContactNumber.setText(prContactNumber);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try{
                    String prAddress = snapshot.child("prAddress").getValue().toString();
                    txtAddress.setText(prAddress);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try{
                    String prEmail = snapshot.child("uEmail").getValue().toString();
                    txtEmail.setText(prEmail);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserDonateClothesActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,cht);
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals("Select A Charity")){
                    //empty
                }
                else {
                    String value = adapterView.getItemAtPosition(i).toString();
                    txtCharity.setText(value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yes = "Laundry Service";
                String no = "No";

                if (rb1.isChecked()){
                    txtService.setText(yes);
                }
                else if (rb2.isChecked()){
                    txtService.setText(no);
                }
                else if (!rb2.isChecked() && !rb2.isChecked()){
                    txtService.setError("Please Select One");
                    txtService.requestFocus();
                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForDatePicker();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forPickTime();
            }
        });

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });


    }

    private String Name="",ContactNumber="",Address="",Email="",Quantity="",Date=""
            ,Time="",Charity="",Services="",Uid;

    private void validateData() {

        Name = txtName.getText().toString().trim();
        ContactNumber = txtContactNumber.getText().toString().trim();
        Address = txtAddress.getText().toString().trim();
        Email = txtEmail.getText().toString().trim();
        Quantity = txtQuantity.getText().toString().trim();
        Date = txtDate.getText().toString().trim();
        Time = txtTime.getText().toString().trim();
        Charity = txtCharity.getText().toString().trim();
        Services = txtService.getText().toString().trim();
        FirebaseUser user = auth.getCurrentUser();
        Uid = user.getUid();

        if (TextUtils.isEmpty(Name)){
            Toast.makeText(this, "Enter Your Name...", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Address)){
            Toast.makeText(this, "Enter Your Address...", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Quantity)){
            Toast.makeText(this, "Enter Quantity...", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(ContactNumber)){
            Toast.makeText(this, "Enter Your Contact Number...", Toast.LENGTH_SHORT).show();
        }else if (ContactNumber.length()<10){
            txtContactNumber.setError("Please Enter Your Phone Number Properly");
        }else if (TextUtils.isEmpty(Date)){
            Toast.makeText(this, "Select Date...", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Time)){
            Toast.makeText(this, "Select Time...", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Charity)){
            Toast.makeText(this, "Select A Charity...", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Services)){
            Toast.makeText(this, "Select The Laundry Service...", Toast.LENGTH_SHORT).show();
        }
        else {
            addDataDonation();
        }
    }

    private void addDataDonation() {

        progressDialog.setMessage("Saving Donation Details...");
        progressDialog.show();


        String timestamp = String.valueOf(System.currentTimeMillis());

        String status="Pending";
        String textile="";
        String recycle="";
        String payment="";
        String paymentStatus="";
        String process="Pending";

        String StatusUid = Uid + status;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("DoUid",Uid);
        hashMap.put("DoName",Name);
        hashMap.put("DoContactNumber", ContactNumber);
        hashMap.put("DoAddress", Address);
        hashMap.put("DoEmail", Email);
        hashMap.put("DoQuantity", Quantity);
        hashMap.put("DoDate", Date);
        hashMap.put("DoTime", Time);
        hashMap.put("DoCharity", Charity);
        hashMap.put("DoService", Services);
        hashMap.put("DoStatusUid", StatusUid);
        hashMap.put("DoStatus", status);
        hashMap.put("DoPayment", payment);
        hashMap.put("DoPaymentStatus", paymentStatus);
        hashMap.put("DoServiceProcess", process);
        hashMap.put("DoTextile", textile);
        hashMap.put("DoRecycle", recycle);
        hashMap.put("DoTimestamp" ,timestamp);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Donation");
        ref.child(timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        progressDialog.dismiss();
                        Toast.makeText(UserDonateClothesActivity.this, "Data Uploaded...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserDonateClothesActivity.this,UserViewDonateActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UserDonateClothesActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();

    }

    private void forPickTime() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                mHour = hourOfDay;
                mMinute=minute;

                Calendar c = Calendar.getInstance();
                c.set(0,0,0,mHour,mMinute);
                txtTime.setText(DateFormat.format("hh:mm aa", c));

            }
        },12,0, false);

        timePickerDialog.updateTime(mHour,mMinute);
        timePickerDialog.show();

    }

}