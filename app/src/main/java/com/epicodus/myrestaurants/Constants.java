package com.epicodus.myrestaurants;

public class Constants {
    public static final String YELP_CONSUMER_KEY = BuildConfig.YELP_CONSUMER_KEY;
    public static final String YELP_CONSUMER_SECRET = BuildConfig.YELP_CONSUMER_SECRET;
    public static final String YELP_TOKEN = BuildConfig.YELP_TOKEN;
    public static final String YELP_TOKEN_SECRET = BuildConfig.YELP_TOKEN_SECRET;
    public static final String YELP_BASE_URL = "https://api.yelp.com/v2/search?term=food";
    public static final String YELP_LOCATION_QUERY_PARAMETER = "location";

    public static final String FIREBASE_CHILD_RESTAURANTS = "restaurants";
    public static final String FIREBASE_QUERY_INDEX = "index";
    public static final String FIREBASE_CHILD_IMAGEURL = "imageUrl";

    public static final String PREFERENCES_LOCATION_KEY = "location";
    public static final String EXTRA_KEY_POSITION = "position";
    public static final String EXTRA_KEY_RESTAURANTS = "restaurants";

    public static final String KEY_SOURCE = "source";
    public static final String SOURCE_SAVED = "saved";
    public static final String SOURCE_FIND = "find";

    public static final String EMPTY_STRING = "";
    public static final String FONT_OSTRICH_REGULAR = "fonts/ostrich-regular.ttf";
    public static final String HTTP_FILTER = "http";
    public static final String BITMAP_EXTRA = "data";

    public static final String INDEX_NOT_SPECIFIED = "not_specified";

    public static final String JSON_ARRAY_BUSINESSES = "businesses";
    public static final String JSON_STRING_NAME = "name";
    public static final String JSON_STRING_PHONE = "display_phone";
    public static final String JSON_OPT_STRING_PHONE = "Phone not available";
    public static final String JSON_STRING_URL = "url";
    public static final String JSON_DOUBLE_RATING = "rating";
    public static final String JSON_STRING_IMAGE_URL = "image_url";
    public static final String JSON_OBJECT_LOCATION = "location";
    public static final String JSON_OBJECT_COORDINATE = "coordinate";
    public static final String JSON_DOUBLE_LAT = "latitude";
    public static final String JSON_DOUBLE_LNG = "longitude";
    public static final String JSON_ARRAY_ADDRESS = "display_address";
    public static final String JSON_ARRAY_CATEGORIES = "categories";
}
