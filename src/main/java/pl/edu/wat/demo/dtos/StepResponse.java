package pl.edu.wat.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepResponse {
    private String id;
    private String name;
    private String description;
    private Optional<String> fileId;
    private Optional<String> nextStepId;
    private String certificateID;
}
