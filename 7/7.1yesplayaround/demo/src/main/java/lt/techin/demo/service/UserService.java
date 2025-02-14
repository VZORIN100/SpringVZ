package lt.techin.demo.service;

import jakarta.validation.constraints.Size;
import lt.techin.demo.dto.UserDTO;
import lt.techin.demo.model.User;
import lt.techin.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public boolean existsUserById(long id) {
    return userRepository.existsById(id);
  }

  public Optional<User> findUserById(long id) {
    return userRepository.findById(id);
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public void deleteUserById(long id) {
    userRepository.deleteById(id);
  }

  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  //==============

  public Optional<User> findUserByPassword(String password) {
    return userRepository.findByPassword(password);
  }

  //May not be needed.
//  public List<User> findAllBooksByUsernameContaining(String username) {
//    // Calls a derived query
//    return userRepository.findAllUsersByUsernameContaining(username);
//  }
//
//  public List<UserDTO> findAllUsersByUsernameContaining(@Size(min = 2) String username) {
//    return List.of();
//  }

}
