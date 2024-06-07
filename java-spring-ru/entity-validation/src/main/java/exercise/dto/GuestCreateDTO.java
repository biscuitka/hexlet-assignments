package exercise.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

// BEGIN
@Data
public class GuestCreateDTO {
    @NotBlank
    private String name;

    @Email(regexp = "^([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+.[a-zA-Z]{2,})$", message = "Введен некорректный email")
    private String email;

    @Pattern(regexp="\\+\\d{11,13}", message="Phone number must start with '+' and have 11-13 characters")
    private String phoneNumber;

    @Size(min = 4, max = 4)
    private String clubCard;

    @Future
    private LocalDate cardValidUntil;
}
// END
