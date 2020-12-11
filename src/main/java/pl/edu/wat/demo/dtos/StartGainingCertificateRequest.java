package pl.edu.wat.demo.dtos;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class StartGainingCertificateRequest {
    @NonNull
    private String userId;
    @NonNull
    private String certificateID;
}
