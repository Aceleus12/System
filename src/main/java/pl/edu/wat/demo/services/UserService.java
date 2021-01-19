package pl.edu.wat.demo.services;

import pl.edu.wat.demo.dtos.request.AddUserRequest;
import pl.edu.wat.demo.dtos.request.LoginRequest;
import pl.edu.wat.demo.dtos.response.JwtResponse;
import pl.edu.wat.demo.dtos.response.MessageResponse;
import pl.edu.wat.demo.dtos.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();

    MessageResponse addNew(AddUserRequest userRequest);

    void removeById(String user_id);

    List<UserResponse> getWithFilters(String name);

    UserResponse get(String user_id);

    JwtResponse signIn(LoginRequest loginRequest);

    void addMoney(String userID, int money);
}
