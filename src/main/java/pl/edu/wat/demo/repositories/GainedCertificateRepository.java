package pl.edu.wat.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.demo.entities.CertificateEntity;
import pl.edu.wat.demo.entities.GainedCertificateEntity;
import pl.edu.wat.demo.entities.UserEntity;

import java.util.List;

@Repository
public interface GainedCertificateRepository extends CrudRepository<GainedCertificateEntity, String> {
    List<GainedCertificateEntity> findByUser(UserEntity user);
    List<GainedCertificateEntity> findByUserAndGained(UserEntity user, boolean gained);
    List<GainedCertificateEntity> findByCertificateAndUser(CertificateEntity certificateEntity, UserEntity userEntity);
//    List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);
//
//    // Enables the distinct flag for the query
//    List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
//    List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
//
//    // Enabling ignoring case for an individual property
//    List<Person> findByLastnameIgnoreCase(String lastname);
//    // Enabling ignoring case for all suitable properties
//    List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
//
//    // Enabling static ORDER BY for a query
//    List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
//    List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
}
