package pl.edu.wat.demo.services;

import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.entities.FileEntity;

public interface FileService {

    FileEntity storeFile(MultipartFile file);

    FileEntity getFile(String fileId);

}
