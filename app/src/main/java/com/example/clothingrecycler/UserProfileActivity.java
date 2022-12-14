package com.example.clothingrecycler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    CircleImageView profileImage;
    EditText txtName,txtAddress,txtContactNumber,txtEmail;
    Button save,logout;
    Uri image_uri = null ;
    private static final  int GALLERY_IMAGE_CODE = 100 ;
    ProgressDialog pd ;
    FirebaseAuth auth ;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = findViewById(R.id.UserProfilePageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });


        save = findViewById(R.id.profile_BtnSave);
        profileImage = findViewById(R.id.prImg);
        txtName = findViewById(R.id.profile_etName);
        txtEmail = findViewById(R.id.profile_etEmail);
        txtContactNumber = findViewById(R.id.profile_etContactNumber);
        txtAddress = findViewById(R.id.profile_etAddress);
        logout = findViewById(R.id.profile_BtnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
                auth.signOut();
                finish();
            }
        });

        pd = new ProgressDialog(this);

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

                try {
                    String pImage = snapshot.child("pImage").getValue().toString();
                    Glide.with(UserProfileActivity.this).load(pImage).into(profileImage);

                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
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

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String prName = txtName.getText().toString().trim();
                String prAddress = txtAddress.getText().toString().trim();
                String prContactNumber = txtContactNumber.getText().toString().trim();
                String prEmail = txtEmail.getText().toString().trim();
                String prof_ID = user.getUid();

                if (TextUtils.isEmpty(prName)){
                    txtName.setError("Please enter your name");
                }
                else if (TextUtils.isEmpty(prContactNumber)){
                    txtContactNumber.setError("Please enter your contact number");
                }
                else if (prContactNumber.length() < 10){
                    txtContactNumber.setError("Please enter a valid contact number");
                }
                else if (TextUtils.isEmpty(prAddress)){
                    txtAddress.setError("Please enter your address");
                }
                else {
                    uploadData(prName,prAddress,prContactNumber,prEmail,prof_ID);
                }

            }
        });

    }

    private void uploadData(String prName, String prAddress, String prContactNumber, String prEmail, String prof_id) {

        pd.setMessage("Editing Profile");
        pd.show();
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        String filepath = "Profiles/"+"profile_"+timeStamp;

        if (profileImage.getDrawable() != null){
            Bitmap bitmap = ((BitmapDrawable)profileImage.getDrawable()).getBitmap();
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

                                FirebaseUser user = auth.getCurrentUser();

                                HashMap<String , Object> hashMap = new HashMap<>();
                                hashMap.put("uid" , prof_id);
                                hashMap.put("uEmail" , user.getEmail());
                                hashMap.put("pId" , timeStamp);
                                hashMap.put("prName" , prName);
                                hashMap.put("prContactNumber" , prContactNumber);
                                hashMap.put("prAddress" , prAddress);
                                hashMap.put("prEmail" , prEmail);
                                hashMap.put("pImage" , downloadUri);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Profile");
                                ref.child(prof_id).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                pd.dismiss();
                                                Toast.makeText(UserProfileActivity.this, "Profile has been edited", Toast.LENGTH_SHORT).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                Toast.makeText(UserProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });
        }

    }

    private void imagePickDialog() {
        String[] options = {"Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose image from");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    galleryPick();

                }
            }
        });

        builder.create().show();

    }

    private void galleryPick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent , GALLERY_IMAGE_CODE);
    }

    private void permission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken toke) {
                        toke.continuePermissionRequest();
                    }
                }).check();
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == GALLERY_IMAGE_CODE){
                image_uri = data.getData();
                profileImage.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}