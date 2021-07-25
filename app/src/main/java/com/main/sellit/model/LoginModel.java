package com.main.sellit.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginModel implements Parcelable {
    String  userName,
            lastName,
            firstName,
            email,
            phoneNumber,
            uuid,
            serviceProviderUuid;


    protected LoginModel(Parcel in) {
        userName = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        uuid = in.readString();
        serviceProviderUuid = in.readString();
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(uuid);
        dest.writeString(serviceProviderUuid);
    }
}
