package pl.edu.wat.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.demo.dtos.request.AddUserRequest;
import pl.edu.wat.demo.dtos.request.LoginRequest;
import pl.edu.wat.demo.dtos.request.UserRequest;
import pl.edu.wat.demo.dtos.response.GainedCertificateResponse;
import pl.edu.wat.demo.dtos.response.GainedStepResponse;
import pl.edu.wat.demo.dtos.response.JwtResponse;
import pl.edu.wat.demo.dtos.response.UserResponse;
import pl.edu.wat.demo.security.jwt.JwtUtils;
import pl.edu.wat.demo.services.GainedCertificateService;
import pl.edu.wat.demo.services.GainedStepService;
import pl.edu.wat.demo.services.UserDetailsImpl;
import pl.edu.wat.demo.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final GainedCertificateService gainedCertificateService;
    private final GainedStepService gainedStepService;


    @Autowired
    public UserController(UserService userService, GainedCertificateService gainedCertificateService, GainedStepService gainedStepService) {
        this.userService = userService;
        this.gainedCertificateService = gainedCertificateService;
        this.gainedStepService = gainedStepService;
    }


    @PostMapping("/api/user/sign_in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = userService.signIn(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }


//    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/api/user/add_user")
    public ResponseEntity addNew(@RequestBody AddUserRequest userRequest) {
        return ResponseEntity.ok(userService.addNew(userRequest));
    }

    //@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/user/all")
    public ResponseEntity<List<UserResponse>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/user/with_filters")
    public ResponseEntity<List<UserResponse>> getAllWithFilters(@RequestParam String name ) {
        return new ResponseEntity(userService.getWithFilters(name), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/api/user/{id}")
    public ResponseEntity remove(@PathVariable String id){
        userService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable String id) {
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/user/certificates/{userId}")
    public ResponseEntity<GainedCertificateResponse> getGainedCertificate(@PathVariable String userId) {
        return new ResponseEntity(gainedCertificateService.getUserCertificates(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/user/certificates/confirmed/{userId}")
    public ResponseEntity<GainedCertificateResponse> getConfirmedGainedCertificate(@PathVariable String userId) {
        return new ResponseEntity(gainedCertificateService.getUserCertificatesFilterByConfirmed(userId,true), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/user/certificates/unconfirmed/{userId}")
    public ResponseEntity<GainedCertificateResponse> geUnconfirmedUserGainedCertificate(@PathVariable String userId) {
        return new ResponseEntity(gainedCertificateService.getUserCertificatesFilterByConfirmed(userId,false), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/user/steps/{userId}")
    public ResponseEntity<GainedStepResponse> getUserGainedSteps(@PathVariable String userId) {
        return new ResponseEntity(gainedStepService.getUserSteps(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/user/steps/confirmed/{userId}")
    public ResponseEntity<GainedStepResponse> getConfirmedUserGainedSteps(@PathVariable String userId) {
        return new ResponseEntity(gainedStepService.getUserStepsFilterByConfirmed(userId,true), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/user/steps/unconfirmed/{userId}")
    public ResponseEntity<GainedStepResponse> getUnconfirmedUserGainedSteps(@PathVariable String userId) {
        return new ResponseEntity(gainedStepService.getUserStepsFilterByConfirmed(userId,false), HttpStatus.OK);
    }



}