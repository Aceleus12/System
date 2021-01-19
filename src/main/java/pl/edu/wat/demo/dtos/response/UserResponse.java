package pl.edu.wat.demo.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String surname;
    private String name;
    private String pesel;
    private String fatherName;
    private int money;
}
