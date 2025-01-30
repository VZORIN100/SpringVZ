package lt.techin.demo22.service;

import lt.techin.demo22.model.Movie;
import lt.techin.demo22.repository.MovieRepository;
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

//  public boolean existsMovieById(long id) {
//    return movieRepository.existsById(id);
//  }

//  public Optional<Movie> findMovieById(long id) {
//    return movieRepository.findById(id);
//  }

//  public Movie saveMovie(Movie movie) {
//    return movieRepository.save(movie);
//  }

//  public void deleteMovieById(long id) {
//    movieRepository.deleteById(id);
//  }

//  public boolean existsMovieByTitle(String title) {
//    // Call custom derived query
//    return movieRepository.existsByTitle(title);
//  }

//  public Movie findMovieByTitle(String title) {
//    return movieRepository.findByTitle(title);
//  }
}
