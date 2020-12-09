package com.example.bargest.Models;

public class Categories {

    int id;
    String Name;

    public Categories(int id,String name) {
        this.id=id;
        Name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
