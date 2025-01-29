package lt.techin.demo22.controller;

import lt.techin.demo22.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {

  private List<Movie> movies = new ArrayList<>(
          List.of(
                  new Movie(1, "One Bird", "A"),
                  new Movie(2, "Two Bird", "B"),
                  new Movie(3, "Three Bird", "C")
          )
  );

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getBooks() {
    return ResponseEntity.ok(movies);
  }

  @GetMapping("/movies/{index}")
  public ResponseEntity<Movie> getMovie(@PathVariable int index) {
    if (index > movies.size() - 1) {
      return ResponseEntity.notFound().build();// build() reikia nurodyti, nes jokio kūno negrąžiname
    }

    return ResponseEntity.ok(movies.get(index));
  }

  @PostMapping("/movies")
  public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
    // Jei pavadinimas arba autorius tušti, grąžinti 400 Bad Request
    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    movies.add(movie);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{index}")
                            .buildAndExpand(movies.size() - 1)
                            .toUri())
            .body(movie);
  }

  @GetMapping("/movies/search") // /books neveiks, nes užimta
  public ResponseEntity<Movie> getBookByTitle(@RequestParam String title) {
    Optional<Movie> foundMovie = movies.stream()
            .filter(b -> b.getTitle().contains(title))
            .findFirst();

    if (foundMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(foundMovie.get());
  }


}
