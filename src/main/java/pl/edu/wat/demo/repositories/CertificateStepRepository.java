package pl.edu.wat.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.demo.entities.StepEntity;

@Repository
public interface CertificateStepRepository extends CrudRepository<StepEntity, String> {
}
