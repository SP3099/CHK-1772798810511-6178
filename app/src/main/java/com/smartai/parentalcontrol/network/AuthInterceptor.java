package com.smartai.parentalcontrol.network;

import android.content.Context;
import android.content.SharedPreferences;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class AuthInterceptor implements Interceptor {

    private static final String PREF_NAME = "ParentalControlPrefs";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static Context appContext;

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        
        String token = getAuthToken();
        
        if (token != null && !token.isEmpty()) {
            Request newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .build();
            return chain.proceed(newRequest);
        }
        
        return chain.proceed(originalRequest);
    }

    private String getAuthToken() {
        if (appContext == null) {
            return null;
        }
        
        SharedPreferences prefs = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_AUTH_TOKEN, null);
    }

    public static void saveAuthToken(String token) {
        if (appContext == null) {
            return;
        }
        
        SharedPreferences prefs = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_AUTH_TOKEN, token).apply();
    }

    public static void clearAuthToken() {
        if (appContext == null) {
            return;
        }
        
        SharedPreferences prefs = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_AUTH_TOKEN).apply();
    }
}
