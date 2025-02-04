package lt.techin.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "movies") // Jei to neparašysiu, lentelė bus traktuojama kaip „book“
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // SQL kode, tai yra AUTO_INCREMENT
  private long id; // Duomenų bazėje: BIGINT

  @NotNull
  @Size(max = 150, message = "Maximum 150 characters")
  @Size(min = 2, message = "Minimum 2 characters")

  // Jau suprantame, jog pattern reikalauja mažiausiai 2 simbolių; jei pridėsime prie
  // @Size min = 2, pjausis su Pattern jei jis yra po @Size .
  //@Pattern .
  private String title; // Duomenų bazėje: VARCHAR
  //Validation has ended for title? Is there a wall between here and @Pattern.

  @Pattern(regexp = "^[A-Z][a-z]+$", message = "director must start with uppercase letter, and continue as" +
          " lowercase")
  @Pattern(regexp = "[^0-9]+$", message = "director must not contain numbers")
  private String director; // VARCHAR [^0-9]

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
