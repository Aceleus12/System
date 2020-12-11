package pl.edu.wat.demo.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CertificateRequest {
    private String name;
    private String description;
    private List<StepRequest> certificateSteps;
}
