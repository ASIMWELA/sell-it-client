package com.main.sellit.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailsModel implements Parcelable {
    String
            firstName,
            lastName,
            userName,
            phoneNumber,
            email,
            password;
    protected UserDetailsModel(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        userName = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<UserDetailsModel> CREATOR = new Creator<UserDetailsModel>() {
        @Override
        public UserDetailsModel createFromParcel(Parcel in) {
            return new UserDetailsModel(in);
        }

        @Override
        public UserDetailsModel[] newArray(int size) {
            return new UserDetailsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(userName);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeString(password);
    }
}
