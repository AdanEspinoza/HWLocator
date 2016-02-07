package com.helloworld.hwlocator.model;

import java.util.ArrayList;

public class Singleton {

    private Singleton(){

    }

    private static Singleton singleton;

    public ArrayList<LocationObject> locationObjectList = new ArrayList<>();

    public static Singleton getInstance(){
        if(singleton==null){
            singleton = new Singleton();
        }
        return singleton;
    }

    public ArrayList<LocationObject> getLocationObjectList() {
        return locationObjectList;
    }

    public void setLocationObjectList(ArrayList<LocationObject> locationObjectList) {
        this.locationObjectList = locationObjectList;
    }
}