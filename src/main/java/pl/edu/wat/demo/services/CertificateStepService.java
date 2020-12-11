package pl.edu.wat.demo.services;

import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.StepResponse;

import java.util.List;

public interface CertificateStepService {

    List<StepResponse> getAll();
    StepResponse get(String certificate_step_id);

    StepResponse addFile(MultipartFile multipartFile, String certificateStepId);
}
