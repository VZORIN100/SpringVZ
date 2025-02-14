package lt.techin.demo.dto;

import lt.techin.demo.model.User;
import lt.techin.demo.model.Role;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

  public static List<UserDTO> toUserDTOList(List<User> users) {
    List<UserDTO> result = users.stream()
            .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getPassword(),
                    user.getRoles()))
            .toList();

    return result;
  }

  //000000000000000000000000000000000000000


  public static UserDTO toUserDTO(User user) {
    return new UserDTO(user.getId(), user.getUsername(), user.getPassword(),
            user.getRoles());
  }

  public static User toUserDTO(UserDTO userDTO) {
    User user = new User();
    user.setUsername(userDTO.username());
    user.setPassword(userDTO.password());
    user.setRoles(userDTO.roles());

    return user;
  }

  public static void updateUserFromDTO(User user, UserDTO userDTO) {
    user.setUsername(userDTO.username());
    user.setPassword(userDTO.password());
    user.setRoles(userDTO.roles());
  }
}
