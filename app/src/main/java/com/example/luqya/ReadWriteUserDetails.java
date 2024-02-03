package com.example.luqya;

public class ReadWriteUserDetails {

    public String fullName, doD, phone, initiativeName,
            initiativeFounderName, initiativePhone, initiativeLocation,
            initiativeOverView,initiativeSocialMediaAccount;
    public int points;


    public ReadWriteUserDetails(){

    }


    public ReadWriteUserDetails(String textFullName, String textDoB, String textPhone, int points){

        this.fullName = textFullName;
        this.doD = textDoB;
        this.phone = textPhone;
        this.points = points;
    }

    public ReadWriteUserDetails(String textInitiativeName, String textInitiativeFounderName, String textInitiativePhone,
                                String textInitiativeLocation, String textInitiativeDescription, String textInitiativeSocialMediaAccount){

        this.initiativeName = textInitiativeName;
        this.initiativeFounderName = textInitiativeFounderName;
        this.initiativePhone = textInitiativePhone;
        this.initiativeLocation = textInitiativeLocation;
        this.initiativeOverView = textInitiativeDescription;
        this.initiativeSocialMediaAccount = textInitiativeSocialMediaAccount;

    }

}
