package com.manuni.adminmadrashaapp;

public class AutoImageModel {
    private String image, key;

    public AutoImageModel(){

    }
    public AutoImageModel(String image, String key){
        this.image = image;
        this.key = key;
    }
    public String getImage(){
        return image;
    }
    public void setKey(String key){
        this.key = key;
    }
}
