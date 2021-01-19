package pl.edu.wat.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.demo.dtos.request.CertificateRequest;
import pl.edu.wat.demo.dtos.request.StepRequest;
import pl.edu.wat.demo.dtos.response.CertificateResponse;
import pl.edu.wat.demo.dtos.response.StepResponse;
import pl.edu.wat.demo.entities.CertificateEntity;
import pl.edu.wat.demo.entities.StepEntity;
import pl.edu.wat.demo.repositories.CertificateRepository;
import pl.edu.wat.demo.repositories.CertificateStepRepository;
import pl.edu.wat.demo.repositories.FileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final FileRepository fileRepository;
    private final FileService fileService;
    private final CertificateStepRepository certificateStepRepository;
    private final CertificateRepository certificateRepository;



    @Autowired
    public CertificateServiceImpl(FileRepository fileRepository, CertificateStepRepository certificateStepRepository, CertificateRepository certificateRepository, FileService fileService) {
        this.fileRepository = fileRepository;
        this.certificateStepRepository = certificateStepRepository;
        this.certificateRepository = certificateRepository;
        this.fileService = fileService;
    }


    @Override
    public CertificateResponse get(String certificate_id) {
        if(certificateRepository.findById(certificate_id).isPresent()){
            CertificateEntity certificateEntity = certificateRepository.findById(certificate_id).get();
            return new CertificateResponse(
                    certificateEntity.getId(),
                    certificateEntity.getName(),
                    certificateEntity.getDescription(),
                    certificateEntity.getFirstStep().getId(),
                    certificateEntity.getStepEntityList().stream().map(
                            certificateStepEntity -> new StepResponse(
                                    certificateStepEntity.getId(),
                                    certificateStepEntity.getName(),
                                    certificateStepEntity.getDescription(),
                                    java.util.Optional.ofNullable((certificateStepEntity.getFileEntity()!=null)?certificateStepEntity.getFileEntity().getId():null),
                                    java.util.Optional.ofNullable((certificateStepEntity.getNextStep()!=null)?certificateStepEntity.getNextStep().getId():null),
                                    certificateStepEntity.getCertificate().getId()
                            )
                    ).collect(Collectors.toList())
            );
        }
        else{
            return null;
        }
    }


    List<StepEntity> addStepEntityList(List<StepRequest> certificateSteps, CertificateEntity certificateEntity){
        List<StepEntity> certificateStepEntities = new ArrayList<>();
        for (StepRequest certificateStep : certificateSteps) {
            StepEntity stepEntity = new StepEntity();
            stepEntity.setName(certificateStep.getName());
            stepEntity.setDescription(certificateStep.getDescription());
            stepEntity.setCertificate(certificateEntity);
            certificateStepEntities.add(stepEntity);
        }
        for (int i = 0; i < (certificateSteps.size()-1); i++){
            certificateStepEntities.get(i).setNextStep(certificateStepEntities.get(i+1));
        }
        certificateStepRepository.saveAll(certificateStepEntities);

        return certificateStepEntities;
    }

    @Override
    public List<CertificateResponse> getFilteredBy(String name) {
        return StreamSupport.stream(certificateRepository.findAllByNameContains(name).spliterator(), false).map(
                certificateEntity -> new CertificateResponse(
                        certificateEntity.getId(),
                        certificateEntity.getName(),
                        certificateEntity.getDescription(),
                        certificateEntity.getFirstStep().getId(),
                        certificateEntity.getStepEntityList().stream().map(
                                certificateStepEntity -> new StepResponse(
                                        certificateStepEntity.getId(),
                                        certificateStepEntity.getName(),
                                        certificateStepEntity.getDescription(),
                                        java.util.Optional.ofNullable((certificateStepEntity.getFileEntity()!=null)?certificateStepEntity.getFileEntity().getId():null),
                                        java.util.Optional.ofNullable((certificateStepEntity.getNextStep()!=null)?certificateStepEntity.getNextStep().getId():null),
                                        certificateStepEntity.getCertificate().getId()
                                )).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
    }

    @Override
    public void addNew(CertificateRequest certificateRequest) {
        CertificateEntity certificateEntity = new CertificateEntity();
        certificateEntity.setName(certificateRequest.getName());
        certificateEntity.setDescription(certificateRequest.getDescription());
        certificateEntity.setCost(certificateRequest.getCost());
        certificateRepository.save(certificateEntity);
        List<StepEntity> list = addStepEntityList(certificateRequest.getCertificateSteps(),certificateEntity);
        certificateEntity.setStepEntityList(list);
        certificateEntity.setFirstStep(list.get(0));
        certificateRepository.save(certificateEntity);
    }

    @Override
    public List<CertificateResponse> getAll() {
        return StreamSupport.stream(certificateRepository.findAll().spliterator(), false).map(
            certificateEntity -> new CertificateResponse(
                certificateEntity.getId(),
                certificateEntity.getName(),
                certificateEntity.getDescription(),
                certificateEntity.getFirstStep().getId(),
                certificateEntity.getStepEntityList().stream().map(
                    certificateStepEntity -> new StepResponse(
                            certificateStepEntity.getId(),
                            certificateStepEntity.getName(),
                            certificateStepEntity.getDescription(),
                            java.util.Optional.ofNullable((certificateStepEntity.getFileEntity()!=null)?certificateStepEntity.getFileEntity().getId():null),
                            java.util.Optional.ofNullable((certificateStepEntity.getNextStep()!=null)?certificateStepEntity.getNextStep().getId():null),
                        certificateStepEntity.getCertificate().getId()
                    )).collect(Collectors.toList())
            )
        ).collect(Collectors.toList());
    }

    @Override
    public boolean removeById(String id) {
        if( certificateRepository.findById(id).isPresent()){
            certificateRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }

    }
}
