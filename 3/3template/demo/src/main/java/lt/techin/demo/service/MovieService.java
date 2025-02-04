package lt.techin.demo.service;

import lt.techin.demo.model.Movie;
import lt.techin.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> findAllMovies() {
    return movieRepository.findAll();
  }

  public boolean existsMovieById(long id) {
    return movieRepository.existsById(id);
  }

  public Optional<Movie> findMovieById(long id) {
    return movieRepository.findById(id);
  }

  public Movie saveMovie(Movie movie) {
    return movieRepository.save(movie);
  }

  public void deleteMovieById(long id) {
    movieRepository.deleteById(id);
  }

  public List<Movie> findAllMoviesByTitleContaining(String title) {
    // Calls a derived query
    return movieRepository.findAllByTitleContaining(title);
  }

  public List<Movie> findAllMoviesByDirector(String director) {
    return movieRepository.findAllByDirector(director);
  }
//Was in previous version 3.2uzd.
//  public boolean existsMovieByTitle(String title) {
//    // Call custom derived query
//    return movieRepository.existsByTitle(title);
//  }
//
//  public Movie findMovieByTitle(String title) {
//    return movieRepository.findByTitle(title);
//  }
}