package lt.techin.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "actors")
public class Actor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Size(min = 4, max = 50, message = "Must be between 4 and 50 characters.")
  private String name;


  public Actor(String name) {
    this.name = name;
  }

  public Actor() {
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}