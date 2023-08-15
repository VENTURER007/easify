package com.example.easify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswrodActivity extends AppCompatActivity {

    private Button forgotSubmitBtn;
    private TextInputLayout  email;

    private String reg_email;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_passwrod);

        email = findViewById(R.id.forgotEmail);
        forgotSubmitBtn = findViewById(R.id.forgotSubmitBtn);
        auth = FirebaseAuth.getInstance();

        forgotSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg_email = email.getEditText().getText().toString().trim();
                if (reg_email.isEmpty()){
                    email.setError("Required");
                }else{
                    auth.sendPasswordResetEmail(reg_email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ForgotPasswrodActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ForgotPasswrodActivity.this,LoginActivity.class));
                                        finish();
                                    }else{
                                        Log.e("From forget acitvity",""+task.getException().getMessage());
                                    }

                                }
                            });

                    }


                }

        });



    }
}