package lt.techin.demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
import lt.techin.demo.validation.Theatre;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "screenings")
public class Screening {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  //  @NotNull
//  @Size(min = 4, max = 150, message = "Must be between 4 and 150 characters.")
  @Theatre
  private String theatre;

  @NotNull
  @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Must match yyyy-mm-dd standard")
  private String date;

  @NotNull
  @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Must match hh-mm-ss standard")
  private String time;


  public Screening(String theatre, String date, String time) {
    this.theatre = theatre;
    this.date = date;
    this.time = time;
  }

  public Screening() {
  }

  public long getId() {
    return id;
  }

  public String getTheatre() {
    return theatre;
  }

  public void setTheatre(String theatre) {
    this.theatre = theatre;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

}