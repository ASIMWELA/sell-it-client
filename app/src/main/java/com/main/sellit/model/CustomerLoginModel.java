package com.main.sellit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.GsonBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CustomerLoginModel implements Parcelable {
    String uuid,
            userName,
            lastName,
            firstName,
            email,
            phoneNumber,
            city,
            country,
            region,
            street,
            locationDescription;

    protected CustomerLoginModel(Parcel in) {
        uuid = in.readString();
        userName = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        city = in.readString();
        country = in.readString();
        region = in.readString();
        street = in.readString();
        locationDescription = in.readString();
    }

    public static final Creator<CustomerLoginModel> CREATOR = new Creator<CustomerLoginModel>() {
        @Override
        public CustomerLoginModel createFromParcel(Parcel in) {
            return new CustomerLoginModel(in);
        }

        @Override
        public CustomerLoginModel[] newArray(int size) {
            return new CustomerLoginModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(userName);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(region);
        dest.writeString(street);
        dest.writeString(locationDescription);
    }
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, CustomerLoginModel.class);
    }
}
