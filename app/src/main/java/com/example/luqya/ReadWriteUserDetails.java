package com.example.luqya;

public class ReadWriteUserDetails {

    public String fullName, doD, phone, initiativeName,
            initiativeFounderName, intiativePhone, initiativeLocation,
            initiativeOverView;
    public int points;


    public ReadWriteUserDetails(){

    }


    public ReadWriteUserDetails(String textFullName, String textDoB, String textPhone){

        this.fullName = textFullName;
        this.doD = textDoB;
        this.phone = textPhone;
        this.points = 0;
    }

    public ReadWriteUserDetails(String textInitiativeName, String textInitiativeFounderName, String textInitiativePhone,
                                String textInitiativeLocation, String textInitiativeDescription){

        this.initiativeName = textInitiativeName;
        this.initiativeFounderName = textInitiativeFounderName;
        this.intiativePhone = textInitiativePhone;
        this.initiativeLocation = textInitiativeLocation;
        this.initiativeOverView = textInitiativeDescription;
    }

}
