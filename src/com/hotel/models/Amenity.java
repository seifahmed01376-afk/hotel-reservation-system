package com.hotel.models;

public class Amenity {
    private int id;
    private String amenity;

   public Amenity(int id,String amenity){
        this.id=id;
        this.amenity=amenity;
    }

    public int getId() {
        return id;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return "Amenity name: "+ amenity +"-Amenity id: "+id;
    }
}
