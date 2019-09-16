package com.example.multisensorapp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Header;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyJsonObjectRequest extends JsonObjectRequest {

    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    public MyJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public MyJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        /*
        Cache.Entry entry = getCacheEntry();
        Map<String, String> headers = entry;
        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
*/
        //checkSessionCookie(headers);
        Cache.Entry entry = getCacheEntry();
        //checkSessionCookie(entry);
        Header header = entry.allResponseHeaders.get(5);
/*
        String cookie = header.getValue();
        if (cookie.length() > 0) {
            String[] splitCookie = cookie.split(";");
            String[] splitSessionId = splitCookie[0].split("=");
            cookie = splitSessionId[1];
            Singleton.getInstance().setToken(cookie);

        }*/
        Singleton.getInstance().setToken(header.getValue());
        //Singleton.getInstance().setHeaders(headers);

        return super.getHeaders();
    }

/*


    public final void checkSessionCookie(Cache.Entry headers) {
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                Singleton.getInstance().setToken(cookie);

            }
        }
    }*/

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = Singleton.getInstance().getToken();
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }

}
