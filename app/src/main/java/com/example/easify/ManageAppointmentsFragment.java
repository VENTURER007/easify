package com.example.easify;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;




import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



import java.util.List;


public class ManageAppointmentsFragment extends Fragment {

    private static final int TEZ_REQUEST_CODE = 123;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";

    private DatabaseReference databaseReference, userdbRef, servicedbRef;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    FirebaseDatabase database;

    AppCompatButton cncl_appomnt, req_pay;

    LatLngWrapper location;

    String lndmrk;

    boolean hasActiveOrder = false;

    private Context appContext;
    private long serviceCharge;

    TextView order_id, cust_name, mobile_no, address1, landmark, service_name, service_charge;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appContext = context.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_appointments, container, false);
        if (getActivity() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            homeActivity.makeNothingHereVisible();
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Get the current user ID
        String userId = currentUser.getUid();

        // Initialize Firebase Realtime Database reference
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        // Create a query to fetch the order details for the current user with the active status
        Query query = ordersRef.orderByChild("user_id").equalTo(userId);

        // Attach a listener to the query
        // Attach a listener to the query
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Loop through the orders and process each active order
                    for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                        OrderModel order = orderSnapshot.getValue(OrderModel.class);
                        if (order != null) {
                            // Process the active order
                            Log.e("From Manage Fragment", order.order_id);

                            // Retrieve and display the order details in your UI
                            order_id = view.findViewById(R.id.order_Id);
                            cust_name = view.findViewById(R.id.cust_name);
                            mobile_no = view.findViewById(R.id.mobile_no);
                            address1 = view.findViewById(R.id.addressView);
                            landmark = view.findViewById(R.id.landmarkId);
                            service_name = view.findViewById(R.id.serviceNameView);
                            service_charge = view.findViewById(R.id.servicChargeView);

                            // Set the order details in your UI elements
                            order_id.setText(order.order_id);
                            landmark.setText(order.landmark);

                            // Retrieve and display the user details
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                            usersRef.child(order.user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        String fullName = dataSnapshot.child("fullName").getValue(String.class);
                                        String phoneNo = dataSnapshot.child("phoneNo").getValue(String.class);
                                        if (fullName != null && phoneNo != null) {
                                            cust_name.setText(fullName);
                                            mobile_no.setText(phoneNo);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d("From Manage Fragment", "User details Not fetched");
                                }
                            });

                            // Retrieve and display the service details
                            DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference("services");
                            servicesRef.child(order.service_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        String serviceName = dataSnapshot.child("serviceName").getValue(String.class);
                                        serviceCharge = dataSnapshot.child("serviceCharge").getValue(Long.class);
                                        if (serviceName != null && serviceCharge > 0) {
                                            service_name.setText(serviceName);
                                            service_charge.setText(String.valueOf(serviceCharge));
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d("From Manage Fragment", "Service Details Not fetched");
                                }
                            });

                            // Retrieve and display the address details
                            Address address = getAddressFromLatLng(order.location);
                            if (address != null) {
                                String addressLine = address.getAddressLine(0);
                                address1.setText(addressLine);
                            }
                        }
                    }
                } else {
                    // No active orders found for the user
                    Log.d("Manage Order", "No active orders found");
                    // Return a blank view or perform any desired action
                    // For example, hide the layout or show a message
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("From Manage Fragment", "Order fetch error: " + databaseError.getMessage());
            }
        });

        //cancel appoinmtnet
        cncl_appomnt = view.findViewById(R.id.cancel_service);
        cncl_appomnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the order details from Firebase Realtime Database
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
                String userId = currentUser.getUid();
                Query query = ordersRef.orderByChild("user_id").equalTo(userId);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                            orderSnapshot.getRef().removeValue();
                            Toast.makeText(getContext(), "Appointment cancelled!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Delete Order", "Failed to delete order: " + databaseError.getMessage());
                    }
                });
            }
        });
        //request payment
        req_pay = view.findViewById(R.id.req_payment);
        req_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog(String.valueOf(serviceCharge));
            }
        });

        return view;
    }

    private Address getAddressFromLatLng(LatLngWrapper latLng) {
        Geocoder geocoder = new Geocoder(appContext);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            if (addresses != null) {
                Address address = addresses.get(0);
                Log.e("addresses", addresses.toString());
                Log.e("address", address.toString());
                return address;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private void showBottomDialog(String amount) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.req_pay_bottom_layout);

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        TextInputLayout upi_id = dialog.findViewById(R.id.upi_id);

        Button submitUpi = dialog.findViewById(R.id.submit_upi);

        // Search clicked
        submitUpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upiId = upi_id.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(upiId)) {
                    Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidUpiId(upiId)) {
                    Toast.makeText(getContext(), "Invalid UPI ID format", Toast.LENGTH_SHORT).show();
                } else {
                    // Generate UPI payment URI
                    String paymentUri = generateUpiPaymentUri(upiId, amount);

                    // Open Google Pay app with UPI payment URI
                    openPaymentapp(paymentUri);
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
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private boolean isValidUpiId(String upiId) {
        // UPI ID format regex pattern
        String regex = "[\\w.-]+@[\\w]+";

        // Validate the upiId against the regex pattern
        return upiId.matches(regex);
    }

    private String generateUpiPaymentUri(String upiId, String amount) {
        // Construct the UPI payment URI
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", "")
                .appendQueryParameter("mc", "")
                .appendQueryParameter("tid", "")
                .appendQueryParameter("tr", "")
                .appendQueryParameter("tn", "")
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("url", "");

        // Return the UPI payment URI
        return uriBuilder.toString();
    }

    private void openPaymentapp(String paymentUri) {

        // Create an Intent with ACTION_VIEW and the payment URI
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(paymentUri));
        intent.setPackage("net.one97.paytm");
        startActivity(intent);
        // Delete the order details from Firebase Realtime Database
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        String userId = currentUser.getUid();
        Query query = ordersRef.orderByChild("user_id").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    orderSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Delete Order", "Failed to delete order: " + databaseError.getMessage());
            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE) {
            // Process based on the data in response.
            Log.d("result", data.getStringExtra("Status"));
        }
    }


}