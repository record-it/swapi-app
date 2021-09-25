package repository;

import api.ApiHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Person;
import model.Planet;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SWAPIPlanetRepository implements SWPlanetRepository{
    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String SWAPI_DEV_API_PLANET = "https://swapi.dev/api/planets/";
    private Map<Integer, Planet> cache = new HashMap<>();
    @Override
    public Optional<Planet> findById(int id) {
        if (cache.containsKey(id)){
            return Optional.of(cache.get(id));
        } else{
            try {
                ApiHelper.getObjectFromApi(SWAPI_DEV_API_PLANET + id, id, body -> {
                    try {
                        Planet planet = MAPPER.readValue(body, Planet.class);
                        cache.put(id, planet);
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
