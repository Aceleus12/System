package pl.edu.wat.demo.services;

import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.CertificateRequest;
import pl.edu.wat.demo.dtos.CertificateResponse;
import pl.edu.wat.demo.entities.FileEntity;

import java.util.List;

public interface FileService {

    FileEntity storeFile(MultipartFile file);

    FileEntity getFile(String fileId);

}
