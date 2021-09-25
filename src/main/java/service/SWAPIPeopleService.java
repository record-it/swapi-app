package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Person;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SWAPIPeopleService implements SWPeopleService{
    private static final HttpClient client = HttpClient.newHttpClient();
    public static final String SWAPI_DEV_API_PEOPLE = "https://swapi.dev/api/people/";

    @Override
    public Person findById(int id) {
        try {
            getPersonFromApi(SWAPI_DEV_API_PEOPLE + id);
        } catch (URISyntaxException e) {
            System.err.println("Błędny URL!");
        } {

        }
        return null;
    }

    private void getPersonFromApi(String url) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .whenCompleteAsync((response, exeption) -> {
                    if (exeption != null){
                        System.out.println("Problem z odpowiedzią z serwera!");
                    }
                    String json = response.body();
                    ObjectMapper mapper = new ObjectMapper();
                    try {

                        Person person = mapper.readValue(json, Person.class);
                        System.out.println(person);
                    } catch (JsonProcessingException e) {
                        System.err.println("Problem z parsowanie JSON!");
                        System.err.println(e.getMessage());
                    }
                });
    }
}
