package com.summerbrochtrup.myrestaurants.models;

import com.summerbrochtrup.myrestaurants.Constants;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;
import org.parceler.Parcel;
import org.parceler.Transient;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Generated("org.jsonschema2pojo")
public class Restaurant {
    String name;
    @SerializedName(Constants.SERIALIZED_NAME_PHONE)
    String phone;
    @SerializedName(Constants.SERIALIZED_NAME_URL)
    String url;
    double rating;
    @SerializedName(Constants.SERIALIZED_NAME_IMAGE_URL)
    String imageUrl;
    List<String> address = new ArrayList<>();
    double latitude;
    double longitude;
    List<List<String>> categories = new ArrayList<>();
    @Transient
    Location location;
    @SerializedName(Constants.SERIALIZED_NAME_ID)
    String restaurantId;
    double distance;
    String pushId;
    String index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String website) {
        this.url = website;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return getLargeImageUrl(imageUrl);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<List<String>> getCategories() {
        return categories;
    }

    public void setCategories(List<List<String>> categories) {
        this.categories = categories;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getId() {
        return restaurantId;
    }

    public void setId(String id) {
        this.restaurantId = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLargeImageUrl(String imageUrl) {
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
        return largeImageUrl;
    }
}
