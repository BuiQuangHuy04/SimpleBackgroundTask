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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  {

    ArrayList<User> usersList = new ArrayList<>();
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private UserListAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);
        progressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.list_users);
        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);

        mAdapter = new UserListAdapter(usersList, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                loadRecyclerViewData();
            }
        });

        findViewById(R.id.buttonReload).setVisibility(View.GONE);

        findViewById(R.id.buttonAdd).setOnClickListener(view -> {
            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtra("userId", (byte[]) null);
            startActivity(intent);
        });
    }

    @Override
    public void onRefresh() {
        loadRecyclerViewData();
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

    private void loadRecyclerViewData()
    {
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
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<User>> call, @NonNull Throwable t) {
                mTextView.setText("Error:" + t.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}