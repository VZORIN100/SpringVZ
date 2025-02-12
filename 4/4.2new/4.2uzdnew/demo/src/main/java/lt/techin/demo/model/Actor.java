package lt.techin.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "actors")
public class Actor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

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
