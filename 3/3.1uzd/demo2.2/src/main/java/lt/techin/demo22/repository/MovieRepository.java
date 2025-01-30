package lt.techin.demo22.repository;

import lt.techin.demo22.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Tipas klasės, ir jos pirminis raktas <Movie, Long>
public interface MovieRepository extends JpaRepository<Movie, Long> {

//  // Derived query
//  boolean existsByTitle(String title);

//  // Derived query
//  Movie findByTitle(String title);
}
