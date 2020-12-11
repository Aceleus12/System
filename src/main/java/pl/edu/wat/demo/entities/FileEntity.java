package pl.edu.wat.demo.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="file")
public class FileEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="fileId")
    private String id;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;
//    private String fatherName;
//    @OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE)
//    private List<GainedCertificateEntity> gainedCertificateEntityList  = new ArrayList<>();
//    @OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE)
//    private List<GainedStepEntity> gainedStepEntityList = new ArrayList<>();
}
