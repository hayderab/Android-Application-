package com.example.food_app.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {



    private FirebaseAuth mAuth;

    TextView inputEmail;
    Button Reset;
    String emailString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail = findViewById(R.id.inputEmail);
        Reset = findViewById(R.id.forgotBtn);
         emailString = inputEmail.getText().toString();

        Reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(CameraView.this, "Camera button is clicked", Toast.LENGTH_SHORT).show();

                restPassword();
                ///
            }


        });


    }


    private void restPassword() {
        if (emailString.isEmpty()) {
            inputEmail.setError("Email is required!");
            inputEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            inputEmail.setError("Please provide valid email!");
            inputEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(emailString)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        // Successful Input - Transition to Login Activity
                        if (task.isSuccessful()) {
                            Log.i("Success", "Password Reset Sent");
//                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(forgotPassword.this, "Password Reset Send.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(forgotPassword.this, login.class);
                            startActivity(intent);
                            finish();
                        }

                        // Invalid Input - Show error message
                        else {
                            Log.i("Fail", "Bad input");
                            Toast.makeText(forgotPassword.this, "The email entered is not registered and does not exist!", Toast.LENGTH_SHORT).show();

//                            progressBar.setVisibility(View.GONE);
                        }


                    }

        });
    }
}