package com.example.btvn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<User> usersList = new ArrayList<>();
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private UserListAdapter mAdapter;
    SwipeRefreshLayout swipLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);
        progressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.list_users);
        swipLayout = findViewById(R.id.swipe_layout);

        mAdapter = new UserListAdapter(usersList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.buttonReload).callOnClick();

        findViewById(R.id.buttonAdd).setOnClickListener(view -> {
            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtra("userId", (byte[]) null);
            startActivity(intent);
        });
    }

    @Override
    public void onRefresh() {
        swipLayout.callOnClick();
        swipLayout.setRefreshing(false);
    }

    public void reload(View view) {
        usersList.clear();
        mTextView.setText("loading...");
        progressBar.setVisibility(View.VISIBLE);

        ApiListener.getAPI().getAllUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<User>> call, @NonNull Response<ArrayList<User>> response) {
                ArrayList<User> userList = response.body();
                mTextView.setText("Number of Users: " + userList.size());
                usersList.addAll(userList);
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<User>> call, @NonNull Throwable t) {
                mTextView.setText("Error:" + t.getMessage());
            }
        });
    }
}