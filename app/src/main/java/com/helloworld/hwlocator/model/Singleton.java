package com.helloworld.hwlocator.model;

import java.util.ArrayList;
import java.util.List;

public class Singleton {

    private Singleton(){

    }

    private static Singleton singleton;

    public List<LocationObject> locationObjectList = new ArrayList<>();

    public static Singleton getInstance(){
        if(singleton==null){
            singleton = new Singleton();
        }
        return singleton;
    }

    public List<LocationObject> getLocationObjectList() {
        return locationObjectList;
    }

    public void setLocationObjectList(List<LocationObject> locationObjectList) {
        this.locationObjectList = locationObjectList;
    }
}