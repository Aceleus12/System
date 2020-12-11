package pl.edu.wat.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.demo.dtos.CertificateRequest;
import pl.edu.wat.demo.dtos.CertificateResponse;
import pl.edu.wat.demo.dtos.UserRequest;
import pl.edu.wat.demo.dtos.UserResponse;
import pl.edu.wat.demo.services.CertificateService;

import java.util.List;

@RestController
@CrossOrigin
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/api/certificates")
    public ResponseEntity<List<CertificateResponse>> getAll() {
        return new ResponseEntity<>(certificateService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/api/certificates/filtered")
    public ResponseEntity<List<CertificateResponse>> getFilteredBy(@RequestParam String name) {
        return new ResponseEntity(certificateService.getFilteredBy(name), HttpStatus.OK);
    }

    @PostMapping("/api/certificates")
    public ResponseEntity addNew(@RequestBody CertificateRequest userRequest) {
        certificateService.addNew(userRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/api/certificates/{id}")
    public ResponseEntity remove(@PathVariable String id){
        if(certificateService.removeById(id)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/api/certificates/{id}")
    public ResponseEntity<CertificateResponse> get(@PathVariable String id) {
        return new ResponseEntity<>(certificateService.get(id), HttpStatus.OK);
    }

}