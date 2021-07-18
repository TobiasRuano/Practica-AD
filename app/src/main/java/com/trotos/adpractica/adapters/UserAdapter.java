package com.trotos.adpractica.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trotos.adpractica.R;
import com.trotos.adpractica.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<User> users;
    Context context;

    public UserAdapter(Context c, List<User> u) {
        this.context = c;
        this.users = u;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.bindData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, userName, company;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            userName = itemView.findViewById(R.id.userName);
            company = itemView.findViewById(R.id.company);
        }

        public void bindData(User item) {
            name.setText(item.getName());
            userName.setText(item.getUsername());
            company.setText(item.getCompany().getName());
        }
    }
}
