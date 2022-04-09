package com.example.btvn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {

    TextView tvUserId;
    EditText etUserName,etUserEmail,etUserGender,etUserStatus;

    Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();

        tvUserId = findViewById(R.id.userId);
        etUserName = findViewById(R.id.userName);
        etUserEmail = findViewById(R.id.userEmail);
        etUserGender = findViewById(R.id.userGender);
        etUserStatus = findViewById(R.id.userStatus);
        btnSave = findViewById(R.id.buttonSave);
        btnDelete = findViewById(R.id.buttonDel);

        btnDelete.setOnClickListener(view -> {
            int userId = Integer.parseInt(tvUserId.getText().toString());
            ApiListener.getAPI().deleteUser(userId).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });
        });

        int userId = intent.getIntExtra("userId", 0);
        if (userId != 0) {
            String userName = intent.getStringExtra("userName");
            String userEmail = intent.getStringExtra("userEmail");
            String userGender = intent.getStringExtra("userGender");
            String userStatus = intent.getStringExtra("userStatus");
            User user = new User(userId, userName, userEmail, userGender, userStatus);

            tvUserId.setText(String.valueOf(user.id));
            etUserName.setText(user.name);
            etUserEmail.setText(user.email);
            etUserGender.setText(user.gender);
            etUserStatus.setText(user.status);

            btnSave.setOnClickListener(view -> {

                int id = Integer.parseInt(tvUserId.getText().toString());
                String name = etUserName.getText().toString();
                String email = etUserEmail.getText().toString();
                String gender = etUserGender.getText().toString();
                String status = etUserStatus.getText().toString();

                User data = new User(id, name, email, gender, status);

                ApiListener.getAPI().putUser(data, data.id).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            tvUserId.setText(String.valueOf(response.body().id));
                            etUserName.setText(response.body().name);
                            etUserEmail.setText(response.body().email);
                            etUserGender.setText(response.body().gender);
                            etUserStatus.setText(response.body().status);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                    }
                });
            });
        } else {
            tvUserId.setEnabled(false);
            btnDelete.setVisibility(View.GONE);

            btnSave.setOnClickListener(view -> {
                int id = Integer.parseInt(tvUserId.getText().toString());
                String name = etUserName.getText().toString();
                String email = etUserEmail.getText().toString();
                String gender = etUserGender.getText().toString();
                String status = etUserStatus.getText().toString();

                User data = new User(id, name, email, gender, status);
                ApiListener.getAPI().postUser(data).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            tvUserId.setText(String.valueOf(response.body().id));
                            etUserName.setText(response.body().name);
                            etUserEmail.setText(response.body().email);
                            etUserGender.setText(response.body().gender);
                            etUserStatus.setText(response.body().status);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                    }
                });
            });
        }
    }
}