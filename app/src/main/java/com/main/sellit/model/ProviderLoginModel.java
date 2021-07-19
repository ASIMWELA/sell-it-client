package com.main.sellit.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProviderLoginModel implements Parcelable {
    String providerUuid,
            officeAddress,
            providerDescription,
            userName,
            lastName,
            firstName,
            email,
            phoneNumber,
            uuid;

    protected ProviderLoginModel(Parcel in) {
        providerUuid = in.readString();
        officeAddress = in.readString();
        providerDescription = in.readString();
        userName = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        uuid = in.readString();
    }

    public static final Creator<ProviderLoginModel> CREATOR = new Creator<ProviderLoginModel>() {
        @Override
        public ProviderLoginModel createFromParcel(Parcel in) {
            return new ProviderLoginModel(in);
        }

        @Override
        public ProviderLoginModel[] newArray(int size) {
            return new ProviderLoginModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(providerUuid);
        dest.writeString(officeAddress);
        dest.writeString(providerDescription);
        dest.writeString(userName);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(uuid);
    }
}
