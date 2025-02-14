package lt.techin.demo.service;

import lt.techin.demo.model.Screening;
import lt.techin.demo.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScreeningService {

  private final ScreeningRepository screeningRepository;

  public ScreeningService(ScreeningRepository screeningRepository) {
    this.screeningRepository = screeningRepository;
  }

  public Screening saveScreening(Screening screening) {
    return screeningRepository.save(screening);
  }
}
