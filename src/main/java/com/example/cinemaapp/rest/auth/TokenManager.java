package com.example.cinemaapp.rest.auth;
import com.example.cinemaapp.rest.RetrofitSingleton;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
public class TokenManager {
    private static String accessToken;
    private static String refreshToken;
    private static User user;

    public static int login(String username, String password) throws IOException {
        var authService = RetrofitSingleton.getInstance().create(AuthService.class);
        var resp = authService.login(new LoginRequest(username, password)).execute();

        if (!resp.isSuccessful())
            return 1; //1 if response is unsuccessful

        var tokens = resp.body();

        assert tokens != null;
        accessToken = tokens.accessToken;
        refreshToken = tokens.refreshToken;

        user = parseJwt(accessToken);
        if (!Objects.equals(user.role, "admin")){
            return 2;//2 if user's role is not admin
        }
        return 3;//3 if response is successful and the user is an admin
    }
    public static User parseJwt(String accessToken) {
        var chunks = accessToken.split("\\.");
        var decoder = Base64.getUrlDecoder();
        var body = new String(decoder.decode(chunks[1]));
        var gson = new Gson();
        return gson.fromJson(body, User.class);
    }
    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }
    public static String refreshToken() throws IOException {
        var authService = RetrofitSingleton.getInstance().create(AuthService.class);
        var resp = authService.refresh(new RefreshToken(refreshToken)).execute();
        if (!resp.isSuccessful())
            throw new RuntimeException("failed to refresh access token");
        return resp.body().accessToken;
    }
    public static String getAccessToken() throws IOException {
        var adjustedExp = user.exp - 30;
        if (getUnixTime() > adjustedExp) {
            accessToken = refreshToken();
        }
        return "Bearer " + accessToken;
    }
}