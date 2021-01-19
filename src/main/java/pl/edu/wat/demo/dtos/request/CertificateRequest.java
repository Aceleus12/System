package pl.edu.wat.demo.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class CertificateRequest {
    private String name;
    private String description;
    private int cost;
    private List<StepRequest> certificateSteps;
}
