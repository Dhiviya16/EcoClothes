package com.example.clothingrecycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminDonateAdapter extends RecyclerView.Adapter<AdminDonateAdapter.MyHolder>{

    Context context;
    List<DonateModel> donateModels;

    public AdminDonateAdapter(Context context, List<DonateModel> donateModelList) {
        this.context = context;
        this.donateModels = donateModelList;
    }

    @NonNull
    @Override
    public AdminDonateAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_donate, parent , false);
        return new AdminDonateAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminDonateAdapter.MyHolder holder, int position) {

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
                    Intent AdminUpdateDonationsActivity = new Intent(context,AdminUpdateDonationsActivity.class);
                    int position = getAdapterPosition();

                    AdminUpdateDonationsActivity.putExtra("DoTimestamp",donateModels.get(position).getDoTimestamp());
                    AdminUpdateDonationsActivity.putExtra("DoUid",donateModels.get(position).getDoUid());

                    AdminUpdateDonationsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(AdminUpdateDonationsActivity);

                }
            });

        }
    }

}
