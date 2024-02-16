package com.example.luqya;

import android.net.Uri;

import androidx.annotation.NonNull;

public class ReadWriteUserDetails {

    public String fullName, doD, phone, initiativeName,
            initiativeFounderName, initiativePhone, initiativeLocation,
            initiativeOverView,initiativeSocialMediaAccount, initiativeId, userId;
    public int points;
    public Uri initativeImageUri;


    public ReadWriteUserDetails(String textInitiativeName, String textInitiativeFounderName, String textInitiativePhone, String textInitiativeLocation, String textDescription, String textInitiativeSocialMediaAccount, Uri initiativeImage){

    }


    public ReadWriteUserDetails(String textFullName, String textDoB, String textPhone, int points, String userId ){

        this.fullName = textFullName;
        this.doD = textDoB;
        this.phone = textPhone;
        this.points = points;
        this.userId = userId;
    }

    public ReadWriteUserDetails(String textInitiativeName, String textInitiativeFounderName, String textInitiativePhone,
                                String textInitiativeLocation, String textInitiativeDescription, String textInitiativeSocialMediaAccount,
                                Uri initativeImageUri, String initiativeId){

        this.initiativeName = textInitiativeName;
        this.initiativeFounderName = textInitiativeFounderName;
        this.initiativePhone = textInitiativePhone;
        this.initiativeLocation = textInitiativeLocation;
        this.initiativeOverView = textInitiativeDescription;
        this.initiativeSocialMediaAccount = textInitiativeSocialMediaAccount;
        this.initativeImageUri = initativeImageUri;
        this.initiativeId = initiativeId;

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


    public String getFullName() {
        return fullName;
    }

    public void setInitativeImageUri(Uri initativeImageUri) {
        this.initativeImageUri = initativeImageUri;
    }
    @NonNull
    public String toString(){
        return this.initiativeName + ",\nFounded by: " + initiativeFounderName;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getInitiativeId() {
        return initiativeId;
    }

    public String getUserId() {
        return userId;
    }
}
