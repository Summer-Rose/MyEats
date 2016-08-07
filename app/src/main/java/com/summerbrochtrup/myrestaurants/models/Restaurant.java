package com.summerbrochtrup.myrestaurants.models;

import com.google.gson.annotations.Expose;
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
    int databaseId;
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
    List<String> categoryList = new ArrayList<>();
    @Transient
    Location location;
    @SerializedName(Constants.SERIALIZED_NAME_ID)
    String yelpId;

    public Restaurant() {}

    public Restaurant(int id, String name, String phone, String url, double rating, String imageUrl,
                      List<String> address, double latitude, double longitude, List<String> categories, String yelpId) {
        this.databaseId = id;
        this.name = name;
        this.phone = phone;
        this.url = url;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categoryList = categories;
        this.yelpId = yelpId;
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int id) {
        this.databaseId = id;
    }

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
        return imageUrl;
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

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getYelpId() {
        return yelpId;
    }

    public void setYelpId(String id) {
        this.yelpId = id;
    }
}
