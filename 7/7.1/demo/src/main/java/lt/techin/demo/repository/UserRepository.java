package lt.techin.demo.repository;

import lt.techin.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// Tipas klasės, ir jos pirminis raktas <User, Long>
public interface UserRepository extends JpaRepository<User, Long> {

  // Šis metodas ras knygas, jei pavadinimas yra vienas prie vieno
  Optional<User> findByUsername(String username);

  // Šis metodas ras knygas, jei pavadinimas yra vienas prie vieno
  List<User> findAllByUsername(String username);

  //=========================

  Optional<User> findByPassword(String password);

  //May not be needed.
  // Derived query
//  List<User> findAllUsersByUsernameContaining(String username);


}
