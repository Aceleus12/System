package pl.edu.wat.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.dtos.CertificateRequest;
import pl.edu.wat.demo.dtos.CertificateResponse;
import pl.edu.wat.demo.entities.FileEntity;
import pl.edu.wat.demo.services.CertificateService;
import pl.edu.wat.demo.services.FileService;

import java.util.List;

@RestController
@CrossOrigin
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/api/file")
    public ResponseEntity add(MultipartFile file) {
        return new ResponseEntity(fileService.storeFile(file), HttpStatus.OK);
    }

<<<<<<< Updated upstream
=======
    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
>>>>>>> Stashed changes
    @GetMapping("/api/file/{fileId}")
    public ResponseEntity downloadFile(@PathVariable String fileId) {
        // Load file from database
        FileEntity fileEntity = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                .body(new ByteArrayResource(fileEntity.getData()));
    }



}