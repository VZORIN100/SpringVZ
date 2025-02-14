package lt.techin.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.demo.model.User;
import lt.techin.demo.model.Role;
//import lt.techin.demo.model.BookDetail;
//import lt.techin.demo.model.Category;
//import lt.techin.demo.model.Review;
import lt.techin.demo.validation.Theatre;

import java.util.List;

public record UserDTO(long id,
                      @NotNull
                      @Size(min = 3, max = 80, message = "Must be between 3 and 80 characters.")
                      String username,

                      @NotNull
                      @Size(min = 3, max = 255, message = "Must be between 3 and 255 characters.")
                      String password,
                      List<Role> roles) {
}

//List<Screening> screenings
//List<Category> categories,
//BookDetail bookDetail

