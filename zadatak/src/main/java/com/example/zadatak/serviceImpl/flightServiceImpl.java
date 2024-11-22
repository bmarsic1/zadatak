package com.example.zadatak.serviceImpl;

import com.example.zadatak.model.Flight;
import com.example.zadatak.model.FlightReq;
import com.example.zadatak.service.accessTokenService;
import com.example.zadatak.service.flightService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class flightServiceImpl implements flightService {

    private static final String BASE_URL = "https://test.api.amadeus.com/v2/shopping/flight-offers";
    private static final int TOKEN_EXPIRED_ERROR_CODE = 38192;

    private final accessTokenService tokenService;
    private final Map<FlightReq, List<Flight>> cachedFlights = new HashMap<>();

    public flightServiceImpl(accessTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public List<Flight> getFlights(FlightReq flightReq) {
        FlightReq params = flightReq.clone();
        if (cachedFlights.containsKey(params)) {
            System.out.println("Retrieved from local cache.");
            return cachedFlights.get(params);
        }

        List<Flight> flights = searchFlights(flightReq);
        if (flights != null) {
            cachedFlights.put(params, flights);
            System.out.println("Cached new search results.");
        }
        return flights;
    }

    private List<Flight> searchFlights(FlightReq flightReq) {
        System.out.println("Searching flights...");
        String accessToken = getValidAccessToken();

        try {
            String requestUri = buildUri(flightReq);
            HttpRequest request = buildHttpRequest(requestUri, accessToken);

            HttpResponse<String> response = sendHttpRequest(request);
            JSONObject jsonResponse = new JSONObject(response.body());

            if (isTokenExpired(jsonResponse)) {
                System.out.println("Token expired. Refreshing token...");
                tokenService.refreshToken();
                return searchFlights(flightReq);
            }

            return extractFlights(jsonResponse, flightReq);
        } catch (Exception e) {
            System.err.println("Error during flight search: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private String getValidAccessToken() {
        if (tokenService.getAccessToken() == null) {
            tokenService.refreshToken();
        }
        return tokenService.getAccessToken();
    }

    private String buildUri(FlightReq flightReq) throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(new URI(BASE_URL));

        for (Field field : flightReq.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(flightReq);
            if (value != null) {
                uriBuilder.queryParam(field.getName(), value.toString());
            }
        }

        return uriBuilder.build().encode().toString();
    }

    private HttpRequest buildHttpRequest(String uri, String accessToken) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Authorization", "Bearer " + accessToken)
                .build();
    }

    private HttpResponse<String> sendHttpRequest(HttpRequest request) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private boolean isTokenExpired(JSONObject jsonResponse) {
        if (jsonResponse.has("errors"))
            return
                    jsonResponse.getJSONArray("errors").getJSONObject(0).getInt("code") == TOKEN_EXPIRED_ERROR_CODE;
        return false;
    }

    private List<Flight> extractFlights(JSONObject jsonResponse, FlightReq flightReq) {
        if (jsonResponse.has("errors")) {
            System.err.println("API Error: " + jsonResponse.getJSONArray("errors").getJSONObject(0).getString("title"));
            return null;
        }

        List<Flight> flights = new ArrayList<>();
        for (Object data : jsonResponse.getJSONArray("data")) {
            JSONObject jsonFlight = (JSONObject) data;

            Integer numOfTransfersBack = flightReq.getReturnDate() != null
                    ? jsonFlight.getJSONArray("itineraries").getJSONObject(1).getJSONArray("segments").length() - 1
                    : null;

            flightReq.setCurrencyCode(jsonFlight.getJSONObject("price").getString("currency"));

            Flight flight = new Flight(
                    flightReq,
                    jsonFlight.getJSONArray("itineraries").getJSONObject(0).getJSONArray("segments").length() - 1,
                    numOfTransfersBack,
                    jsonFlight.getJSONObject("price").getFloat("total")
            );

            flights.add(flight);
        }

        return flights;
    }
}
