package pl.edu.wat.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.demo.entities.GainedCertificateEntity;
import pl.edu.wat.demo.entities.GainedStepEntity;
import pl.edu.wat.demo.entities.UserEntity;

import java.util.List;

@Repository
public interface GainedStepRepository extends CrudRepository<GainedStepEntity, String> {
    List<GainedStepEntity> findByUserAndConfirmed(UserEntity user, boolean confirmed);
    List<GainedStepEntity> findByUserAndGainedCertificateEntityAndConfirmed(UserEntity userEntity, GainedCertificateEntity gainedCertificateEntity, boolean confirmed);
    List<GainedStepEntity> findByUser(UserEntity user);
}
