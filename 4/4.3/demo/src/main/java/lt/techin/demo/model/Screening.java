package lt.techin.demo.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "screenings")
public class Screening {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String theatre;
  private String date;
  private String time;


  public Screening(String theatre, String date, String time) {
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

  public long getId() {
    return id;
  }

}