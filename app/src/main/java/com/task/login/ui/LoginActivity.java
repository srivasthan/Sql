package com.task.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.task.login.database.DatabaseHelper;
import com.task.login.R;
import com.task.login.database.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText name, password;
    private SessionManager sessionManager;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        Button login = findViewById(R.id.login);
        name = findViewById(R.id.edt_login);
        password = findViewById(R.id.edt_password);
        databaseHelper = new DatabaseHelper(this);

        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("name", "Welcome Again");
            startActivity(intent);
            finish();
        }

        login.setOnClickListener(v -> validateEntry());

    }

    private void validateEntry() {
        name.setError(null);
        password.setError(null);

        if (name.getText().toString().trim().isEmpty() && password.getText().toString().trim().isEmpty()) {
            name.setError("Name Cannot be empty");
            password.setError("Password Cannot be empty");
        } else if (name.getText().toString().trim().isEmpty()) {
            name.setError("Enter valid email");
        } else if (password.getText().toString().trim().isEmpty()) {
            password.setError("Enter correct Password");
        } else {
            if (name.getText().toString().trim().equals("admin")) {
                if (!name.getText().toString().trim().equals("admin") && !password.getText().toString().trim().equals("admin")) {
                    name.setError("Email or Password wrong");
                    password.setError("Email or Password wrong");
                } else {
                    sessionManager.setLogin(true);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("name", name.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }
            } else {
                if (!databaseHelper.checkUser(name.getText().toString().trim(), password.getText().toString().trim())) {
                    name.setError("Email or Password wrong");
                    password.setError("Email or Password wrong");
                } else {
                    sessionManager.setLogin(true);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("name", name.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }
            }

        }
    }

}