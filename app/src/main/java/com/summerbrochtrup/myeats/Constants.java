package com.summerbrochtrup.myeats;

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
    public static final String YELP_QUERY_LAT_LNG = "ll";
    public static final String YELP_TERM_FOOD = "food";
    public static final String SERIALIZED_NAME_ADDRESS = "display_address";
    public static final String SERIALIZED_NAME_PHONE = "display_phone";
    public static final String SERIALIZED_NAME_URL = "mobile_url";
    public static final String SERIALIZED_NAME_IMAGE_URL = "image_url";
    public static final String SERIALIZED_NAME_ID = "id";

    /* Shared Preferences Constants */
    public static final String PREFERENCES_LOCATION_KEY = "location";

    /* Intent Extra Keys */
    public static final String EXTRA_KEY_RESTAURANT = "restaurant";
    public static final String EXTRA_KEY_SOURCE = "source";

    /* Source Keys */
    public static final String SOURCE_SAVED = "saved";
    public static final String SOURCE_FIND = "find";

    /* Extras */
    public static final String EMPTY_STRING = "";
}
