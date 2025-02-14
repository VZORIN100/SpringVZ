//package lt.techin.demo.validation;
//
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Target({ElementType.FIELD})
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = TheatreValidator.class)
//public @interface Theatre {
//
//  String message() default "Theatre cannot be null; Must start with uppercase & continue with lowercase letters;"
//          + " Must be between 4 and 50 characters ";
//
//  Class<?>[] groups() default {};
//
//  Class<? extends Payload>[] payload() default {};
//}