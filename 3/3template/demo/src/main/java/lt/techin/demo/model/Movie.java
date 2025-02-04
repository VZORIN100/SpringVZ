package lt.techin.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies") // Jei to neparašysiu, lentelė bus traktuojama kaip „book“
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // SQL kode, tai yra AUTO_INCREMENT
  private long id; // Duomenų bazėje: BIGINT

  private String title; // Duomenų bazėje: VARCHAR
  private String director; // VARCHAR

  // id nereikia, nes jis generuojamas
  public Movie(String title, String director) {
    //this.id = id;
    this.title = title;
    this.director = director;
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

}
