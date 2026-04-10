package com.hotel.models;

import java.util.ArrayList;

public class RoomPreference {
    private RoomType preferredRoomType;
    private int preferredFloor;
    private ArrayList<Amenity> preferredAmenities;

    public RoomPreference(RoomType preferredRoomType, int preferredFloor, ArrayList<Amenity> preferredAmenities) {
        this.preferredRoomType = preferredRoomType;
        this.preferredFloor = preferredFloor;
        this.preferredAmenities = preferredAmenities;
    }

    public RoomType getPreferredRoomType() {
        return preferredRoomType;
    }

    public void setPreferredRoomType(RoomType preferredRoomType) {
        this.preferredRoomType = preferredRoomType;
    }

    public int getPreferredFloor() {
        return preferredFloor;
    }

    public void setPreferredFloor(int preferredFloor) {
        this.preferredFloor = preferredFloor;
    }

    public ArrayList<Amenity> getPreferredAmenities() {
        return preferredAmenities;
    }

    public void setPreferredAmenities(ArrayList<Amenity> preferredAmenities) {
        this.preferredAmenities = preferredAmenities;
    }
}
