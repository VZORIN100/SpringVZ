package lt.techin.demo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lt.techin.demo.model.Movie;
import lt.techin.demo.model.Actor;
import lt.techin.demo.repository.ActorRepository;
import lt.techin.demo.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api")
public class ActorController {

  private final ActorService actorService;

  // @Autowired nebūtina rašyti ant konstruktoriaus, KAI yra 1 konstruktorius
  // Aš rekomenduoju visada rašyti
  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @GetMapping("/actors")
  public ResponseEntity<List<Actor>> getActors() {
    return ResponseEntity.ok(actorService.findAllActors());
  }

  @GetMapping("/actors/{id}")
  public ResponseEntity<?> getBook(@PathVariable @Min(1) long id) {
    // Gal geriau validuoti su if, vietoj @Min
//    if (id < 1) {
//      Map<String, String> response = new HashMap<>();
//      response.put("id", "Cannot be lower than 1");
//
//      return ResponseEntity.badRequest().body(response);
//    }

    Optional<Actor> foundActor = actorService.findActorById(id);

    if (foundActor.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(foundActor.get());
  }

  @PostMapping("/actors")
  public ResponseEntity<?> addActor(@Valid @RequestBody Actor actor) {

    Actor savedActor = actorService.saveActor(actor);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedActor.getId())
                            .toUri())
            .body(savedActor);
  }

  @PutMapping("/actors/{id}")
  public ResponseEntity<?> updateActor(@PathVariable long id, @RequestBody Actor actor) {
    if (actor.getName().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name cannot be empty");
    }

    if (actorService.existsActorById(id)) {
      Actor actorFromDb = actorService.findActorById(id).get();

      actorFromDb.setName(actor.getName());
//      bookFromDb.setAuthor(book.getAuthor());
//      bookFromDb.setReviews(book.getReviews());
//      bookFromDb.setCategories(book.getCategories());

      return ResponseEntity.ok(actorService.saveActor(actorFromDb));
    }

    Actor savedActor = actorService.saveActor(actor);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            // Reikia daryti replacePath, nes kitap priklijuos rezultatą prie
                            // egzistuojančio galo; nurodome pilną kelią
                            .replacePath("/api/actors/{id}")
                            .buildAndExpand(savedActor.getId())
                            .toUri())
            .body(actor);
  }

  @DeleteMapping("/actors/{id}")
  public ResponseEntity<Void> deleteActor(@PathVariable long id) {
    // Pastebime, jog tikrinimo sąkinius rašome viršuje. Tai vadinama Guard Clauses
//    if (index > movies.size() - 1) {
//      return ResponseEntity.notFound().build();
//    }
//
//    movies.remove(index);
    if (!actorService.existsActorById(id)) {
      return ResponseEntity.notFound().build();
    }

    actorService.deleteActorById(id);
    return ResponseEntity.noContent().build();
  }

  //POST like in Teacher Example in BookController is a bit different.
  //BookController has commented out section with Map<String, String> result = new HashMap<>();
  //
  //Do I use mine with simple "if", one with "HashMap", or nothing like in Teacher Example.
  //I used nothing like in Teacher Example.
  //Below is old version I used until now. I comment out just in case.

//  @PostMapping("/actors")
//  public ResponseEntity<?> addActor(@Valid @RequestBody Actor actor) {
//
//    if (actor.getName().isEmpty()) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name cannot be empty");
//    }
//
//    Actor savedActor = actorService.saveActor(actor);
//
//    return ResponseEntity.created(
//                    ServletUriComponentsBuilder.fromCurrentRequest()
//                            .path("/{id}")
//                            .buildAndExpand(savedActor.getId())
//                            .toUri())
//            .body(savedActor);
//  }

  //==========================================================================================

  //Below is @PostMapping with two different if techniques. Normal and HashMap.
  //Either can be used?
  //Maybe.
  //I leave just in case.

//  @PostMapping("/actors")
//  public ResponseEntity<?> addActor(@Valid @RequestBody Actor actor) {
//
////    if (actor.getName().isEmpty()) {
////      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name cannot be empty");
////    }
//
//    //====================================Either above works.
//    //
//    //
//    //=========================Or below works.
//
////    if (actor.getName().isEmpty() || actor.getName().isEmpty()) {
////      Map<String, String> result = new HashMap<>();
////      result.put("name", "Cannot be empty");
////
////      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
////    }
//    //
//    //==========================Or nothing works.
//
//    Actor savedActor = actorService.saveActor(actor);
//
//    return ResponseEntity.created(
//                    ServletUriComponentsBuilder.fromCurrentRequest()
//                            .path("/{id}")
//                            .buildAndExpand(savedActor.getId())
//                            .toUri())
//            .body(savedActor);
//  }
}
