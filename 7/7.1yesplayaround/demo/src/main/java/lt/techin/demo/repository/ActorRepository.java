package lt.techin.demo.repository;

import lt.techin.demo.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {


}
