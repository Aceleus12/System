package pl.edu.wat.demo.dtos;

import lombok.Data;
import lombok.NonNull;

import java.util.Optional;

@Data
public class UserRequestWithFilters {
    private String name;
    private String email;
    private String surname;
    private String pesel;
}
