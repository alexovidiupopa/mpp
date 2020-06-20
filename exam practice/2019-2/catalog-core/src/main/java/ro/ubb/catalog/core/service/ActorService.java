package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Actor;

import java.util.List;

public interface ActorService {

    List<Actor> getAllActors();
    List<Actor> getAllAvailableActors();
}
