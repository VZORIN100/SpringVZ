package lt.techin.demo.repository;

import lt.techin.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//import java.time.LocalDate;
//import java.time.LocalTime;

// Tipas klasės, ir jos pirminis raktas <Movie, Long>
public interface MovieRepository extends JpaRepository<Movie, Long> {

  // Derived query
  List<Movie> findAllByTitleContaining(String title);

  // Šis metodas ras filmus, jei pavadinimas yra vienas prie vieno
  List<Movie> findAllByTitle(String title);

  // ?1 simbolizuoja pirmą metodo parametrą, t. y. director
  @NativeQuery(value = "SELECT * FROM movies WHERE director = ?1")
  List<Movie> findAllByDirector(String director);

  // JPQL query; veikia taip pat, kaip @NativeQuery
  // Sintaksė remiasi būtent Java kodu
  // @Query("select b from Book b where b.author = ?1")
  // List<Book> findAllByAuthor(String author);

  //Previous 3.2uzd.
//  // Derived query
//  boolean existsByTitle(String title);
//
//  // Derived query
//  Movie findByTitle(String title);
}
