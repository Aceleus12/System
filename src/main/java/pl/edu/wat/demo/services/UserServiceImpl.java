package pl.edu.wat.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.edu.wat.demo.dtos.request.AddUserRequest;
import pl.edu.wat.demo.dtos.request.LoginRequest;
import pl.edu.wat.demo.dtos.response.JwtResponse;
import pl.edu.wat.demo.dtos.response.MessageResponse;
import pl.edu.wat.demo.dtos.response.UserResponse;
import pl.edu.wat.demo.entities.ERole;
import pl.edu.wat.demo.entities.RoleEntity;
import pl.edu.wat.demo.entities.UserEntity;
import pl.edu.wat.demo.repositories.RoleRepository;
import pl.edu.wat.demo.repositories.UserRepository;
import pl.edu.wat.demo.security.jwt.JwtUtils;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void addMoney(String userID, int money) {
        if(userRepository.findById(userID).isPresent()){
            UserEntity user = userRepository.findById(userID).get();
            user.setMoney(user.getMoney()+money);
            userRepository.save(user);
        }
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
                        userEntity.getFatherName(),
                        userEntity.getMoney()
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
                        userEntity.getFatherName(),
                        userEntity.getMoney()
                )).collect(Collectors.toList());
    }

    @Override
    public JwtResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public MessageResponse addNew(@Valid @RequestBody AddUserRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new MessageResponse("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }

        // Create new user's account
        UserEntity user = new UserEntity();

        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setFatherName(signUpRequest.getFatherName());
        user.setPesel(signUpRequest.getPesel());
        user.setEmail(signUpRequest.getEmail());
        user.setMoney(100);
        userRepository.save(user);

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        RoleEntity modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoleEntities(roles);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
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
                    user.getFatherName(),
                    user.getMoney()
            );
        }
        else{
            return null;
        }


    }
}
