package com.example.luqya;


public class DataClass {

    private String name, overview, date, duration, language, time, location, attendingMethod, category, initiative, dataImage;
    private Object eventType;


    public DataClass(String name, String overview, String date, String duration, String language, String time,
                     String location, String attendingMethod, String category, String initiative, String dataImage) {

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


    public String getCategory() {
        return category;
    }

    public String getAttendingMethod() {
        return attendingMethod;
    }

    public String getInitiative() {
        return initiative;
    }

    public String getDataImage() {
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

    public DataClass() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAttendingMethod(String attendingMethod) {
        this.attendingMethod = attendingMethod;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setInitiative(String initiative) {
        this.initiative = initiative;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }
}