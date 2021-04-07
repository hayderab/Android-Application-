package com.example.food_app.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_app.MainActivity;
import com.example.food_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

     TextView login_button,forgottePw , createAccount;
      EditText email, password;
     String Email, Password;
//    private Button login_button;
     FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //data creating database instance.
        mAuth = FirebaseAuth.getInstance();

        //create_users  = (TextView)findViewById(R.id.create_account);

        login_button = (Button)findViewById(R.id.login_btn);
        login_button = (Button)findViewById(R.id.login_btn);

        forgottePw = findViewById(R.id.forget_password);
        createAccount = findViewById(R.id.create_account);



//        login_button.setOnClickListener(this);
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn_longIn();
            }
        });

        email = (EditText) findViewById(R.id.inputEmail);
        password = (EditText) findViewById(R.id.input_password);


        // Check if user is already authenticated
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        }

        // Set event trigger for forgotten password button
        forgottePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Run function after event trigger
                Intent intent2 = new Intent(login.this, forgotPassword.class);
                startActivity(intent2);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Run function after event trigger
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });



    }


    public void btn_register(View view) {
        Intent intent = new Intent(login.this, signup.class);
        startActivity(intent);
    }



    public void btn_longIn() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
//
        if (Email.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }
        if (Password.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();

        }
        if (Password.length() < 8) {
            password.setError("Minimum password length should be 8");
            password.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)

            {

                if (task.isSuccessful()){
                    //register user
                    startActivity (new Intent (login.this, MainActivity.class));
                    finish();
                }else{

                    Toast.makeText(login.this, "Failed to login! Please Check your credentials", Toast.LENGTH_LONG).show();
                }

            }

        });

    }

}


