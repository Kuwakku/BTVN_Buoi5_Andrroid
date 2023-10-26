package com.example.btvn_buoi5_andrroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> mlistUsers;


    Context mcontext;

    public UserAdapter(ArrayList<User> listUsers) {
        this.mlistUsers = listUsers;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mcontext = parent.getContext();
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_users, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = mlistUsers.get(position);
        holder.tvUserName.setText(user.getUserName());
    }

    public void deleteItem(int pos) {
        mlistUsers.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemCount() {
        return mlistUsers != null ? mlistUsers.size() : 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tvUserName);
        }
    }
}
