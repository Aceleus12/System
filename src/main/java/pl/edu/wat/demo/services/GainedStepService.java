package pl.edu.wat.demo.services;

import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.GainedStepResponse;
import pl.edu.wat.demo.dtos.StepResponse;
import pl.edu.wat.demo.entities.GainedCertificateEntity;

import java.util.List;

public interface GainedStepService {
    List<GainedStepResponse> getAll();

    boolean endStep(String step_id);

    boolean addComment(String step_id, String comment);

    void removeById(String certificate_id);

    void startStep(String id, String userId, GainedCertificateEntity gainedCertificateEntity);

    public List<GainedStepResponse> getUserSteps(String userId);

    public List<GainedStepResponse> getUserStepsFilterByConfirmed(String userId,boolean confirmed);

    GainedStepResponse addFile(MultipartFile multipartFile, String certificateStepId);

    public List<GainedStepResponse> getByCertificateAndUserAndConfirmed(String gainedCertificateID, String userId, boolean confirmed);
}
