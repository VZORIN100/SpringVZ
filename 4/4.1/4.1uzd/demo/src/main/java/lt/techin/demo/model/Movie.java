package lt.techin.demo.model;

import jakarta.persistence.*;

import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "movies") // Jei to neparašysiu, lentelė bus traktuojama kaip „book“
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // SQL kode, tai yra AUTO_INCREMENT
  private long id; // Duomenų bazėje: BIGINT

  private String title; // Duomenų bazėje: VARCHAR
  private String director; // VARCHAR

  // Galiu ir vaikinėje lentelėje išsaugoti įrašą - review
  @OneToMany(cascade = CascadeType.ALL)

  // Neuždėjus šios anotacijos, Hibernate bandys pats kurti tarpinę lentelę
  @JoinColumn(name = "movie_id")
  private List<Screening> screenings;


  // id nereikia, nes jis generuojamas
  public Movie(String title, String director, List<Screening> screenings) {
    //this.id = id;
    this.title = title;
    this.director = director;
    this.screenings = screenings;
  }


  // Konstruktorius be argumentų yra reikalingas,
  // tam kad Hibernate galėtų veikti
  public Movie() {

  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  // getId() reikalingas, kad Jackson galėtu serialize/
  // deserialize id
  public long getId() {
    return id;
  }

  public List<Screening> getScreenings() {
    return screenings;
  }

  public void setScreenings(List<Screening> screenings) {
    this.screenings = screenings;
  }

}
