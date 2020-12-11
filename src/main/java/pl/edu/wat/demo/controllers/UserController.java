package pl.edu.wat.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.demo.dtos.*;
import pl.edu.wat.demo.services.GainedCertificateService;
import pl.edu.wat.demo.services.GainedStepService;
import pl.edu.wat.demo.services.UserService;

import java.util.List;

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

    @GetMapping("/api/user/all")
    public ResponseEntity<List<UserResponse>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/api/user/with_filters")
    public ResponseEntity<List<UserResponse>> getAllWithFilters(@RequestParam String name) {
        return new ResponseEntity(userService.getWithFilters(name), HttpStatus.OK);
    }

    @PostMapping("/api/user")
    public ResponseEntity addNew(@RequestBody UserRequest userRequest) {
        userService.addNew(userRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity remove(@PathVariable String id){
        userService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable String id) {
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    }

    @GetMapping("/api/user/certificates/{userId}")
    public ResponseEntity<GainedCertificateResponse> getGainedCertificate(@PathVariable String userId) {
        return new ResponseEntity(gainedCertificateService.getUserCertificates(userId), HttpStatus.OK);
    }

    @GetMapping("/api/user/certificates/confirmed/{userId}")
    public ResponseEntity<GainedCertificateResponse> getConfirmedGainedCertificate(@PathVariable String userId) {
        return new ResponseEntity(gainedCertificateService.getUserCertificatesFilterByConfirmed(userId,true), HttpStatus.OK);
    }

    @GetMapping("/api/user/certificates/unconfirmed/{userId}")
    public ResponseEntity<GainedCertificateResponse> geUnconfirmedUserGainedCertificate(@PathVariable String userId) {
        return new ResponseEntity(gainedCertificateService.getUserCertificatesFilterByConfirmed(userId,false), HttpStatus.OK);
    }

    @GetMapping("/api/user/steps/{userId}")
    public ResponseEntity<GainedStepResponse> getUserGainedSteps(@PathVariable String userId) {
        return new ResponseEntity(gainedStepService.getUserSteps(userId), HttpStatus.OK);
    }

    @GetMapping("/api/user/steps/confirmed/{userId}")
    public ResponseEntity<GainedStepResponse> getConfirmedUserGainedSteps(@PathVariable String userId) {
        return new ResponseEntity(gainedStepService.getUserStepsFilterByConfirmed(userId,true), HttpStatus.OK);
    }


    @GetMapping("/api/user/steps/unconfirmed/{userId}")
    public ResponseEntity<GainedStepResponse> getUnconfirmedUserGainedSteps(@PathVariable String userId) {
        return new ResponseEntity(gainedStepService.getUserStepsFilterByConfirmed(userId,false), HttpStatus.OK);
    }



}