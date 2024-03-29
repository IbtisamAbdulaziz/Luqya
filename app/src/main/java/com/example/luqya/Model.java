package com.example.luqya;

public class Model {

    private String userId;
    private String name;
    private boolean isSelected;
    private int points;

    public Model(String userId, String name, boolean isSelected, int points){
        this.name=name;
        this.isSelected=isSelected;
        this.points = points;
        this.userId = userId;
    }

    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public boolean isSelected(){return isSelected;}
    public void setSelected(boolean selected){isSelected = selected;}

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String  getUserId() {
        return userId;
    }

    public String toString(){
        return "Model{"+ "name='" + name + '\'' + ", is Selected=" +isSelected+'}';
    }
}
