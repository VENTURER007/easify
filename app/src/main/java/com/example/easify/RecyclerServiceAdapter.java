package com.example.easify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerServiceAdapter extends RecyclerView.Adapter<RecyclerServiceAdapter.ViewHolder> {
    Context context;
    ArrayList<ServiceModel> service_list;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ServiceModel serviceModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecyclerServiceAdapter(Context context, ArrayList<ServiceModel> service_list) {
        this.context = context;
        this.service_list = service_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.services_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceModel service = service_list.get(position);
        holder.service_name.setText(service.getServiceName());
        holder.service_charge.setText(String.valueOf(service.getServiceCharge())+" INR");
    }

    @Override
    public int getItemCount() {
        return service_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView service_name,service_charge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.service_name);
            service_charge = itemView.findViewById(R.id.service_charge);

            // Set click listener for the item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        ServiceModel service = service_list.get(position);
//                        Log.e("from adapter","service_id : " + service.getService_id());
                        listener.onItemClick(service);

                    }
                }
            });
        }
    }
}
