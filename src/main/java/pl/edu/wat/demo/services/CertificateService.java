package pl.edu.wat.demo.services;

import pl.edu.wat.demo.dtos.request.CertificateRequest;
import pl.edu.wat.demo.dtos.response.CertificateResponse;

import java.util.List;

public interface CertificateService {

    List<CertificateResponse> getAll();

    CertificateResponse get(String certificate_id);

    void addNew(CertificateRequest  certificateRequest);

    boolean removeById(String certificate_id);

    List<CertificateResponse> getFilteredBy(String name);
}
