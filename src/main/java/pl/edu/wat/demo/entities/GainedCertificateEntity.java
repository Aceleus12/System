package pl.edu.wat.demo.entities;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="gainedCertificate")
public class GainedCertificateEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="gainedCertificateId")
    private String id;
    private Date gainDate;
    private Date collectDate;
    private boolean gained;
    private boolean collected;
    @OneToOne
    private FileEntity fileEntity;
    @OneToOne
    private GainedStepEntity actualStep;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "certificateId")
    private CertificateEntity certificate;






}
