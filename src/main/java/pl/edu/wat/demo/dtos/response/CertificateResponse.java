package pl.edu.wat.demo.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponse {
    private String id;
    private String name;
    private String description;
    private String firstStepId;
    private List<StepResponse> certificateSteps;
}
