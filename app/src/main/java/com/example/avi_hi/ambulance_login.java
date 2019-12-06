package com.example.avi_hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ambulance_login extends AppCompatActivity implements View.OnClickListener{


    EditText email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_login);

        email= findViewById(R.id.enter_email);
        password = findViewById(R.id.edittext_password);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button_register).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);
    }

    private void ambulance_login()
    {
        String email1 = email.getText().toString().trim();
        String pass1 = password.getText().toString().trim();

        //aadhar
        if (email1.isEmpty()) {
            email.setError("Email is Required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS .matcher(email1).matches()) {
            email.setError("Enter Valid Name");
            email.requestFocus();
            return;
        }

        //for Password
        if (pass1.isEmpty()) {
            password.setError("Password Required");
            password.requestFocus();
            return;
        }
        if (pass1.length() < 6) {
            password.setError("Minimum length of Password is 6");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email1,pass1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(ambulance_login.this, ambulance_inner.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button_login:
                ambulance_login();
                break;
            case R.id.button_register:
                Intent i = new Intent(ambulance_login.this, ambulance_reg.class);
                startActivity(i);
                break;
        }

    }
}
