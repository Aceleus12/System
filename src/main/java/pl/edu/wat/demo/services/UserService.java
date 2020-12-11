package pl.edu.wat.demo.services;

import pl.edu.wat.demo.dtos.UserRequest;
import pl.edu.wat.demo.dtos.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();

    void addNew(UserRequest userRequest);

    void removeById(String user_id);

    List<UserResponse> getWithFilters(String name);

    UserResponse get(String user_id);
}
