package service;

import model.Planet;
import repository.SWPlanetRepository;

import java.util.Optional;

public class SWAPIPlanetService implements SWPlanetService{
    private final SWPlanetRepository planetRepository;

    public SWAPIPlanetService(SWPlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @Override
    public Optional<Planet> findById(int id) {
        return planetRepository.findById(id);
    }
}
