package pl.edu.wat.demo.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainedCertificateResponse {
    private String id;
    private Optional<Date> collectDate;
    private Optional<Date> gainDate;
    private Optional<String> fileId;
    private boolean collected;
    private boolean gained;
    private String userId;
    private String certificateID;
    private String certificateName;
}
