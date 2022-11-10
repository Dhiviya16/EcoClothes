package com.example.clothingrecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class AdminUpdateRequestActivity extends AppCompatActivity {

    Button update,whatsApp,map;
    EditText txtName,txtContactNumber,txtAddress,txtEmail,txtQuantity,txtTime,txtDate,txtStatus,txtRemark;
    ImageView imageView,confirm;
    RadioButton fetch,completed,rejected;
    FirebaseAuth auth ;
    DatabaseReference ref;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_request);

        Toolbar toolbar = findViewById(R.id.UpdateRequestPageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        imageView = findViewById(R.id.update_ImageView);
        txtName = findViewById(R.id.update_etName);
        txtContactNumber = findViewById(R.id.update_etContactNumber);
        txtAddress = findViewById(R.id.update_etAddress);
        txtEmail = findViewById(R.id.update_etEmail);
        txtQuantity = findViewById(R.id.update_etQuantity);
        txtTime = findViewById(R.id.update_etTime);
        txtDate = findViewById(R.id.update_etDate);
        update = findViewById(R.id.update_BtnUpdate);
        confirm = findViewById(R.id.update_ConfirmType);
        fetch = findViewById(R.id.rbFetch);
        completed = findViewById(R.id.rbCompleted);
        rejected = findViewById(R.id.rbRejected);
        txtStatus = findViewById(R.id.update_etStatus);
        txtRemark = findViewById(R.id.update_etRemark);
        whatsApp = findViewById(R.id.whatsappBtn);
        map = findViewById(R.id.mapBtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        String postFID = getIntent().getExtras().getString("ApFid");

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .child(postFID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String pImage = dataSnapshot.child("ApImage").getValue().toString();
                    Glide.with(AdminUpdateRequestActivity.this).load(pImage).into(imageView);
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

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String complete = "Completed";
                String fetched = "To Fetch";
                String reject = "Rejected";

                if (completed.isChecked()){
                    txtStatus.setText(complete);
                }
                else if (fetch.isChecked()){
                    txtStatus.setText(fetched);
                }
                else if (rejected.isChecked()){
                    txtStatus.setText(reject);
                }
                else if (!completed.isChecked() && !fetch.isChecked() && !rejected.isChecked()){
                    txtStatus.setError("Please select the current status");
                    txtStatus.requestFocus();
                }
            }
        });

        String DUid = getIntent().getExtras().getString("ApUid");
        String DFId = getIntent().getExtras().getString("ApFid");
        String DImage = getIntent().getExtras().getString("ApImage");
        
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ApName = txtName.getText().toString().trim();
                String ApContactNumber = txtContactNumber.getText().toString().trim();
                String ApAddress = txtAddress.getText().toString().trim();
                String ApEmail = txtEmail.getText().toString().trim();
                String ApQuantity = txtQuantity.getText().toString().trim();
                String ApTime = txtTime.getText().toString().trim();
                String ApDate = txtDate.getText().toString().trim();
                String ApStatus = txtStatus.getText().toString().trim();
                String ApRemark = txtRemark.getText().toString().trim();
                String ApUid = DUid;
                String ApFid = DFId;
                String ApImage = DImage;

                if (TextUtils.isEmpty(ApStatus)){
                    txtStatus.setError("Please select the current status");
                }
                else if (TextUtils.isEmpty(ApRemark)){
                    txtRemark.setError("Please update the Remark/Comment");
                }
                else {
                    String ApStatusUid = DUid+ApStatus;
                    uploadData(ApName,ApContactNumber,ApAddress,ApEmail,ApQuantity,ApTime,
                            ApDate,ApStatus,ApRemark,ApUid,ApFid,ApImage,ApStatusUid);
                }
                
            }
        });
        
    }

    private void uploadData(String apName, String apContactNumber, String apAddress, String apEmail, String apQuantity, String apTime, String apDate, String apStatus, String apRemark, String apUid, String apFid, String apImage,
                            String apStatusUid) {

        progressDialog.setMessage("Updating Request Info");
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

                                HashMap<String , Object> hashMap = new HashMap<>();

                                hashMap.put("ApUid" , apUid);
                                hashMap.put("ApEmail" , apEmail);
                                hashMap.put("ApFid" , apFid);
                                hashMap.put("ApName" , apName);
                                hashMap.put("ApContactNumber" , apContactNumber);
                                hashMap.put("ApAddress" , apAddress);
                                hashMap.put("ApQuantity" , apQuantity);
                                hashMap.put("ApTime" , apTime);
                                hashMap.put("ApDate" , apDate);
                                hashMap.put("ApImage" , apImage);
                                hashMap.put("ApStatus" , apStatus);
                                hashMap.put("ApStatusUid" , apStatusUid);
                                hashMap.put("ApRemark",apRemark);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Request");
                                ref.child(apFid).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();;
                                                Toast.makeText(AdminUpdateRequestActivity.this, "Request has been Updated", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(AdminUpdateRequestActivity.this, AdminPendingRequestActivity.class));
                                                finish();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(AdminUpdateRequestActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminUpdateRequestActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }

    }
}