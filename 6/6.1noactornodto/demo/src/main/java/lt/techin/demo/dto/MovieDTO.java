package lt.techin.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.demo.model.Movie;
//import lt.techin.demo.model.BookDetail;
//import lt.techin.demo.model.Category;
//import lt.techin.demo.model.Review;
import lt.techin.demo.model.Screening;
import lt.techin.demo.validation.Theatre;

import java.util.List;

public record MovieDTO(long id,
                       @NotNull
                       @Size(min = 4, max = 150, message = "Must be between 4 and 150 characters.")
                       // Jau suprantame, jog pattern reikalauja mažiausiai 2 simbolių; jei pridėsime prie
                       // @Size min = 2, pjausis su Pattern
                       String title,

                       @Size(min = 4, max = 50, message = "Must be between 4 and 150 characters.")
                       @Pattern(regexp = "^[A-Z][a-z]+$", message = "Must start with uppercase letter, " +
                               "and continue as lowercase. " + "Also not contain any numbers.")
                       String director,
                       List<Screening> screenings) {

  //List<Screening> screenings
  //List<Category> categories,
  //BookDetail bookDetail

}
