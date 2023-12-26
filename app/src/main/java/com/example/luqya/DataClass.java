package com.example.luqya;


public class DataClass {

  private String name, overview, date, gender, Duration, Language, age, location;

    public DataClass(String name, String overview, String date, String gender, String duration, String language, String age, String location) {
        this.name = name;
        this.overview = overview;
        this.date = date;
        this.gender = gender;
        Duration = duration;
        Language = language;
        this.age = age;
        this.location = location;
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

    public String getGender() {
        return gender;
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
}
