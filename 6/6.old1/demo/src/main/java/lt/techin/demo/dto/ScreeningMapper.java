package lt.techin.demo.dto;

import lt.techin.demo.model.Movie;
import lt.techin.demo.model.Screening;

import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.LocalTime;


public class ScreeningMapper {
  public static List<ScreeningDTO> toScreeningDTOList(List<Screening> screenings) {
    List<ScreeningDTO> result = screenings.stream()
            .map(screening -> new ScreeningDTO(screening.getId(), screening.getTheatre()))
            .toList();

    return result;
  }


  public static ScreeningDTO toScreeningDTO(Screening screening) {
    return new ScreeningDTO(screening.getId(), screening.getTheatre());
  }

  public static Screening toScreening(ScreeningDTO screeningDTO) {
    Screening screening = new Screening();
    screening.setTheatre(screeningDTO.theatre());

    return screening;
  }

  public static void updateScreeningFromDTO(Screening screening, ScreeningDTO screeningDTO) {

    screening.setTheatre(screeningDTO.theatre());
  }
}
