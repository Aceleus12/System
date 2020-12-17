package pl.edu.wat.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.demo.entities.GainedStepEntity;
import pl.edu.wat.demo.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    List<UserEntity> findAllBySurnameContainsOrNameContainsOrPeselContainsOrEmailContains(String surname, String name, String pesel, String email);
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
