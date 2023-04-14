package com.example.cinemaapp.rest.auth;
import com.example.cinemaapp.Auditorium.Auditorium;
import com.example.cinemaapp.rest.RetrofitSingleton;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


public class TokenManager {
    private static String accessToken;
    private static String refreshToken;
    private static User user;

    public static boolean login(String username, String password) throws IOException {
        var authService = RetrofitSingleton.getInstance().create(AuthService.class);
        var resp = authService.login(new LoginRequest(username, password)).execute();

        if (!resp.isSuccessful())
            return false;

        var tokens = resp.body();

        assert tokens != null;
        accessToken = tokens.accessToken;
        refreshToken = tokens.refreshToken;

        user = parseJwt(accessToken);
        System.out.println(user.role);
        if (!Objects.equals(user.role, "admin")){
            return false;
        }
        return true;
    }

    public static User parseJwt(String accessToken) {
        var chunks = accessToken.split("\\.");
        var decoder = Base64.getUrlDecoder();
        var body = new String(decoder.decode(chunks[1]));

        var gson = new Gson();
        User user = gson.fromJson(body, User.class);

        return user;
    }

    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

    public static String refreshToken() throws IOException {
        var authService = RetrofitSingleton.getInstance().create(AuthService.class);
        var resp = authService.refresh(new RefreshToken(refreshToken)).execute();

        if (!resp.isSuccessful())
            throw new RuntimeException("failed to refresh access token");

        return resp.body();
    }

    public static String getAccessToken() throws IOException {
        var adjustedExp = user.exp - 30;

        if (getUnixTime() > adjustedExp) {
            accessToken = refreshToken();
        }

        return "Bearer " + accessToken;
    }
}
