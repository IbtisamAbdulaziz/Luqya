package com.example.luqya;


public class DataClass {

  private String name, overview, date, Duration, Language, age, location, attendingMethod ,category, dataImage;


    public DataClass(String name, String overview, String date, String duration, String language, String age,
                     String location,String attendingMethod,String category) {

        this.name = name;
        this.overview = overview;
        this.date = date;
        this.Duration = duration;
        this.Language = language;
        this.age = age;
        this.location = location;
        this.attendingMethod = attendingMethod;
        this.category = category;

    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return Duration;
    }

    public String getLanguage() {
        return Language;
    }

    public String getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }


    public String getCategory(){return category;}

    public String getAttendingMeth(){return attendingMethod;}

    public String getDataImage(){
        return dataImage;
    }


    public DataClass(){

    }
}
