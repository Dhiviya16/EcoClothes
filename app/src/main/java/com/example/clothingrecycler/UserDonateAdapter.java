package com.example.clothingrecycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UserDonateAdapter extends RecyclerView.Adapter<UserDonateAdapter.MyHolder>{

    Context context;
    List<DonateModel> donateModels;

    public UserDonateAdapter(Context context, List<DonateModel> donateModelList) {
        this.context = context;
        this.donateModels = donateModelList;
    }

    @NonNull
    @Override
    public UserDonateAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_donate, parent , false);
        return new UserDonateAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDonateAdapter.MyHolder holder, int position) {

        String name = donateModels.get(position).getDoName();
        String status = donateModels.get(position).getDoStatus();
        String date = donateModels.get(position).getDoDate();

        holder.showName.setText(name);
        holder.showStatus.setText(status);
        holder.showDate.setText(date);

    }

    @Override
    public int getItemCount() {
        return donateModels.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView showName,showStatus,showDate;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            showDate = itemView.findViewById(R.id.ShowDonateDate);
            showName =itemView.findViewById(R.id.ShowDonateName);
            showStatus =itemView.findViewById(R.id.ShowDonateStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent UserDonateDetailsActivity = new Intent(context,UserDonateDetailsActivity.class);
                    int position = getAdapterPosition();

                    UserDonateDetailsActivity.putExtra("DoTimestamp",donateModels.get(position).getDoTimestamp());
                    UserDonateDetailsActivity.putExtra("DoUid",donateModels.get(position).getDoUid());

                    UserDonateDetailsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(UserDonateDetailsActivity);

                }
            });

        }
    }

}
