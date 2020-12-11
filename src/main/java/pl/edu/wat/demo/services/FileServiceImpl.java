package pl.edu.wat.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.CertificateRequest;
import pl.edu.wat.demo.dtos.CertificateResponse;
import pl.edu.wat.demo.dtos.StepRequest;
import pl.edu.wat.demo.dtos.StepResponse;
import pl.edu.wat.demo.entities.CertificateEntity;
import pl.edu.wat.demo.entities.FileEntity;
import pl.edu.wat.demo.entities.StepEntity;
import pl.edu.wat.demo.repositories.CertificateRepository;
import pl.edu.wat.demo.repositories.CertificateStepRepository;
import pl.edu.wat.demo.repositories.FileRepository;
import pl.edu.wat.demo.repositories.GainedCertificateRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileEntity storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setFileType(file.getContentType());
            fileEntity.setData(file.getBytes());

            return fileRepository.save(fileEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FileEntity getFile(String fileId) {
        if(fileRepository.findById(fileId).isPresent()){
            return fileRepository.findById(fileId).get();
        }
        else {
            return null;
        }



    }
}
