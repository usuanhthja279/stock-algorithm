package com.trading.stocks.util;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.openqa.selenium.Cookie;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommonUtil {
    public static Map<String, String> getQueryMap(String query) {
        Map<String, String> map = new HashMap<>();
        String[] queryParts = query.split("&");
        for (String queryPart : queryParts) {
            String[] keyValue = queryPart.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    public static Cookie getCookie(JSONObject cookieObject) {
        // Parse the JSON cookies
            String name = cookieObject.getString("name");
            String value = cookieObject.getString("value");
            String domain = cookieObject.getString("domain");
            String path = cookieObject.optString("path", "/");
            boolean secure = cookieObject.getBoolean("secure");
            boolean httpOnly = cookieObject.getBoolean("httpOnly");

            // Convert expirationDate to Date
            long expirationDate = cookieObject.optLong("expirationDate", -1);
            Date expiry = expirationDate > 0 ? new Date((long) (expirationDate * 1000)) : null;

            // Create a Cookie object
            Cookie cookie = new Cookie.Builder(name, value)
                    .domain(domain)
                    .path(path)
                    .isSecure(secure)
                    .isHttpOnly(httpOnly)
                    .expiresOn(expiry)
                    .build();

            // Add the cookie to WebDriver

        // Refresh the page to apply the cookies
        return cookie;

    }


}
