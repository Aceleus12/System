package pl.edu.wat.demo.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
public class StepRequest {
    private String name;
    private String description;
}
