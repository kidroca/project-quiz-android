package com.example.kidroca.mylittlequizapp.authentication.models.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kidroca on 16.1.2016 г..
 */
public class UserRequestModel implements Parcelable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

    public UserRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserRequestModel(
            String firstName, String lastName, String email, String password, String confirmPassword) {

        this(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.confirmPassword = confirmPassword;
    }

    public UserRequestModel(UserView userViewModel) {
        this.email = userViewModel.getEmail().getText().toString();
        this.password = userViewModel.getPassword().getText().toString();

        if (userViewModel.getConfirmPassword() != null) {
            this.confirmPassword = userViewModel.getConfirmPassword().getText().toString();
        }

        if (userViewModel.getFirstName() != null) {
            this.firstName = userViewModel.getFirstName().getText().toString();
        }

        if (userViewModel.getLastName() != null) {
            this.lastName = userViewModel.getLastName().getText().toString();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    protected UserRequestModel(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        password = in.readString();
        confirmPassword = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(confirmPassword);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserRequestModel> CREATOR = new Parcelable.Creator<UserRequestModel>() {
        @Override
        public UserRequestModel createFromParcel(Parcel in) {
            return new UserRequestModel(in);
        }

        @Override
        public UserRequestModel[] newArray(int size) {
            return new UserRequestModel[size];
        }
    };
}