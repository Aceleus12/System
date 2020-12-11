package pl.edu.wat.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.*;
import pl.edu.wat.demo.services.GainedCertificateService;
import pl.edu.wat.demo.services.GainedStepService;
import pl.edu.wat.demo.services.UserService;

import java.util.List;

@RestController
@CrossOrigin
public class GainedCertificatesController {

    private final GainedCertificateService gainedCertificateService;
    private final GainedStepService gainedStepService;

    @Autowired
    public GainedCertificatesController(GainedCertificateService gainedCertificateService, GainedStepService gainedStepService) {
        this.gainedCertificateService = gainedCertificateService;
        this.gainedStepService = gainedStepService;
    }

    @DeleteMapping("/api/gained_certificates/{id}")
    public ResponseEntity remove(@PathVariable String id){
        if(gainedCertificateService.removeById(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity((HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/gained_certificates/{id}")
    public ResponseEntity<GainedCertificateResponse> get(@PathVariable String id) {
        return new ResponseEntity(gainedCertificateService.get(id), HttpStatus.OK);
    }

    @GetMapping("/api/gained_certificates")
    public ResponseEntity<List<GainedCertificateResponse>> getAll() {
        return new ResponseEntity(gainedCertificateService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/api/gained_certificates/add_file/{id}")
    public ResponseEntity addFile(@RequestParam MultipartFile file, @PathVariable String stepId) {
        return new ResponseEntity(gainedCertificateService.addFile(file,stepId),HttpStatus.OK);
    }

    @PutMapping("/api/gained_certificates/confirm_collecting/{id}")
    public ResponseEntity addFile(@PathVariable String stepId) {
        return new ResponseEntity(gainedCertificateService.confirmCollecting(stepId),HttpStatus.OK);
    }

    @GetMapping("/api/gained_certificates/confirmed/{gainedCertificateID}/{userId}")
    public ResponseEntity<List<GainedStepResponse>> getConfirmedByCertificateAndUser(@PathVariable String gainedCertificateID, @PathVariable String userId) {
        return new ResponseEntity(gainedStepService.getByCertificateAndUserAndConfirmed(gainedCertificateID,userId,true), HttpStatus.OK);
    }

    @GetMapping("/api/gained_certificates/unconfirmed/{gainedCertificateID}/{userId}")
    public ResponseEntity<List<GainedStepResponse>> getUnconfirmedByCertificateAndUser(@PathVariable String gainedCertificateID, @PathVariable String userId) {
        return new ResponseEntity(gainedStepService.getByCertificateAndUserAndConfirmed(gainedCertificateID,userId,false), HttpStatus.OK);
    }




}