package com.example.btvn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private ArrayList<User> users;
    private Context context;

    public UserListAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View mItemView = mInflater.inflate(R.layout.userlist_item, parent, false);
        return new UserViewHolder(mItemView, this);    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        String curUserName = user.name;
        holder.tvListUser.setText(curUserName);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context,UserDetailActivity.class);
            intent.putExtra("userId", user.id);
            intent.putExtra("userName", user.name);
            intent.putExtra("userEmail", user.email);
            intent.putExtra("userGender", user.gender);
            intent.putExtra("userStatus", user.status);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

class UserViewHolder extends RecyclerView.ViewHolder {

    TextView tvListUser;
    UserListAdapter userListAdapter;

    public UserViewHolder(@NonNull View itemView, UserListAdapter adapter) {
        super(itemView);
        tvListUser = itemView.findViewById(R.id.user_name);
        this.userListAdapter = adapter;
    }
}
