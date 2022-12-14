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

public class UserRequestAdapter extends RecyclerView.Adapter<UserRequestAdapter.MyHolder>{

    Context context;
    List<RequestModel> requestModels;

    public UserRequestAdapter(Context context, List<RequestModel> courseModelList) {
        this.context = context;
        this.requestModels = courseModelList;
    }

    @NonNull
    @Override
    public UserRequestAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_post, parent , false);
        return new UserRequestAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRequestAdapter.MyHolder holder, int position) {

        String name = requestModels.get(position).getApName();
        String status = requestModels.get(position).getApStatus();
        String image = requestModels.get(position).getApImage();

        holder.showName.setText(name);
        holder.showStatus.setText(status);
        Glide.with(context).load(image).into(holder.showImage);

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView showName,showStatus;
        ImageView showImage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            showImage = itemView.findViewById(R.id.showImage);
            showName =itemView.findViewById(R.id.showName);
            showStatus =itemView.findViewById(R.id.showStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent UserRequestDetailsActivity = new Intent(context,UserRequestDetailsActivity.class);
                    int position = getAdapterPosition();

                    UserRequestDetailsActivity.putExtra("ApFid",requestModels.get(position).getApFid());
                    UserRequestDetailsActivity.putExtra("ApUid",requestModels.get(position).getApUid());

                    UserRequestDetailsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(UserRequestDetailsActivity);

                }
            });

        }
    }

}

