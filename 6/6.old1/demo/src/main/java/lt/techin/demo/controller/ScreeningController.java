package lt.techin.demo.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lt.techin.demo.dto.ScreeningDTO;
import lt.techin.demo.dto.ScreeningMapper;
import lt.techin.demo.model.Movie;
import lt.techin.demo.model.Screening;
import lt.techin.demo.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ScreeningController {

  private final ScreeningService screeningService;

  // @Autowired nebūtina rašyti ant konstruktoriaus, KAI yra 1 konstruktorius
  // Aš rekomenduoju visada rašyti
  @Autowired
  public ScreeningController(ScreeningService screeningService) {
    this.screeningService = screeningService;
  }

  @PostMapping("/screenings")
  public ResponseEntity<?> addScreening(@Valid @RequestBody ScreeningDTO screeningDTO) {

//    if (screening.getTheatre().isEmpty()) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name cannot be empty");
//    }

    Screening savedScreening = screeningService.saveScreening(ScreeningMapper.toScreening(screeningDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedScreening.getId())
                            .toUri())
            .body(ScreeningMapper.toScreeningDTO(savedScreening));
  }
}
