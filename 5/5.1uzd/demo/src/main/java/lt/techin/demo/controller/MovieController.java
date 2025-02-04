package lt.techin.demo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lt.techin.demo.model.Movie;
import lt.techin.demo.repository.MovieRepository;
import lt.techin.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api")
public class MovieController {

//  private List<Movie> movies = new ArrayList<>(
//          List.of(
//                  new Movie(1, "One Cat", "A1"),
//                  new Movie(2, "Two Cat", "B1"),
//                  new Movie(3, "Three Cat", "C1")
//          )
//  );

  // @Autowired // field injection (not recommended)
  private final MovieService movieService;

  @Autowired // constructor injection (very good)
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movieService.findAllMovies());
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable long id) {
//    if (id > movies.size() - 1) {
//      return ResponseEntity.notFound().build();// build() reikia nurodyti, nes jokio kūno negrąžiname
//    }
    Optional<Movie> foundMovie = movieService.findMovieById(id);

    if (foundMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(foundMovie.get());
  }

  @PostMapping("/movies")
  // Klaustukas - būtinas, nes grąžinamas tipas gali būti Book arba String
  public ResponseEntity<?> addMovie(@Valid @RequestBody Movie movie) {

    // Jei pavadinimas arba autorius tušti, grąžinti 400 Bad Request
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {

      //      return ResponseEntity.badRequest().build();
      // Grąžiname ir String, tam kad klientui būdų aiškiau, kas įvyko blogai
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or director cannot be empty");
    }

    Movie savedMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedMovie.getId())
                            .toUri())
            .body(savedMovie);
  }


  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @Valid @RequestBody Movie movie) {
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      //return ResponseEntity.badRequest().build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or director cannot be empty");
    }

    // Tikriname, ar paduotas indeksas id yra???
    if (movieService.existsMovieById(id)) {
      Movie movieFromDb = movieService.findMovieById(id).get();

      movieFromDb.setTitle(movie.getTitle());
      movieFromDb.setDirector(movie.getDirector());
      movieFromDb.setScreenings(movie.getScreenings());

      return ResponseEntity.ok(movieService.saveMovie(movieFromDb));
    }

    Movie savedMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            // Reikia daryti replacePath, nes kitap priklijuos rezultatą prie
                            // egzistuojančio galo; nurodome pilną kelią
                            .replacePath("/api/movies/{id}")
                            .buildAndExpand(savedMovie.getId())
                            .toUri())
            .body(movie);
  }

  @DeleteMapping("/movies/{id}")
  public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
    // Pastebime, jog tikrinimo sąkinius rašome viršuje. Tai vadinama Guard Clauses
//    if (index > movies.size() - 1) {
//      return ResponseEntity.notFound().build();
//    }
//
//    movies.remove(index);
    if (!movieService.existsMovieById(id)) {
      return ResponseEntity.notFound().build();
    }

    movieService.deleteMovieById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/movies/search/by-title")
  public ResponseEntity<List<Movie>> getMoviesByTitleContaining(@RequestParam String title) {
    return ResponseEntity.ok(movieService.findAllMoviesByTitleContaining(title));
  }

  @GetMapping("/movies/search/by-director")
  public ResponseEntity<List<Movie>> getMoviesByDirector(@RequestParam String director) {
    return ResponseEntity.ok(movieService.findAllMoviesByDirector(director));
  }

}

