package com.example.clothingrecycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;

public class UserForgotPasswordActivity extends AppCompatActivity {

    Button submit;
    EditText txtEmail;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_password);

        Toolbar toolbar = findViewById(R.id.ForgotPasswordPageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        submit = findViewById(R.id.forgot_btnSubmit);
        txtEmail = findViewById(R.id.forgot_etEmail);
        auth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {

        String email = txtEmail.getText().toString().trim();

        if(email.isEmpty()) {
            txtEmail.setError("Please Enter Email Address");
            txtEmail.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NotNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(UserForgotPasswordActivity.this, "Please Check Your Email", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(UserForgotPasswordActivity.this, "Try again Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}