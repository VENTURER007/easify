package com.example.easify;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ServiceModel implements Parcelable {
    public String serviceName;
    public String service_id;

    public ServiceModel(String service_id, String serviceName, int serviceCharge, Map<String, Boolean> currentEmployee) {
        this.service_id = service_id;
        this.serviceName = serviceName;
        this.serviceCharge = serviceCharge;
        this.currentEmployee = currentEmployee;
    }

    public ServiceModel(){

    }

    protected ServiceModel(Parcel in) {
        serviceName = in.readString();
        serviceCharge = in.readInt();
    }
    public ServiceModel(String serviceName, int serviceCharge) {
        this.serviceName = serviceName;
        this.serviceCharge = serviceCharge;
        this.currentEmployee = new HashMap<>();
    }

    public static final Creator<ServiceModel> CREATOR = new Creator<ServiceModel>() {
        @Override
        public ServiceModel createFromParcel(Parcel in) {
            return new ServiceModel(in);
        }

        @Override
        public ServiceModel[] newArray(int size) {
            return new ServiceModel[size];
        }
    };

    public ServiceModel(String service_id, String serviceName, int serviceCharge) {

        this.service_id = service_id;
        this.serviceName = serviceName;
        this.serviceCharge = serviceCharge;

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(int serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Map<String, Boolean> getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(Map<String, Boolean> currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    private int serviceCharge;
    private Map<String, Boolean> currentEmployee;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {

        parcel.writeString(serviceName);
        parcel.writeInt(serviceCharge);
        parcel.writeMap(currentEmployee);

    }
}
