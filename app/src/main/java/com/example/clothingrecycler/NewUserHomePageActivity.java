package com.example.clothingrecycler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewUserHomePageActivity extends AppCompatActivity {

    CircleImageView profile;
    TextView txtEmail;
    FirebaseAuth auth ;
    Button recycle,donate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_home_page);

        txtEmail = findViewById(R.id.showEmail);
        profile = findViewById(R.id.prImg);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String emailName = user.getEmail();
        txtEmail.setText(emailName);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewUserHomePageActivity.this, UserProfileActivity.class));
            }
        });

        recycle = findViewById(R.id.GotoRecycle);
        donate = findViewById(R.id.GotoDonate);

        recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewUserHomePageActivity.this, UserHomePageActivity.class));
            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewUserHomePageActivity.this, UserViewDonateActivity.class));
            }
        });

    }
}