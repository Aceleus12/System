package pl.edu.wat.demo.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {
    @Size(min = 3, max = 20)
    private String username;
    @Size(max = 50)
    @Email
    private String email;
    private Set<String> role;
    @Size(min = 6, max = 40)
    private String password;
    private String surname;
    private String name;
    private String pesel;
    private String fatherName;

}
