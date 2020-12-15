package pl.edu.wat.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.request.StartGainingCertificateRequest;
import pl.edu.wat.demo.services.*;

@RestController
@CrossOrigin
public class StepController {

    private final GainedStepService gainedStepService;
    private final CertificateStepService certificateStepService;
    private final GainedCertificateService gainedCertificateService;
    private final FileService fileService;

    @Autowired
    public StepController(GainedStepService gainedStepService, GainedCertificateService gainedCertificateService, FileService fileService, CertificateStepService certificateStepService) {
        this.gainedStepService = gainedStepService;
        this.gainedCertificateService = gainedCertificateService;
        this.fileService = fileService;
        this.certificateStepService = certificateStepService;
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/api/start_procedure")
    public ResponseEntity startProcedure(@RequestBody StartGainingCertificateRequest startGainingCertificateRequest) {
        int response_code = gainedCertificateService.startProcedure(startGainingCertificateRequest);
        if(response_code==1){
            return new ResponseEntity(HttpStatus.OK);
        }
        else if(response_code == -1){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/api/step/confirm/{gainedStepId}")
    public ResponseEntity endStep(@PathVariable String gainedStepId) {
        if(gainedStepService.endStep(gainedStepId)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/api/gained_step/add_comment/{gainedStepId}")
    public ResponseEntity addComment(@PathVariable String gainedStepId, @RequestParam String comment) {
        if(gainedStepService.addComment(gainedStepId,comment)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/api/gained_step/add_file/{gainedStepId}")
    public ResponseEntity addFileToGained(@RequestParam MultipartFile file,@PathVariable String gainedStepId) {
        return new ResponseEntity(gainedStepService.addFile(file,gainedStepId),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/api/step/add_file/{stepId}")
    public ResponseEntity addFile(@RequestParam MultipartFile file,@PathVariable String stepId) {
        return new ResponseEntity(certificateStepService.addFile(file,stepId),HttpStatus.OK);
    }
//
//    @GetMapping("/api/users")
//    public ResponseEntity<List<UserResponse>> getAll() {
//        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
//    }
//
//
//
//    @DeleteMapping("/api/users/{id}")
//    public ResponseEntity remove(@PathVariable String id){
//        userService.removeById(id);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @GetMapping("/api/users/{id}")
//    public ResponseEntity<UserResponse> get(@PathVariable int id) {
//        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
//    }
}