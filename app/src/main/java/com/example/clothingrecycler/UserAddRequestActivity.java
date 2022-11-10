package com.example.clothingrecycler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;

public class UserAddRequestActivity extends AppCompatActivity {

    ImageView imageView,time,date;
    EditText txtName,txtContactNumber,txtAddress,txtEmail,txtQuantity,txtTime,txtDate;
    Button sent;
    DatabaseReference ref;
    ProgressDialog progressDialog;
    private int mYear, mMonth, mDay;
    private int mHour, mMinute;
    int i=0;
    FirebaseAuth auth ;
    Uri image_uri = null ;

    //image pick constants
    private static final  int GALLERY_IMAGE_CODE = 100 ;
    private static final  int CAMERA_IMAGE_CODE = 200 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_request);

        Toolbar toolbar = findViewById(R.id.AddRequestPageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        imageView = findViewById(R.id.add_ImageView);
        time = findViewById(R.id.add_PickTime);
        date = findViewById(R.id.add_PickDate);
        txtName = findViewById(R.id.add_etName);
        txtContactNumber = findViewById(R.id.add_etContactNumber);
        txtAddress = findViewById(R.id.adde_etAddress);
        txtEmail = findViewById(R.id.add_etEmail);
        txtQuantity = findViewById(R.id.add_etQuantity);
        txtTime = findViewById(R.id.add_etTime);
        txtDate = findViewById(R.id.add_etDate);
        sent = findViewById(R.id.add_BtnSent);

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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
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

                String ApName = txtName.getText().toString().trim();
                String ApContactNumber = txtContactNumber.getText().toString().trim();
                String ApAddress = txtAddress.getText().toString().trim();
                String ApEmail = txtEmail.getText().toString().trim();
                String ApQuantity = txtQuantity.getText().toString().trim();
                String ApTime = txtTime.getText().toString().trim();
                String ApDate= txtDate.getText().toString().trim();
                String ApUi = user.getUid();

                if (TextUtils.isEmpty(ApName)){
                    txtName.setError("Please enter your name");
                }
                else if (TextUtils.isEmpty(ApContactNumber)){
                    txtContactNumber.setError("Please enter your contact number");
                }
                else if (ApContactNumber.length()<10){
                    txtContactNumber.setError("Please enter a valid phone number");
                }
                else if (TextUtils.isEmpty(ApAddress)){
                    txtAddress.setError("Please enter your address");
                }
                else if (TextUtils.isEmpty(ApQuantity)){
                    txtQuantity.setError("Please enter the quantity of clothes");
                }
                else if (TextUtils.isEmpty(ApTime)){
                    txtTime.setError("Please select a pick-up time");
                }
                else if (TextUtils.isEmpty(ApDate)){
                    txtDate.setError("Please select a pick-up date");
                }
                else {
                    uploadData(ApName,ApContactNumber,ApAddress,ApEmail,ApQuantity,ApTime,
                            ApDate,ApUi);
                }

            }
        });

    }

    private void uploadData(String apName, String apContactNumber, String apAddress, String apEmail, String apQuantity, String apTime, String apDate, String apUi) {

        progressDialog.setMessage("Saving Request Info...");
        progressDialog.show();
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        String filepath = "Request/"+"request"+timeStamp;

        if (imageView.getDrawable() != null){
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG , 100 , baos);
            byte[] data = baos.toByteArray();

            StorageReference reference = FirebaseStorage.getInstance().getReference().child(filepath);
            reference.putBytes(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                            while (!uriTask.isSuccessful());

                            String downloadUri = uriTask.getResult().toString();

                            if (uriTask.isSuccessful()){

                                String status="Pending";
                                String remark="";

                                String StatusUid = apUi + status;
                                HashMap<String , Object> hashMap = new HashMap<>();

                                hashMap.put("ApUid" , apUi);
                                hashMap.put("ApEmail" , apEmail);
                                hashMap.put("ApFid" , timeStamp);
                                hashMap.put("ApName" , apName);
                                hashMap.put("ApContactNumber" , apContactNumber);
                                hashMap.put("ApAddress" , apAddress);
                                hashMap.put("ApQuantity" , apQuantity);
                                hashMap.put("ApTime" , apTime);
                                hashMap.put("ApDate" , apDate);
                                hashMap.put("ApImage" , downloadUri);
                                hashMap.put("ApStatus" , status);
                                hashMap.put("ApStatusUid" , StatusUid);
                                hashMap.put("ApRemark",remark);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Request");
                                ref.child(timeStamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();;
                                                Toast.makeText(UserAddRequestActivity.this, "New Request has been submitted", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(UserAddRequestActivity.this, UserHomePageActivity.class));
                                                finish();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(UserAddRequestActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserAddRequestActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }

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

    private void imagePickDialog() {

        //options(camera, gallery)
        String[] options = {"Camera" , "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose image from");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    cameraPick();
                }
                if (which == 1){
                    galleryPick();

                }
            }
        });

        builder.create().show();

    }

    private void cameraPick() {
        //intent to pick image from camera
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE , "Temp Pick");
        contentValues.put(MediaStore.Images.Media.TITLE , "Temp desc");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT , image_uri);
        startActivityForResult(intent , CAMERA_IMAGE_CODE);
    }

    private void galleryPick() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent , GALLERY_IMAGE_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == GALLERY_IMAGE_CODE){
                image_uri = data.getData();
                imageView.setImageURI(image_uri);
            }
            if (requestCode == CAMERA_IMAGE_CODE){
                imageView.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}