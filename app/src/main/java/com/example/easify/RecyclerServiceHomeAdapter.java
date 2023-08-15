package com.example.easify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class RecyclerServiceHomeAdapter extends RecyclerView.Adapter<RecyclerServiceHomeAdapter.ViewHolder> {
    Context context;
    ArrayList<ServiceModel> service_list;



    public RecyclerServiceHomeAdapter(Context context, ArrayList<ServiceModel> service_list) {
        this.context = context;
        this.service_list = service_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.services_home_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceModel service = service_list.get(position);
        holder.service_name.setText(service.getServiceName());
        Glide.with(context).load(service.getImageUrl()).override(Target.SIZE_ORIGINAL).into(holder.service_image);


    }

    @Override
    public int getItemCount() {
        return service_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView service_name;
        ImageView service_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.service_name);
            service_image = itemView.findViewById(R.id.serviceImage);


        }
    }
}
