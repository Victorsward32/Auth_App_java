package com.example.auth1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;

public class ResisterActivity extends AppCompatActivity {
    private EditText username, password;
    private Button register, login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        username = findViewById(R.id.reg_name);
        password = findViewById(R.id.reg_password);
        register = findViewById(R.id.reg_submit);
        login = findViewById(R.id.reg_signin);

        // Set OnClickListener on register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerNewUser() {
        String userEmail = username.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        // Validate input fields
        if (userEmail.isEmpty()) {
            username.setError("Please enter email");
            username.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
            return;
        }

        // Create a new user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration success
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(ResisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                        // Navigate to LoginActivity
                        Intent intent = new Intent(ResisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Registration failed
                        String errorMessage = ((FirebaseAuthException) task.getException()).getMessage();
                        Toast.makeText(ResisterActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
