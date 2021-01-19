package pl.edu.wat.demo.entities;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="certificate")
public class CertificateEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "certificateId")
    private String id;
    private String name;
    private String description;
    private int cost;
    @NotNull
    @OneToOne
    private StepEntity firstStep;
    @OneToMany(mappedBy = "certificate", cascade=CascadeType.REMOVE)
    private List<StepEntity> stepEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "certificate", cascade=CascadeType.REMOVE)
    private List<GainedCertificateEntity> gainedCertificateEntityList = new ArrayList<>();
}
