package com.example.easify;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment{

    RecyclerServiceHomeAdapter adapter;
    ArrayList<ServiceModel> services = new ArrayList<>();

    private Context appContext;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appContext = context.getApplicationContext();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (getActivity() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            homeActivity.makeNothingHereInvisible();
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_services_home);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference servicesRef = database.getReference("services");
        String serviceKey = servicesRef.push().getKey();
        adapter = new RecyclerServiceHomeAdapter(getContext(),services);
        recyclerView.setAdapter(adapter);
        servicesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear(); // Clear the existing list before adding new data
                for (DataSnapshot serviceSnapshot : dataSnapshot.getChildren()) {
                    ServiceModel serviceModel = serviceSnapshot.getValue(ServiceModel.class);
                    services.add(serviceModel);

                }
                adapter.notifyDataSetChanged(); // Notify the adapter of the updated data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Toast.makeText(getContext(), "Failed to fetch services: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;



    }
}