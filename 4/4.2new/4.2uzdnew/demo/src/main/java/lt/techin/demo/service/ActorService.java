package lt.techin.demo.service;

import lt.techin.demo.model.Actor;
import lt.techin.demo.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorService {

  private final ActorRepository actorRepository;

  public ActorService(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public Actor saveActor(Actor actor) {
    return actorRepository.save(actor);
  }
}
