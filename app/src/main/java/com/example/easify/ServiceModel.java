package com.example.easify;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ServiceModel implements Parcelable {
    public String serviceName;
    public String service_id;

    public String getImageUrl() {
        return imageUrl;
    }

    public ServiceModel(String serviceName, String service_id, String imageUrl, int serviceCharge, Map<String, Boolean> currentEmployee) {
        this.serviceName = serviceName;
        this.service_id = service_id;
        this.imageUrl = imageUrl;
        this.serviceCharge = serviceCharge;
        this.currentEmployee = currentEmployee;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String imageUrl;

    public ServiceModel(String service_id, String serviceName, int serviceCharge, Map<String, Boolean> currentEmployee) {
        this.service_id = service_id;
        this.serviceName = serviceName;
        this.serviceCharge = serviceCharge;
        this.currentEmployee = currentEmployee;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public ServiceModel(){

    }

    protected ServiceModel(Parcel in) {
        serviceName = in.readString();
        serviceCharge = in.readInt();
        imageUrl = in.readString();
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
        parcel.writeString(imageUrl);

    }
}
