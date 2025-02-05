package lt.techin.demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lt.techin.demo.validation.Theatre;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "screenings")
public class Screening {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Theatre
  private String theatre;

  private LocalDate date;
  private LocalTime time;


  public Screening(String theatre, LocalDate date, LocalTime time) {
    this.theatre = theatre;
    this.date = date;
    this.time = time;
  }

  public Screening() {
  }

  public String getTheatre() {
    return theatre;
  }

  public void setTheatre(String theatre) {
    this.theatre = theatre;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalTime getTime() {
    return time;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }

  public long getId() {
    return id;
  }

}