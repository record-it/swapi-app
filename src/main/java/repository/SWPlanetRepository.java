package repository;

import model.Planet;

import java.util.Optional;

public interface SWPlanetRepository {
    Optional<Planet> findById(int id);
}
