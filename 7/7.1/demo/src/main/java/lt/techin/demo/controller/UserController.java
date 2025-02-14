package lt.techin.demo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lt.techin.demo.dto.MovieDTO;
import lt.techin.demo.dto.MovieMapper;
import lt.techin.demo.model.Movie;
import lt.techin.demo.model.User;
import lt.techin.demo.repository.MovieRepository;
import lt.techin.demo.repository.UserRepository;
import lt.techin.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
import lt.techin.demo.dto.UserDTO;
import lt.techin.demo.dto.UserMapper;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  // Bean'as egzistuojas kontekste, dėl to galime
  // ir @Autowire
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }


  @GetMapping("/users")
  public ResponseEntity<List<UserDTO>> getUsers() {
    return ResponseEntity.ok(UserMapper.toUserDTOList(userService.findAllUsers()));
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable @Min(1) long id) {


    Optional<User> foundUser = userService.findUserById(id);

    if (foundUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(UserMapper.toUserDTO(foundUser.get()));
  }

  @PostMapping("/users")
  public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {

    User user = UserMapper.toUser(userDTO);

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userService.saveUser(user);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
            .body(UserMapper.toUserDTO(savedUser));
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable long id, @Valid @RequestBody UserDTO userDTO) {
//    if (movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
//      //return ResponseEntity.badRequest().build();
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or director cannot be empty");
//    }

    // Tikriname, ar paduotas indeksas id yra???
    if (userService.existsUserById(id)) {
      User userFromDb = userService.findUserById(id).get();

      //"Not very good" was written here in Teacher Example demo-6-diena.
      //Why? I don't know. Maybe no longer needed since other stuff was added.
//      movieFromDb.setTitle(movie.getTitle());
//      movieFromDb.setDirector(movie.getDirector());
//      movieFromDb.setScreenings(movie.getScreenings());

      UserMapper.updateUserFromDTO(userFromDb, userDTO);

      userService.saveUser(userFromDb);

      return ResponseEntity.ok(userDTO);
    }

    User savedUser = userService.saveUser(UserMapper.toUserDTO(userDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            // Reikia daryti replacePath, nes kitap priklijuos rezultatą prie
                            // egzistuojančio galo; nurodome pilną kelią
                            .replacePath("/api/users/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
            .body(userDTO);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable long id) {
    // Pastebime, jog tikrinimo sąkinius rašome viršuje. Tai vadinama Guard Clauses
//    if (index > movies.size() - 1) {
//      return ResponseEntity.notFound().build();
//    }
//
//    movies.remove(index);
    if (!userService.existsUserById(id)) {
      return ResponseEntity.notFound().build();
    }

    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }

  //There was no DTO in demo-6-diena, but I could add it anyway.
  // But firstly a findAllUsersByUsernameContaining method needs to be created in UserService.
  //Also, @GetMapping("/users/search/by-username") needs to be altered in some way.
  //I altered it for now. Way below is the version how it was before.
  //But for now maybe it is not necessary.
  //
//  @GetMapping("/users/search/by-username")
//  public ResponseEntity<List<UserDTO>> getUsersByUsernameContaining(@RequestParam @Size(min = 2) String username) {
//
//    Optional<User> foundUser = userService.findUserByUsername(username);
//
//    if (foundUser.isEmpty()) {
//      return ResponseEntity.notFound().build();
//    }
//
//    return ResponseEntity.ok(userService.findAllUsersByUsernameContaining(username));
//  }

  //Past version
//  @GetMapping("/users/search/by-username")
//  public ResponseEntity<List<UserDTO>> getUsersByUsernameContaining(@RequestParam @Size(min = 2) String username) {
//    return ResponseEntity.ok(userService.findAllUsersByUsernameContaining(username));
//  }

//================================================================================================
  //Below is @PostMapping("/users") with DTO added
//  @PostMapping("/users")
//  // Kai kursite patys, būtinai naudokite DTO!
//  public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
//
//    // Slatpažodis yra šifruojamas prieš saugant į duomenų bazę
//    //?????????????Do I need DTO here????????????????????????????????????????????????????????
//    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//
//    User savedUser = userService.saveUser(UserMapper.toUserDTO(userDTO));
//
//    return ResponseEntity.created(
//                    ServletUriComponentsBuilder.fromCurrentRequest()
//                            .path("/{id}")
//                            .buildAndExpand(savedUser.getId())
//                            .toUri())
//            .body(UserMapper.toUserDTO(savedUser));
//  }


  //====================================================================================
  //Below is @PostMapping("/users") without DTO added CURRENT VERSION.
//  @PostMapping("/users")
//  // Kai kursite patys, būtinai naudokite DTO!
//  public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
//
//    // Slatpažodis yra šifruojamas prieš saugant į duomenų bazę
//    //?????????????Do I need DTO here????????????????????????????????????????????????????????
//    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//
//    User savedUser = userService.saveUser(UserMapper.toUserDTO(userDTO));
//
//    return ResponseEntity.created(
//                    ServletUriComponentsBuilder.fromCurrentRequest()
//                            .path("/{id}")
//                            .buildAndExpand(savedUser.getId())
//                            .toUri())
//            .body(UserMapper.toUserDTO(savedUser));
//  }


  //Below is @PostMapping("/users") without DTO added WAY PAST VERSION.
//  @PostMapping("/users")
//  // Kai kursite patys, būtinai naudokite DTO!
//  public ResponseEntity<User> addUser(@RequestBody User user) {
//
//    // Slatpažodis yra šifruojamas prieš saugant į duomenų bazę
//    user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//    User savedUser = userService.saveUser(user);
//
//    return ResponseEntity.created(
//                    ServletUriComponentsBuilder.fromCurrentRequest()
//                            .path("/{id}")
//                            .buildAndExpand(savedUser.getId())
//                            .toUri())
//            .body(savedUser);
//  }


  //Below is @PostMapping("/users") WITH DTO added CURRENT VERSION AND getpassword SHOULD WORK.
//  @PostMapping("/users")
//  public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
//
//    // Convert the UserDTO to a User entity
//    User user = UserMapper.toUser(userDTO);
//
//    // Encode the password here, before saving to the database
//    user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//    // Save the user
//    User savedUser = userService.saveUser(user);
//
//    // Return the response with the saved user data in a DTO
//    return ResponseEntity.created(
//                    ServletUriComponentsBuilder.fromCurrentRequest()
//                            .path("/{id}")
//                            .buildAndExpand(savedUser.getId())
//                            .toUri())
//            .body(UserMapper.toUserDTO(savedUser));
//  }

}
