package com.example.easify;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easify.adapter.PlaceAutoSuggestAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class BookAppointmentFragment extends Fragment implements RecyclerServiceAdapter.OnItemClickListener {

ArrayList<ServiceModel> services = new ArrayList<>();
RecyclerServiceAdapter adapter;

FirebaseAuth mAuth;

FirebaseDatabase database;

FirebaseUser currentUser;

LatLng latLng;
LatLngWrapper latlng2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_book_appointment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_services);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference servicesRef = database.getReference("services");
        String serviceKey = servicesRef.push().getKey();

//uncomment and run to add new servie





//        services.add(new ServiceModel(serviceKey,"Plumbing",200));
//        services.add(new ServiceModel(serviceKey,"Electrical Works",200));
//        services.add(new ServiceModel(serviceKey,"Home Saloon",100));
//        services.add(new ServiceModel(serviceKey,"Coconut climbing",500));
//        servicesRef.child(serviceKey).setValue(new ServiceModel(serviceKey,"Cook",500))
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Service added successfully
//                        Toast.makeText(getContext(), "Service added sucessfully", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Failed to add service
//                    }
//                });
        adapter = new RecyclerServiceAdapter(getContext(),services);
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

        adapter.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onItemClick(ServiceModel serviceModel) {
        // Display the dialog box
        Log.e("from dialog box","service_id: "+serviceModel.service_id);
        showBottomDialog(serviceModel);
    }
    private void showBottomDialog(ServiceModel serviceModel) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        TextInputLayout landmark = dialog.findViewById(R.id.landmark_layout);
        Log.e("from dialog box","service_id: "+serviceModel.service_id);
        Button bookButton = dialog.findViewById(R.id.loc_search);
        final String[] searchAddress = new String[1];
        // Access the AutoCompleteTextView inside the dialog layout
        final AutoCompleteTextView autoCompleteTextView=dialog.findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(getContext(), R.layout.simple_list_item_1));


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Address : ",autoCompleteTextView.getText().toString());
                latLng=getLatLngFromAddress(autoCompleteTextView.getText().toString());
                if(latLng!=null) {
                    Log.d("Lat Lng : ", " " + latLng.latitude + " " + latLng.longitude);
                    Address address=getAddressFromLatLng(latLng);

                    if(address!=null) {
                        searchAddress[0] = address.getAddressLine(0);
                        Log.d("Address : ", "" + address.toString());
                        Log.d("Address Line : ",""+address.getAddressLine(0));
                        Log.d("Phone : ",""+address.getPhone());
                        Log.d("Pin Code : ",""+address.getPostalCode());
                        Log.d("Feature : ",""+address.getFeatureName());
                        Log.d("More : ",""+address.getLocality());
                    }
                    else {
                        Log.d("Adddress","Address Not Found");
                    }
                }
                else {
                    Log.d("Lat Lng","Lat Lng Not Found");
                }
//                dialog.dismiss();

            }
        });

        //Search clicked
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(searchAddress[0]) && TextUtils.isEmpty(landmark.getEditText().getText().toString().trim())) {
                    Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth = FirebaseAuth.getInstance();
                    currentUser = mAuth.getCurrentUser();
                    database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("orders");
                    DatabaseReference serviceRef = database.getReference("services");

                    String user_id = currentUser.getUid();
                    // Get current date and time
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String currentDateAndTime = sdf.format(new Date()).toString();
                    String lnd_mark = landmark.getEditText().getText().toString().trim();
                    String location = autoCompleteTextView.getText().toString().trim();

                    // Query the database to check if an order already exists for the current user
                    ref.orderByChild("user_id").equalTo(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(getContext(), "Order already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                String order_id = ref.push().getKey();
                                Log.e("from book appointment fragment", latLng.toString());
                                latlng2 = new LatLngWrapper(latLng.latitude,latLng.longitude);
                                OrderModel orderModel = new OrderModel(order_id,serviceModel.service_id, user_id, currentDateAndTime, latlng2, lnd_mark, "", "false");
                                ref.child(order_id).setValue(orderModel)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Order added successfully
                                                Toast.makeText(getContext(), "Order Added successfully", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }

                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Failed to add order
                                                Toast.makeText(getContext(), "Order not added!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Failed to read value
                            Toast.makeText(getContext(), "Failed to check existing orders: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    private LatLng getLatLngFromAddress(String address){

        Geocoder geocoder=new Geocoder(getContext());
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if(addressList!=null){
                Address singleaddress=addressList.get(0);
                LatLng latLng=new LatLng(singleaddress.getLatitude(),singleaddress.getLongitude());
                return latLng;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    private Address getAddressFromLatLng(LatLng latLng){
        Geocoder geocoder=new Geocoder(getContext());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            if(addresses!=null){
                Address address=addresses.get(0);
                return address;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}