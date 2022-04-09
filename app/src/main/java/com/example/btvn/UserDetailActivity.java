package com.example.btvn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {

    TextView tvUserId,tvUserIdLabel;
    EditText etUserName,etUserEmail,etUserGender,etUserStatus;

    Button btSave, btDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();

        tvUserIdLabel = findViewById(R.id.idLabel);
        tvUserId = findViewById(R.id.userId);
        etUserName = findViewById(R.id.userName);
        etUserEmail = findViewById(R.id.userEmail);
        etUserGender = findViewById(R.id.userGender);
        etUserStatus = findViewById(R.id.userStatus);
        btSave = findViewById(R.id.buttonSave);
        btDelete = findViewById(R.id.buttonDel);

        btDelete.setOnClickListener(view -> {
            int userId = Integer.parseInt(tvUserId.getText().toString());
            ApiListener.getAPI().deleteUser(userId).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(UserDetailActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(UserDetailActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            });
        });

        int userId = intent.getIntExtra("userId", 0);
        if (userId != 0) {
            User user = new User(userId,
                    intent.getStringExtra("userName"),
                    intent.getStringExtra("userEmail"),
                    intent.getStringExtra("userGender"),
                    intent.getStringExtra("userStatus"));

            tvUserId.setText(String.valueOf(user.id));
            etUserName.setText(user.name);
            etUserEmail.setText(user.email);
            etUserGender.setText(user.gender);
            etUserStatus.setText(user.status);

            btSave.setOnClickListener(view -> {
                User data = new User(
                        Integer.parseInt(tvUserId.getText().toString()),
                        etUserName.getText().toString(),
                        etUserEmail.getText().toString(),
                        etUserGender.getText().toString(),
                        etUserStatus.getText().toString());

                ApiListener.getAPI().putUser(data, data.id).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            tvUserId.setText(String.valueOf(response.body().id));
                            etUserName.setText(response.body().name);
                            etUserEmail.setText(response.body().email);
                            etUserGender.setText(response.body().gender);
                            etUserStatus.setText(response.body().status);
                            Toast.makeText(UserDetailActivity.this,"Done!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(UserDetailActivity.this,"Failed!",Toast.LENGTH_SHORT).show();
                    }
                });
            });
        } else {
            tvUserIdLabel.setVisibility(View.GONE);
            tvUserId.setVisibility(View.GONE);
            btDelete.setVisibility(View.GONE);

            btSave.setOnClickListener(view -> {
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
                            Toast.makeText(UserDetailActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(UserDetailActivity.this,"Failed!",Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }
}