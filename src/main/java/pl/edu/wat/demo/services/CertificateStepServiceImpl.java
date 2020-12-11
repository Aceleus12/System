package pl.edu.wat.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.StepResponse;
import pl.edu.wat.demo.entities.StepEntity;
import pl.edu.wat.demo.repositories.CertificateStepRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CertificateStepServiceImpl implements CertificateStepService {


    private final CertificateStepRepository certificateStepRepository;
    private final FileService fileService;


    @Autowired
    public CertificateStepServiceImpl(CertificateStepRepository certificateStepRepository, FileService fileService) {
        this.certificateStepRepository = certificateStepRepository;
        this.fileService = fileService;
    }

    @Override
    public StepResponse get(String certificate_step_id) {
        if (certificateStepRepository.findById(certificate_step_id).isPresent()){
            StepEntity stepEntity = certificateStepRepository.findById(certificate_step_id).get();
            return new StepResponse(
                    stepEntity.getId(),
                    stepEntity.getName(),
                    stepEntity.getDescription(),
                    java.util.Optional.ofNullable((stepEntity.getFileEntity()!=null)?stepEntity.getFileEntity().getId():null),
                    java.util.Optional.ofNullable((stepEntity.getNextStep()!=null)?stepEntity.getNextStep().getId():null),
                    stepEntity.getCertificate().getId()
            );
        }
        else return null;
    }

    @Override
    public List<StepResponse> getAll() {
        return StreamSupport.stream(certificateStepRepository.findAll().spliterator(), false).map(
            certificateStepEntity -> new StepResponse(
                certificateStepEntity.getId(),
                certificateStepEntity.getName(),
                certificateStepEntity.getDescription(),
                java.util.Optional.ofNullable((certificateStepEntity.getFileEntity()!=null)?certificateStepEntity.getFileEntity().getId():null),
                java.util.Optional.ofNullable((certificateStepEntity.getNextStep()!=null)?certificateStepEntity.getNextStep().getId():null),
                certificateStepEntity.getCertificate().getId()
            )
        ).collect(Collectors.toList());
    }

    @Override
    public StepResponse addFile(MultipartFile multipartFile, String certificateStepId) {
        if(certificateStepRepository.findById(certificateStepId).isPresent()){
            StepEntity stepEntity = certificateStepRepository.findById(certificateStepId).get();
            stepEntity.setFileEntity(fileService.storeFile(multipartFile));
            certificateStepRepository.save(stepEntity);
            return  new StepResponse(
            stepEntity.getId(),
            stepEntity.getName(),
            stepEntity.getDescription(),
            java.util.Optional.ofNullable((stepEntity.getFileEntity()!=null)?stepEntity.getFileEntity().getId():null),
            java.util.Optional.ofNullable((stepEntity.getNextStep()!=null)?stepEntity.getNextStep().getId():null),
            stepEntity.getCertificate().getId());
        }
        return null;
    }


}
