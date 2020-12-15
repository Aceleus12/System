package pl.edu.wat.demo.services;

import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.request.StartGainingCertificateRequest;
import pl.edu.wat.demo.dtos.response.GainedCertificateResponse;

import java.util.List;

public interface GainedCertificateService {
    List<GainedCertificateResponse> getAll();

    GainedCertificateResponse get(String id);

    int startProcedure(StartGainingCertificateRequest certificateRequest);

    List<GainedCertificateResponse> getUserCertificates(String user_id);

    List<GainedCertificateResponse> getUserCertificatesFilterByConfirmed(String user_id, boolean confirmed);

    boolean removeById(String certificate_id);

    GainedCertificateResponse addFile(MultipartFile file, String stepId);

    GainedCertificateResponse confirmCollecting(String stepId);

}
