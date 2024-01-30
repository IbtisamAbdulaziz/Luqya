package com.example.luqya;


public class DataClass {

  private String name, overview, date, duration, language, time, location, attendingMethod ,category, initiative, dataImage;


    public DataClass(String name, String overview, String date, String duration, String language, String time,
                     String location,String attendingMethod,String category, String initiative, String dataImage) {

        this.name = name;
        this.overview = overview;
        this.date = date;
        this.duration = duration;
        this.language = language;
        this.time = time;
        this.location = location;
        this.attendingMethod = attendingMethod;
        this.category = category;
        this.dataImage = dataImage;
        this.initiative = initiative;

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
        return duration;
    }

    public String getLanguage() {
        return language;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }


    public String getCategory(){return category;}

    public String getAttendingMethod(){return attendingMethod;}

    public String getInitiative() {
        return initiative;
    }

    public String getDataImage(){
        return dataImage;
    }

    @Override
    public String toString() {
        return "DataClass{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                // Add other fields here...
                '}';
    }
    public DataClass(){

    }
}
