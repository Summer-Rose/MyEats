package com.summerbrochtrup.myeats.models;

import com.summerbrochtrup.myeats.Constants;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by summerbrochtrup on 8/7/16.
 */
@Generated("org.jsonschema2pojo")
public class Location {
    @SerializedName(Constants.SERIALIZED_NAME_ADDRESS)
    private List<String> displayAddress = new ArrayList<>();
    private Coordinate coordinate;

    public List<String> getDisplayAddress() {
        return displayAddress;
    }

    public void setDisplayAddress(List<String> displayAddress) {
        this.displayAddress = displayAddress;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
