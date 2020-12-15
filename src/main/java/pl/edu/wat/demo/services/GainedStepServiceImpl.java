package pl.edu.wat.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.response.GainedStepResponse;
import pl.edu.wat.demo.entities.GainedCertificateEntity;
import pl.edu.wat.demo.entities.GainedStepEntity;
import pl.edu.wat.demo.entities.UserEntity;
import pl.edu.wat.demo.repositories.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GainedStepServiceImpl implements GainedStepService {

    private final GainedStepRepository gainedStepRepository;
    private final CertificateStepRepository certificateStepRepository;
    private final UserRepository userRepository;
    private final GainedCertificateRepository gainedCertificateRepository;
    private final FileService fileService;



    @Autowired
    public GainedStepServiceImpl(GainedStepRepository gainedStepRepository, CertificateStepRepository certificateStepRepository, UserRepository userRepository, GainedCertificateRepository gainedCertificateRepository, FileService fileService) {
        this.gainedStepRepository = gainedStepRepository;
        this.certificateStepRepository = certificateStepRepository;
        this.userRepository = userRepository;
        this.gainedCertificateRepository = gainedCertificateRepository;
        this.fileService = fileService;
    }

    @Override
    public boolean addComment(String step_id, String comment) {
        if(gainedStepRepository.findById(step_id).isPresent()){
            GainedStepEntity gainedStepEntity = gainedStepRepository.findById(step_id).get();
            gainedStepEntity.setComment(comment);
            gainedStepRepository.save(gainedStepEntity);
            return true;
        }
        return false;
    }



    @Override
    public List<GainedStepResponse> getAll() {
        return StreamSupport.stream(gainedStepRepository.findAll().spliterator(), false)
                .map(stepProceedEntity -> new GainedStepResponse(
                        stepProceedEntity.getId(),
                        stepProceedEntity.isConfirmed(),
                        stepProceedEntity.getComment(),
                        stepProceedEntity.getCertificateStep().getName(),
                        stepProceedEntity.getCertificateStep().getDescription(),
                        stepProceedEntity.getStartDate(),
                        java.util.Optional.ofNullable(stepProceedEntity.getStopDate()),
                        java.util.Optional.ofNullable((stepProceedEntity.getFileEntity()!=null)?stepProceedEntity.getFileEntity().getId():null),
                        stepProceedEntity.getUser().getId(),
                        stepProceedEntity.getCertificateStep().getId()
                )).collect(Collectors.toList());
    }

    @Override
    public void startStep( String certificateStepId, String userId, GainedCertificateEntity gainedCertificateEntity) {
        if(userRepository.findById(userId).isPresent() && certificateStepRepository.findById(certificateStepId).isPresent() ){
            GainedStepEntity gainedStepEntity = new GainedStepEntity();
            gainedStepEntity.setUser(userRepository.findById(userId).get());
            gainedStepEntity.setCertificateStep(certificateStepRepository.findById(certificateStepId).get());
            gainedStepEntity.setConfirmed(false);
            gainedStepEntity.setStartDate(new Date());
            gainedStepEntity.setGainedCertificateEntity(gainedCertificateEntity);
            gainedStepRepository.save(gainedStepEntity);
            gainedCertificateEntity.setActualStep(gainedStepEntity);
            gainedStepRepository.save(gainedStepEntity);
        }
    }

    @Override
    public boolean endStep(String step_id) {
        if(gainedStepRepository.findById(step_id).isPresent()){
            GainedStepEntity gainedStepEntity = gainedStepRepository.findById(step_id).get();
            if(gainedStepEntity.getGainedCertificateEntity().getActualStep() == gainedStepEntity){
                gainedStepEntity.setConfirmed(true);
                gainedStepEntity.setStopDate(new Date());
                gainedStepRepository.save(gainedStepEntity);
                GainedCertificateEntity gainedCertificateEntity = gainedStepEntity.getGainedCertificateEntity();
                if(gainedStepEntity.getCertificateStep().getNextStep()!=null){
                    GainedStepEntity nextGainedStepEntity = new GainedStepEntity();
                    nextGainedStepEntity.setUser(gainedStepEntity.getUser());
                    nextGainedStepEntity.setCertificateStep(gainedStepEntity.getCertificateStep().getNextStep());
                    nextGainedStepEntity.setConfirmed(false);
                    nextGainedStepEntity.setStartDate(new Date());
                    nextGainedStepEntity.setGainedCertificateEntity(gainedStepEntity.getGainedCertificateEntity());
                    gainedStepRepository.save(nextGainedStepEntity);
                    gainedCertificateEntity.setActualStep(nextGainedStepEntity);
                }
                else{
                    gainedCertificateEntity.setGained(true);
                    gainedCertificateEntity.setGainDate(new Date());
                }
                gainedCertificateRepository.save(gainedCertificateEntity);
                return true;

            }
        }

        return false;
    }

    @Override
    public List<GainedStepResponse> getUserSteps(String userId) {
        if(userRepository.findById(userId).isPresent()){
            return gainedStepRepository.findByUser(userRepository.findById(userId).get()).stream()
                .map(gainedStepEntity -> new GainedStepResponse(
                    gainedStepEntity.getId(),
                    gainedStepEntity.isConfirmed(),
                    gainedStepEntity.getComment(),
                    gainedStepEntity.getCertificateStep().getName(),
                    gainedStepEntity.getCertificateStep().getDescription(),
                    gainedStepEntity.getStartDate(),
                    java.util.Optional.ofNullable(gainedStepEntity.getStopDate()),
                    java.util.Optional.ofNullable((gainedStepEntity.getFileEntity()!=null)?gainedStepEntity.getFileEntity().getId():null),
                    gainedStepEntity.getUser().getId(),
                    gainedStepEntity.getCertificateStep().getId()
                )).collect(Collectors.toList());
        }
        return null;
    }


    public List<GainedStepResponse> getUserStepsFilterByConfirmed(String userId, boolean confirmed) {
        if(userRepository.findById(userId).isPresent()){
            return gainedStepRepository.findByUserAndConfirmed(userRepository.findById(userId).get(),true).stream()
                    .map(gainedStepEntity -> new GainedStepResponse(
                            gainedStepEntity.getId(),
                            gainedStepEntity.isConfirmed(),
                            gainedStepEntity.getComment(),
                            gainedStepEntity.getCertificateStep().getName(),
                            gainedStepEntity.getCertificateStep().getDescription(),
                            gainedStepEntity.getStartDate(),
                            java.util.Optional.ofNullable(gainedStepEntity.getStopDate()),
                            java.util.Optional.ofNullable((gainedStepEntity.getFileEntity()!=null)?gainedStepEntity.getFileEntity().getId():null),
                            gainedStepEntity.getUser().getId(),
                            gainedStepEntity.getCertificateStep().getId()
                    )).collect(Collectors.toList());
        }
        return null;
    }


    @Override
    public void removeById(String id) {
        gainedStepRepository.deleteById(id);

    }

    @Override
    public GainedStepResponse addFile(MultipartFile multipartFile, String certificateStepId) {
        if(gainedStepRepository.findById(certificateStepId).isPresent()){
            GainedStepEntity stepEntity = gainedStepRepository.findById(certificateStepId).get();
            stepEntity.setFileEntity(fileService.storeFile(multipartFile));
            gainedStepRepository.save(stepEntity);
            return  new GainedStepResponse(
                    stepEntity.getId(),
                    stepEntity.isConfirmed(),
                    stepEntity.getComment(),
                    stepEntity.getCertificateStep().getName(),
                    stepEntity.getCertificateStep().getDescription(),
                    stepEntity.getStartDate(),
                    java.util.Optional.ofNullable(stepEntity.getStopDate()),
                    java.util.Optional.ofNullable((stepEntity.getFileEntity()!=null)?stepEntity.getFileEntity().getId():null),
                    stepEntity.getUser().getId(),
                    stepEntity.getCertificateStep().getId()
                    );
        }
        return null;
    }

    @Override
    public List<GainedStepResponse> getByCertificateAndUserAndConfirmed(String gainedCertificateID, String userId, boolean confirmed) {
        if(gainedCertificateRepository.findById(gainedCertificateID).isPresent() && userRepository.findById(userId).isPresent()){
            UserEntity userEntity = userRepository.findById(userId).get();
            GainedCertificateEntity gainedCertificateEntity = gainedCertificateRepository.findById(gainedCertificateID).get();
            return gainedStepRepository.findByUserAndGainedCertificateEntityAndConfirmed(userEntity,gainedCertificateEntity,confirmed).stream()
                    .map(gainedStepEntity -> new GainedStepResponse(
                            gainedStepEntity.getId(),
                            gainedStepEntity.isConfirmed(),
                            gainedStepEntity.getComment(),
                            gainedStepEntity.getCertificateStep().getName(),
                            gainedStepEntity.getCertificateStep().getDescription(),
                            gainedStepEntity.getStartDate(),
                            java.util.Optional.ofNullable(gainedStepEntity.getStopDate()),
                            java.util.Optional.ofNullable((gainedStepEntity.getFileEntity()!=null)?gainedStepEntity.getFileEntity().getId():null),
                            gainedStepEntity.getUser().getId(),
                            gainedStepEntity.getCertificateStep().getId()
                    )).collect(Collectors.toList());
        }
        return null;
    }
}
