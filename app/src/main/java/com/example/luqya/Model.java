package com.example.luqya;

public class
Model {
    private String name;
    private boolean isSelected;

    public Model(String name, boolean isSelected){
        this.name=name;
        this.isSelected=isSelected;
    }

    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public boolean isSelected(){return isSelected;}
    public void setSelected(boolean selected){isSelected = selected;}

    public String toString(){
        return "user{"+ "name" + name + "is Selected" +isSelected+'}';
    }
}
