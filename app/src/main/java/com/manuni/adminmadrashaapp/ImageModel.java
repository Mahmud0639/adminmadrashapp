package com.manuni.adminmadrashaapp;

public class ImageModel {
    private String image, key;

    public ImageModel() {

    }

    public ImageModel(String image, String key) {
        this.image = image;
        this.key = key;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }


}