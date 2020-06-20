package ro.ubb.catalog.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Actor;
import ro.ubb.catalog.core.repository.ActorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService{
    @Autowired
    private ActorRepository actorRepository;

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public List<Actor> getAllAvailableActors() {
        return actorRepository.findAll().stream().filter(actor->actor.getMovie_id()==null).collect(Collectors.toList());
    }
}
