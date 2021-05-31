package com.task.login.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.task.login.database.DatabaseHelper;
import com.task.login.R;

public class CurrentUser extends Fragment {

    SharedPreferences sharedpreferences;
    private TextView userName, userMail;
    private ProgressDialog progressBar;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_user, container, false);

        userName = view.findViewById(R.id.txt_user_name);
        userMail = view.findViewById(R.id.txt_user_mail);
        databaseHelper = new DatabaseHelper(getContext());

        progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("Loading");
        progressBar.show();

        if (databaseHelper.getAllUser().size() > 0) {
            getCurrentUser();
        } else {
            progressBar.dismiss();
        }

        return view;
    }

    private void getCurrentUser() {
        sharedpreferences = getActivity().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        userName.setText(sharedpreferences.getString("name", ""));
        userMail.setText(sharedpreferences.getString("mail", ""));
        progressBar.dismiss();

    }
}
