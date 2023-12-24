package com.example.luqya;

public class ReadWriteUserDetails {

    public String fullName, doD, phone, initiativeName, initiativeFounderName, intiativePhone, initiativeLocation;


    public ReadWriteUserDetails(String textFullName, String textDoB, String textPhone){

        this.fullName = textFullName;
        this.doD = textDoB;
        this.phone = textPhone;
    }

    public ReadWriteUserDetails(String textInitiativeName, String textInitiativeFounderName, String textIntiativePhone, String textInitiativeLocation){

        this.initiativeName = textInitiativeName;
        this.initiativeFounderName = textInitiativeFounderName;
        this.intiativePhone = textIntiativePhone;
        this.initiativeLocation = textInitiativeLocation;
    }

}
