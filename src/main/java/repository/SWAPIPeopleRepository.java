package repository;

import api.ApiHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Person;
import model.Persons;

import java.net.URISyntaxException;
import java.util.*;

public class SWAPIPeopleRepository implements SWPeopleRepository {
    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String SWAPI_DEV_API_PEOPLE = "https://swapi.dev/api/people/";
    private Map<Integer, Person> cache = new HashMap<>();
    private List<Person> personsList = new ArrayList<>();
    @Override
    public List<Person> findAll() {
        if (personsList.isEmpty()) {
            try {
                ApiHelper.getObjectFromApi(SWAPI_DEV_API_PEOPLE, 0, body -> {
                    try {
                        Persons persons = MAPPER.readValue(body, Persons.class);
                        personsList.addAll(persons.getResults());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
            } catch (URISyntaxException e) {
                System.err.println(e.getMessage());
            }
        }
        return personsList;
    }

    @Override
    public Optional<Person> findById(int id) {
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        } else {
            try {
                ApiHelper.getObjectFromApi(SWAPI_DEV_API_PEOPLE + id, id, body -> {
                    try {
                        Person person = MAPPER.readValue(body, Person.class);
                        cache.put(id, person);
                        System.out.println("Szukana osoba jest już dostępna!");
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
