package com.dthealth.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dthealth.R;
import com.dthealth.dao.entity.User;

public class UserListAdapter extends PagedListAdapter<User, UserListAdapter.MyViewHolder> {
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
    private ItemClickListener itemClickListener;
    public UserListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mCtx = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_users, parent, false);
        return new MyViewHolder(view);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            User user = getItem(position);
            if (user != null) {
                holder.imageView.setImageResource(R.drawable.baseline_person_outline_black_18dp);
                holder.textView_name.setText(user.getFullName());
                holder.textView_gender.setText(mCtx.getResources().getString(R.string.gender_label, user.getShownGender()));
                holder.textView_dateOfBirth.setText(mCtx.getResources().getString(R.string.dateOfBirth_label, user.getDateOfBirth()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemClickListener.onItemClick(user);
                    }
                });
            }
        } catch (Exception e) {
            Log.e("---------", e.getMessage());
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView_name;
        TextView textView_gender;
        TextView textView_dateOfBirth;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.portrait);
            textView_name = itemView.findViewById(R.id.textViewName);
            textView_gender = itemView.findViewById(R.id.textGender);
            textView_dateOfBirth = itemView.findViewById(R.id.textBirthDate);
        }
    }

    public interface ItemClickListener{
        void onItemClick(User user);
    }
}
