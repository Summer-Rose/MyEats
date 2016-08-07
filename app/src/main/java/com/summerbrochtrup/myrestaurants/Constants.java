package com.summerbrochtrup.myrestaurants;

public class Constants {
    /* Yelp Constants */
    public static final String YELP_CONSUMER_KEY = BuildConfig.YELP_CONSUMER_KEY;
    public static final String YELP_CONSUMER_SECRET = BuildConfig.YELP_CONSUMER_SECRET;
    public static final String YELP_TOKEN = BuildConfig.YELP_TOKEN;
    public static final String YELP_TOKEN_SECRET = BuildConfig.YELP_TOKEN_SECRET;
    public static final String YELP_BASE_URL = "https://api.yelp.com";
    public static final String YELP_GET_SEARCH = "/v2/search";
    public static final String YELP_QUERY_TERM = "term";
    public static final String YELP_QUERY_LOCATION = "location";
    public static final String YELP_TERM_FOOD = "food";
    public static final String SERIALIZED_NAME_ADDRESS = "display_address";
    public static final String SERIALIZED_NAME_PHONE = "display_phone";
    public static final String SERIALIZED_NAME_URL = "mobile_url";
    public static final String SERIALIZED_NAME_IMAGE_URL = "image_url";
    public static final String SERIALIZED_NAME_ID = "id";

    /* Firebase Constants*/
    public static final String FIREBASE_CHILD_RESTAURANTS = "restaurants";
    public static final String FIREBASE_QUERY_INDEX = "index";
    public static final String FIREBASE_CHILD_IMAGEURL = "imageUrl";

    /* Shared Preferences Constants */
    public static final String PREFERENCES_LOCATION_KEY = "location";
    public static final String EXTRA_KEY_POSITION = "position";
    public static final String EXTRA_KEY_RESTAURANTS = "restaurants";

    /* Intent Extra Keys */
    public static final String KEY_SOURCE = "source";
    public static final String SOURCE_SAVED = "saved";
    public static final String SOURCE_FIND = "find";

    /* Fonts */
    public static final String EMPTY_STRING = "";
    public static final String FONT_OSTRICH_REGULAR = "fonts/ostrich-regular.ttf";

    /* Extras */
    public static final String HTTP_FILTER = "http";
    public static final String BITMAP_EXTRA = "data";
    public static final String INDEX_NOT_SPECIFIED = "not_specified";
}
