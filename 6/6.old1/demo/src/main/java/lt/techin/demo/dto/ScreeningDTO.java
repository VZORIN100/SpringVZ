package lt.techin.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
//import lt.techin.demo.model.BookDetail;
//import lt.techin.demo.model.Category;
//import lt.techin.demo.model.Review;
import lt.techin.demo.model.Screening;
import lt.techin.demo.validation.Theatre;

import java.util.List;

public record ScreeningDTO(long id,
                           @Theatre
                           String theatre) {

  //List<Screening> screenings
  //List<Category> categories,
  //BookDetail bookDetail

}
