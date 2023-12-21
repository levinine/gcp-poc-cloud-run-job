package gcp.poc.cr.weatherdatapublishr.fetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherDataFetcher {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataFetcher.class);
    private static final String WEATHER_DATA_URI = "https://api.open-meteo.com/v1/forecast?latitude=45.25&longitude=19.84&current_weather=true";

    public static String getCurrentWeatherData() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEATHER_DATA_URI))
                .build();

        HttpResponse<String> response;
        try {
            logger.info(String.format("Getting weather data from %s", WEATHER_DATA_URI.split("v1")[0]));
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error(e.getClass().getName() + " occured when sending request to get weather data!");
            throw new RuntimeException(e);
        }

        return response.body();
    }
}
