package lt.techin.demo.dto;

import lt.techin.demo.model.Movie;
import lt.techin.demo.model.Screening;
import lt.techin.demo.model.Actor;

import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.LocalTime;

public class MovieMapper {

  public static List<MovieDTO> toMovieDTOList(List<Movie> movies) {
    List<MovieDTO> result = movies.stream()
            .map(movie -> new MovieDTO(movie.getId(), movie.getTitle(), movie.getDirector(),
                    movie.getScreenings(), movie.getActors()))
            .toList();

    return result;
  }

  //000000000000000000000000000000000000000


  public static MovieDTO toMovieDTO(Movie movie) {
    return new MovieDTO(movie.getId(), movie.getTitle(), movie.getDirector(),
            movie.getScreenings(), movie.getActors());
  }

  public static Movie toMovie(MovieDTO movieDTO) {
    Movie movie = new Movie();
    movie.setTitle(movieDTO.title());
    movie.setDirector(movieDTO.director());
    movie.setScreenings(movieDTO.screenings());
    movie.setActors(movieDTO.actors());

    return movie;
  }

  public static void updateMovieFromDTO(Movie movie, MovieDTO movieDTO) {
    movie.setTitle(movieDTO.title());
    movie.setDirector(movieDTO.director());
    movie.setScreenings(movieDTO.screenings());
    movie.setActors(movieDTO.actors());
  }
}
