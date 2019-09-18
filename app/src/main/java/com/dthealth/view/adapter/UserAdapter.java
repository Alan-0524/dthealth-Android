package com.dthealth.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dthealth.R;
import com.dthealth.service.model.User;

public class UserAdapter extends PagedListAdapter<User, UserAdapter.MyViewHolder> {
    private static DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getFullName().equals(newItem.getFullName());
        }
    };
    private Context mCtx;

    public UserAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mCtx = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_users, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            User user = getItem(position);
            if (user != null) {
                holder.textView_name.setText(user.getFullName());
            }
        }catch (Exception e){
            Log.e("---------",e.getMessage());
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textViewName);
        }
    }
}
