package com.example.avi_hi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.util.Patterns;
import android.view.View;
import android.Manifest;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnSuccessListener;

public class hospital_reg extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText email1,adhar,opt_name,hospital_name,opt_mobile,hospital_area,hospital_state,password;


    TextView lat1,lont;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private  static final int REQUEST_CODE = 101;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_reg);



        lat1= findViewById(R.id.lat);
        lont = findViewById(R.id.lon);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        mAuth = FirebaseAuth.getInstance();

        email1 = findViewById(R.id.hospital_email);
        hospital_name=findViewById(R.id.hospital_name);
        opt_name = findViewById(R.id.operator_Name);
        adhar=findViewById(R.id.operator_aadhar);
        opt_mobile=findViewById(R.id.operator_mobile);
        hospital_area=findViewById(R.id.hospital_location);
        hospital_state=findViewById(R.id.hospital_state);
        password=findViewById(R.id.hospital_password);


        findViewById(R.id.button_submit).setOnClickListener(this);
        findViewById(R.id.loc).setOnClickListener(this);

    }


    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);

            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                    currentLocation = location;
                    lat1.setText("Latitude : "+currentLocation.getLatitude());
                    lont.setText("Longitude : "+currentLocation.getLongitude());
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()+"--"+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();

                    //SupportMapFragment supportMapFragment  = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    //supportMapFragment.getMapAsync(MainActivity.this);
                }
            }
        });
    }


    private void hospital_signup(){

        String email= email1.getText().toString().trim();
        String hos1 = hospital_name.getText().toString().trim();
        String opt1 = opt_name.getText().toString().trim();
        String adh1 = adhar.getText().toString().trim();
        String pass1 = password.getText().toString().trim();
        String mob1 = opt_mobile.getText().toString().trim();
        String area1= hospital_area.getText().toString().trim();
        String state1= hospital_state.getText().toString().trim();


        //Email
        if (!Patterns.EMAIL_ADDRESS .matcher(email).matches()) {
            email1.setError("Email is Wrong");
            email1.requestFocus();
            return;
        }

        //for name
        if (hos1.isEmpty()) {
            hospital_name.setError("Hospital Name is Required");
            hospital_name.requestFocus();
            return;
        }

        //for operator name
        if (opt1.isEmpty()) {
            opt_name.setError("Hospital Name is Required");
            opt_name.requestFocus();
            return;
        }

        //for aadhar number
        if (adh1.isEmpty()) {
            adhar.setError("Aadhar Number is Required");
            adhar.requestFocus();
            return;
        }

        if (adh1.length() == 6) {
            password.setError("Enter Valid Aadhar Number:");
            password.requestFocus();
            return;
        }

        //for Password
        if (pass1.length() < 6) {
            password.setError("Minimum length of Password is 6");
            password.requestFocus();
            return;
        }
        //for phone
        if (mob1.isEmpty()) {
            opt_mobile.setError("Phone is Required");
            opt_mobile.requestFocus();
            return;
        }

        if (mob1.length() != 10) {
            opt_mobile.setError("Length Exceeds");
            opt_mobile.requestFocus();
            return;
        }

        //for area
        if (area1.isEmpty()) {
            hospital_area.setError("Teacher Area is Required");
            hospital_area.requestFocus();
            return;
        }

        //for state
        if (state1.isEmpty()) {
            hospital_state.setError("State is required");
            hospital_state.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,pass1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(hospital_reg.this,hospital_login.class));
                    hospital hos= new hospital(
                            email1.getText().toString(),
                            hospital_state.getText().toString(),
                            hospital_name.getText().toString(),
                            opt_name.getText().toString(),
                            adhar.getText().toString(),
                            password.getText().toString(),
                            opt_mobile.getText().toString(),
                            hospital_area.getText().toString()
                    );
                    FirebaseDatabase.getInstance().getReference("hos")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(hos).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(hospital_reg.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(hospital_reg.this, hospital_login.class));
                            } else {
                                //display a failure message
                                Toast.makeText(getApplicationContext(), "Already have an Account", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Already have an Account", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_submit:
                hospital_signup();
                break;
        }

    }
}
