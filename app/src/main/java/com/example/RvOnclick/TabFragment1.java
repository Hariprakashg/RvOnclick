package com.example.RvOnclick;


import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment1 extends Fragment implements CustomerAdapter.OnItemClickListener {


    private TextView mTextView;
    EditText etCustomerSearch;
    public static StDatabase stDatabase;
    private FusedLocationProviderClient fusedLocationProviderClient;
    final ApplicationController applicationController = new ApplicationController();
    public RecyclerView recyclerView;
    public CustomerAdapter customerAdapter;
    private String TAG  = "TabFragment1";



    public static List<Customer> customerList, searchableList;


    private CustomerAdapter.OnItemClickListener listener;
    //public CustomerAdapter customerAdapter = new CustomerAdapter(listener,customerList, getActivity());


    public TabFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);

        stDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), StDatabase.class, "StDB")
                .allowMainThreadQueries().build();

        customerList = stDatabase.stDao().getEnabledCustomers(false);
        for(Customer cust : customerList){
            Log.d(TAG, "onCreateView: " + cust.getDisplay_name() + "\n");
        }



        etCustomerSearch = view.findViewById(R.id.et_searchCustomerList);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        customerList = applicationController.sortCustomerList(customerList);


        listener = this;
        customerAdapter = new CustomerAdapter(listener, customerList, getActivity());
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        etCustomerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //autogenerated - serves no purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //fires up when there's any change in the search text box
                List<Customer> filtered = new ArrayList<>();
                searchableList = stDatabase.stDao().getCustomer();
                for (Customer customer : searchableList) {
                    String searchString;
                    if (customer.getDisplay_name() == null) {
                        searchString = customer.getCustomer_name().toLowerCase();
                    } else {
                        searchString = customer.getDisplay_name().toLowerCase();
                    }
                    String searchedString = etCustomerSearch.getText().toString().toLowerCase();
                    if (searchString.contains(searchedString)) {
                        filtered.add(customer);
                    }
                }
                customerList = applicationController.sortCustomerList(filtered);
                customerAdapter = new CustomerAdapter(listener, customerList, getActivity());
                recyclerView.setAdapter(customerAdapter);
                customerAdapter.notifyDataSetChanged();

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted
            } else {
                applicationController.makeShortToast(getActivity().getApplicationContext(), "Permission Denied!");
                applicationController.checkConfigExistence("useLocationFilter", "0", stDatabase);
            }
        }
    }




    // Trigger new location updates at interval
    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)

        int PERMISSION_REQUEST_FINE_LOCATION = 1;
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
        } else {
            LocationServices.getFusedLocationProviderClient(getActivity()).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            // do work here
                            applicationController.makeShortToast(getActivity(),"onActivityResul");
                            onLocationChanged(locationResult.getLastLocation());
                            Location location = locationResult.getLastLocation();
                            applicationController.makeShortToast(getActivity(),"onLocationChanged: Lat: "+ location.getLatitude()
                                    + "Long: " + location.getLongitude());
                            customerList = stDatabase.stDao().getEnabledCustomers(false);

                            customerList = applicationController.filterCustomersByProximity(customerList,location);
                            customerList = applicationController.sortCustomerList(customerList);
                            customerAdapter.notifyDataSetChanged();
                        }
                    },
                    Looper.myLooper());
        }
    }*/


    //public void onLocationChanged(Location location) {
        // New location has now been determined
        /*applicationController.makeShortToast(getActivity(),"onLocationChanged: Lat: "+ location.getLatitude()
        + "Long: " + location.getLongitude());
        customerList = stDatabase.stDao().getEnabledCustomers(false);

        customerList = applicationController.filterCustomersByProximity(customerList,location);
        customerList = applicationController.sortCustomerList(customerList);
        customerAdapter.notifyDataSetChanged();*/

        //String msg = "Updated Location: " +
         //       Double.toString(location.getLatitude()) + "," +
           //     Double.toString(location.getLongitude());
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());*/
    //}

/*
    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
        int PERMISSION_REQUEST_FINE_LOCATION = 1;
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
        } else {
            locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // GPS location can be null if GPS is switched off
                    if (location != null) {
                        onLocationChanged(location);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("MapDemoActivity", "Error trying to get last GPS location");
                    e.printStackTrace();
                }
            });
        }
    }*/


    @Override
    public void onItemClicked(View v, int position) {

        String custCode = customerList.get(position).getCustomer_id();
        //Toast.makeText(getActivity(), custCode, Toast.LENGTH_SHORT).show();
        /*Bundle args = new Bundle();
        args.putString("custCode", custCode);

        DialogFragment_ProductRateInfo df = new DialogFragment_ProductRateInfo();
        df.setArguments(args);
        df.show(getFragmentManager(),"Dialog");*/

        Intent intent = new Intent(getActivity(), CustomerTransactionActivity.class);
        intent.putExtra("custCode", custCode);
        getActivity().startActivity(intent);
    }


}
