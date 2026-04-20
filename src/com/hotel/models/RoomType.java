package com.hotel.models;

public class RoomType {
    private static int idCounter=0;
    private int id;
    private String type;

  public  RoomType(String type){
        this.id=++idCounter;
        this.type=type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString(){
      return "RoomType: "+type+"-RoomId: "+id;
    }
}
