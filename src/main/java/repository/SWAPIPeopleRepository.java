package repository;

import api.ApiHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Person;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class SWAPIPeopleRepository implements SWPeopleRepository{
    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String SWAPI_DEV_API_PEOPLE = "https://swapi.dev/api/people/";
    private Map<Integer, Person> cache = new HashMap<>();

    @Override
    public List<Person> findAll() {
        return null;
    }

    @Override
    public Optional<Person> findById(int id) {
        if (cache.containsKey(id)){
            return Optional.of(cache.get(id));
        } else{
            try {
                ApiHelper.getObjectFromApi(SWAPI_DEV_API_PEOPLE + id, id, body -> {
                    try {
                        Person person = MAPPER.readValue(body, Person.class);
                        cache.put(id, person);
                    } catch (JsonProcessingException e) {
                        System.err.println(e.getMessage());
                    }
                });
            } catch (URISyntaxException e) {
                System.err.println(e.getMessage());
            }
            return Optional.empty();
        }
    }
}
