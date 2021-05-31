package com.task.login.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.task.login.database.DatabaseHelper;
import com.task.login.Model.User;
import com.task.login.R;
import com.task.login.database.SessionManager;

public class UserRegistration extends Fragment {

    private DatabaseHelper databaseHelper;
    private TextInputEditText userName;
    private TextInputEditText userMail;
    private TextInputEditText userPassword;
    private TextInputEditText userConfirmPassword;
    private Button btnRegister;
    private User user;
    private SessionManager sessionManager;
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_registration, container, false);

        databaseHelper = new DatabaseHelper(view.getContext());
        user = new User();
        sessionManager = new SessionManager(getContext());
        sharedpreferences = getActivity().getSharedPreferences("currentUser", Context.MODE_PRIVATE);

        userName = view.findViewById(R.id.edt_name);
        userMail = view.findViewById(R.id.edt_email);
        userPassword = view.findViewById(R.id.user_password);
        userConfirmPassword = view.findViewById(R.id.user_confirm_password);
        btnRegister = view.findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName.setError(null);
                userMail.setError(null);
                userPassword.setError(null);
                userConfirmPassword.setError(null);

                if (userName.getText().toString().trim().isEmpty() || userMail.getText().toString().trim().isEmpty()
                        || userPassword.getText().toString().trim().isEmpty() || userConfirmPassword.getText().toString().trim().isEmpty()) {
                    userName.setError("Please Enter User Name");
                    userMail.setError("Please Enter E-Mail");
                    userPassword.setError("Please Enter Password");
                    userConfirmPassword.setError("Please Enter Confirm Password");
                } else if (userName.getText().toString().trim().isEmpty()) {
                    userName.setError("Please Enter User Name");
                } else if (userMail.getText().toString().trim().isEmpty()) {
                    userMail.setError("Please Enter E-Mail");
                } else if (userPassword.getText().toString().trim().isEmpty()) {
                    userPassword.setError("Please Enter Password");
                } else if (userConfirmPassword.getText().toString().trim().isEmpty()) {
                    userConfirmPassword.setError("Please Enter Confirm Password");
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userMail.getText().toString().trim()).matches()) {
                    userMail.setError("Please Enter Proper E-Mail");
                } else if (!userPassword.getText().toString().trim().equals(userConfirmPassword.getText().toString().trim())) {
                    userName.setError("Password and Confirm Password doesn't Match");
                    userConfirmPassword.setError("Password and Confirm Password doesn't Match");
                } else {
                    if (databaseHelper.checkUser(userName.getText().toString().trim(), userPassword.getText().toString().trim())) {
                        sessionManager.setLogin(true);
                        Toast.makeText(view.getContext(), "User Already Exists", Toast.LENGTH_SHORT).show();
                        emptyFields();
                    } else {
                        sessionManager.setLogin(true);
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        //storing Current User
                        editor.putString("name", userName.getText().toString().trim());
                        editor.putString("mail", userMail.getText().toString().trim());
                        editor.apply();

                        user.setName(userName.getText().toString().trim());
                        user.setEmail(userMail.getText().toString().trim());
                        user.setPassword(userPassword.getText().toString().trim());
                        databaseHelper.addUser(user);
                        Toast.makeText(view.getContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        emptyFields();
                    }
                }
            }
        });


        return view;
    }

    private void emptyFields() {
        userName.setText(null);
        userMail.setText(null);
        userPassword.setText(null);
        userConfirmPassword.setText(null);
    }
}
