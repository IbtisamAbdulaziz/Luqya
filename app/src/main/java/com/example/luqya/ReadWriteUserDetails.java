package com.example.luqya;

import android.net.Uri;

import androidx.annotation.NonNull;

public class ReadWriteUserDetails {

    public String fullName, doD, phone, initiativeName,
            initiativeFounderName, initiativePhone, initiativeLocation,
            initiativeOverView,initiativeSocialMediaAccount;
    public int points;
    public Uri initativeImageUri;


    public ReadWriteUserDetails(){

    }


    public ReadWriteUserDetails(String textFullName, String textDoB, String textPhone, int points){

        this.fullName = textFullName;
        this.doD = textDoB;
        this.phone = textPhone;
        this.points = points;
    }

    public ReadWriteUserDetails(String textInitiativeName, String textInitiativeFounderName, String textInitiativePhone,
                                String textInitiativeLocation, String textInitiativeDescription, String textInitiativeSocialMediaAccount, Uri initativeImageUri){

        this.initiativeName = textInitiativeName;
        this.initiativeFounderName = textInitiativeFounderName;
        this.initiativePhone = textInitiativePhone;
        this.initiativeLocation = textInitiativeLocation;
        this.initiativeOverView = textInitiativeDescription;
        this.initiativeSocialMediaAccount = textInitiativeSocialMediaAccount;
        this.initativeImageUri = initativeImageUri;

    }

    public String getInitiativeName() {
        return initiativeName;
    }

    public String getInitiativeFounderName() {
        return initiativeFounderName;
    }

    public String getInitiativePhone() {
        return initiativePhone;
    }

    public String getInitiativeLocation() {
        return initiativeLocation;
    }

    public String getInitiativeOverView() {
        return initiativeOverView;
    }

    public String getInitiativeSocialMediaAccount() {
        return initiativeSocialMediaAccount;
    }

    public Uri getInitativeImageUri() {
        return initativeImageUri;
    }

    public void setInitativeImageUri(Uri initativeImageUri) {
        this.initativeImageUri = initativeImageUri;
    }
    @NonNull
    public String toString(){
        return this.initiativeName + ",\nFounded by: " + initiativeFounderName;
    }

    public String toString2(){

        return this.fullName;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
