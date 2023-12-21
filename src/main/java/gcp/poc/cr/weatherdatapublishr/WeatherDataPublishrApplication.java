package gcp.poc.cr.weatherdatapublishr;

import gcp.poc.cr.weatherdatapublishr.fetcher.WeatherDataFetcher;
import gcp.poc.cr.weatherdatapublishr.publisher.PubSubPublisher;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class WeatherDataPublishrApplication implements ApplicationContextAware {

    private static ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(WeatherDataPublishrApplication.class, args);

        String weatherData = WeatherDataFetcher.getCurrentWeatherData();
        try {
            PubSubPublisher.publish(weatherData);
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        ((ConfigurableApplicationContext) context).close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
