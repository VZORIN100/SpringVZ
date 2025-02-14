//package lt.techin.demo.validation;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//public class TheatreValidator implements ConstraintValidator<Theatre, String> {
//
//  @Override
//  public boolean isValid(String theatre, ConstraintValidatorContext constraintValidatorContext) {
//    return theatre != null && theatre.matches("^[A-Z][a-z]+$")
//            && (theatre.length() >= 4) && (theatre.length() <= 150);
//  }
//}
