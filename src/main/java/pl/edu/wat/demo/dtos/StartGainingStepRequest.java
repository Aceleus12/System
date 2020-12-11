package pl.edu.wat.demo.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class StartGainingStepRequest {
    private Date startDate;
    private String userId;
    private String certificateStepId;
}
