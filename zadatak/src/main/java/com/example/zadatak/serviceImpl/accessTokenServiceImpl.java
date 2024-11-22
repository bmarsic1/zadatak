package com.example.zadatak.serviceImpl;

import com.example.zadatak.service.accessTokenService;
import com.example.zadatak.utility.GlobalConstants;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class accessTokenServiceImpl implements accessTokenService {

    private String accessToken;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    private void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void refreshToken() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String body = "grant_type=client_credentials&client_id=" + GlobalConstants.clientId + "&client_secret=" + GlobalConstants.clientSecret;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://test.api.amadeus.com/v1/security/oauth2/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject obj = new JSONObject(response.body());
            setAccessToken(obj.getString("access_token"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
