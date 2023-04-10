package com.example.easify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.button);
        textView = findViewById(R.id.text1);
        user = auth.getCurrentUser();
        if(user == null){
            Toast.makeText(MainActivity.this, "Authentication sucessful.",
                    Toast.LENGTH_SHORT).show();
        }else{
            textView.setText(user.getEmail());
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent home_view = new Intent(MainActivity.this, LoginActivity.class );
                startActivity(home_view);
                finish();
            }
        });




    }
}