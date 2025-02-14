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
  @Size(min = 4, max = 150, message = "Must be between 4 and 150 characters.")
  // Jau suprantame, jog pattern reikalauja mažiausiai 2 simbolių; jei pridėsime prie
  // @Size min = 2, pjausis su Pattern jei jis yra po @Size .
  //@Pattern .
  private String title; // Duomenų bazėje: VARCHAR[150]
  //Validation has ended for title? Is there a wall between here and @Pattern.

  @NotNull
  @Size(min = 4, max = 50, message = "Must be between 4 and 50 characters.")
  @Pattern(regexp = "^[A-Z][a-z]+$", message = "Must start with uppercase letter, " +
          "and continue as lowercase. " + "Also not contain any numbers.")
  //@Pattern(regexp = "^[A-Z][a-z]+$" is best use even if Exercise is asking not to use numbers.
  // Because @Pattern(regexp = "^[A-Z][a-z][^0-9]+$" is kinda correct but "^[A-Z][a-z]+$" already states:
  //This is what should be included inside [] and anything else is excluded inside,
  //like numbers, symbols or other stuff.
  //For Future! @Pattern(regexp = "(^[A-Z][a-z]+$)|([^0-9]+$)", can happen, but it is enough to do one regexp
  // I mean not using OR | symbol. Like @Pattern(regexp = "^[A-Z][a-z][^0-9]+$"
  //
  //Different ways of writing below.
//  @Pattern(regexp = "(^[A-Z][a-z]+$)|([^0-9]+$)", message = "Must start with uppercase letter, " +
//          "and continue as lowercase. " + "Also not contain any numbers.")
  //@Pattern(regexp = "^[A-Z][a-z]+$", message = "Must start with uppercase letter, and continue as lowercase")
  //@Pattern(regexp = "[^0-9]+$", message = "director must not contain numbers")
  //
  //Kodel uzkomentojame? Nes @Pattern(regexp = "[^0-9]+$"
  // pjausis su above @Pattern(regexp = "^[A-Z][a-z]+$". Du @Pattern vienu metu ne per gerai.
  private String director; // VARCHAR [50]

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
