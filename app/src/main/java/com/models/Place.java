package com.models;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

public class Place implements Serializable, ClusterItem {
    public int    id;
    public int type;
    public String name;
    public String image;
    public String address;
    public String phone_number;
    public String website;
    public String description;
    public double lon;
    public double lat;
    public String pic_place;
    public long last_update;
    public float distance = -1;
    public String snippet;
    public BitmapDescriptor icon;

    public Place(){

    }

    public Place(BitmapDescriptor ic){
        icon = ic;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(lat, lon);
    }

    public boolean isDraft(){
        return (address == null && phone_number == null && website == null && description == null);
    }

    public void setIcon(BitmapDescriptor ic){
        this.icon = ic;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getPId(){
        return id;
    }

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setPhone_number(String phone_number){
        this.snippet = "Telp : "+phone_number;
        this.phone_number = phone_number;
    }

    public String getPhone_number(){
        return phone_number;
    }

    public void setWebsite(String website){
        this.website = website;
    }

    public String getWebsite(){
        return website;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setLon(double lon){
        this.lon = lon;
    }

    public double getLon(){
        return lon;
    }

    public void setLat(double lat){
        this.lat = lat;
    }

    public double getLat(){
        return lat;
    }

    public void setPic_place(String url){
        this.pic_place = url;
    }

    public String getPic_place(){
        return pic_place;
    }
    public void setLast_update(long last_update){
        this.last_update = last_update;
    }

    public long getLast_update(){
        return last_update;
    }

    public void setDistance(float distance){
        this.distance = distance;
    }

    public float getDistance(){
        return distance;
    }
    public String getSnippet(){
        return snippet;
    }

}
