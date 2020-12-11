package pl.edu.wat.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.demo.dtos.UserRequest;
import pl.edu.wat.demo.dtos.UserResponse;
import pl.edu.wat.demo.entities.UserEntity;
import pl.edu.wat.demo.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userEntity -> new UserResponse(
                        userEntity.getId(),
                        userEntity.getEmail(),
                        userEntity.getSurname(),
                        userEntity.getName(),
                        userEntity.getPesel(),
                        userEntity.getFatherName()
                )).collect(Collectors.toList());
    }



    @Override
    public List<UserResponse> getWithFilters(String name) {
        return StreamSupport.stream(userRepository.findAllBySurnameContainsOrNameContainsOrPeselContainsOrEmailContains(name,name,name,name).spliterator(), false)
                .map(userEntity -> new UserResponse(
                        userEntity.getId(),
                        userEntity.getEmail(),
                        userEntity.getSurname(),
                        userEntity.getName(),
                        userEntity.getPesel(),
                        userEntity.getFatherName()
                )).collect(Collectors.toList());
    }

    @Override
    public void addNew(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequest.getName());
        userEntity.setSurname(userRequest.getSurname());
        userEntity.setFatherName(userRequest.getFatherName());
        userEntity.setPesel(userRequest.getPesel());
        userEntity.setEmail(userRequest.getEmail());
        userRepository.save(userEntity);
    }

    @Override
    public void removeById(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponse get(String user_id) {
        if(userRepository.findById(user_id).isPresent()){
            UserEntity user = userRepository.findById(user_id).get();
            return new UserResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getSurname(),
                    user.getName(),
                    user.getPesel(),
                    user.getFatherName()
            );
        }
        else{
            return null;
        }


    }
}
