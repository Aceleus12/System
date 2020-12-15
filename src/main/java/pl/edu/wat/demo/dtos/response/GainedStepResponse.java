package pl.edu.wat.demo.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainedStepResponse {
    private String id;
    private boolean confirmed;
    private String comment;
    private String stepName;
    private String stepDescription;
    private Date startDate;
    private Optional<Date> stopDate;
    private Optional<String> fileId;
    private String userId;
    private String certificateStepId;
}
