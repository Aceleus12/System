package pl.edu.wat.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.response.GainedCertificateResponse;
import pl.edu.wat.demo.dtos.response.GainedStepResponse;
import pl.edu.wat.demo.services.GainedCertificateService;
import pl.edu.wat.demo.services.GainedStepService;

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

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/api/gained_certificates/{id}")
    public ResponseEntity remove(@PathVariable String id){
        if(gainedCertificateService.removeById(id)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity((HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/gained_certificates/{id}")
    public ResponseEntity<GainedCertificateResponse> get(@PathVariable String id) {
        return new ResponseEntity(gainedCertificateService.get(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/gained_certificates")
    public ResponseEntity<List<GainedCertificateResponse>> getAll() {
        return new ResponseEntity(gainedCertificateService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/api/gained_certificates/add_file/{id}")
    public ResponseEntity addFile(@RequestParam MultipartFile file, @PathVariable String id) {
        return new ResponseEntity(gainedCertificateService.addFile(file,id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/api/gained_certificates/confirm_collecting/{id}")
    public ResponseEntity confirmCollecting(@PathVariable String id) {
        GainedCertificateResponse gainedCertificateResponse = gainedCertificateService.confirmCollecting(id);
        if(gainedCertificateResponse == null){
            return new ResponseEntity(null,HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(gainedCertificateResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/gained_certificates/confirmed/{gainedCertificateID}/{userId}")
    public ResponseEntity<List<GainedStepResponse>> getConfirmedByCertificateAndUser(@PathVariable String gainedCertificateID, @PathVariable String userId) {
        return new ResponseEntity(gainedStepService.getByCertificateAndUserAndConfirmed(gainedCertificateID,userId,true), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/api/gained_certificates/unconfirmed/{gainedCertificateID}/{userId}")
    public ResponseEntity<List<GainedStepResponse>> getUnconfirmedByCertificateAndUser(@PathVariable String gainedCertificateID, @PathVariable String userId) {
        return new ResponseEntity(gainedStepService.getByCertificateAndUserAndConfirmed(gainedCertificateID,userId,false), HttpStatus.OK);
    }




}