package pl.edu.wat.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.demo.entities.CertificateEntity;
import pl.edu.wat.demo.entities.UserEntity;

import java.util.List;

@Repository
public interface CertificateRepository extends CrudRepository<CertificateEntity, String> {
    List<CertificateEntity> findAllByNameContains(String name);
}
