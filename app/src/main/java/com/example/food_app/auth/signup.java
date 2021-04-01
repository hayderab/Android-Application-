package com.example.food_app.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity implements View.OnClickListener {
    private TextView register_user;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    //private FirebaseAuth mAuth;
    private EditText userName, email, password, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        register_user = (Button) findViewById(R.id.register_user);
        register_user.setOnClickListener(this);
//        username = (TextInputLayout)findViewById(R.id.userName);
//        email = (TextInputLayout) findViewById(R.id.email);
        //password = (TextInputLayout) findViewById(R.id.password);
//        confirm_password = (TextInputLayout) findViewById(R.id.confirm_password);

        userName = (EditText) findViewById(R.id.fullName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_user:
                register_user();
                break;

        }
    }


    private void register_user() {
        final String Username = userName.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String ConfirmPassword = confirm_password.getText().toString().trim();

        if (Username.isEmpty()) {
            userName.setError("Full name is required!");
            userName.requestFocus();
            return;
        }

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

        if (Password.equals(confirm_password)) {
            password.setError("Password does'not match");
            password.requestFocus();
            return;
        }

//        progressbar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(Email, Password)
                //checking if the user have been registered.
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            User user = new User(Username, Email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task){
                                            if(task.isSuccessful()){
                                                Toast.makeText(signup.this, "user has been registered successfully!", Toast.LENGTH_LONG).show();
                                                //progressbar.setVisibility(View.GONE);
                                            }
                                            else{
                                                Toast.makeText(signup.this, "Failded to register! Try again ", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                        } else{
                            Toast.makeText(signup.this, "Failded to register", Toast.LENGTH_LONG).show();
                        }
                    }



                });



























    }
}