package com.task.login.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.task.login.database.DatabaseHelper;
import com.task.login.Model.User;
import com.task.login.R;
import com.task.login.adapter.UsersRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class UserList extends Fragment {

    private List<User> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private TextView noData;
    private GifImageView gifImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        RecyclerView recyclerViewUsers = view.findViewById(R.id.recyclerViewUsers);
        noData = view.findViewById(R.id.txt_no_user);
        gifImageView = view.findViewById(R.id.no_user_gif);

        listUsers = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getContext());
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);

        if (databaseHelper.getAllUser().size() > 0) {
            noData.setVisibility(View.GONE);
            gifImageView.setVisibility(View.GONE);

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    listUsers.clear();
                    listUsers.addAll(databaseHelper.getAllUser());
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    usersRecyclerAdapter.notifyDataSetChanged();
                }
            }.execute();
        }

        return view;

    }
}
