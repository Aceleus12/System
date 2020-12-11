package pl.edu.wat.demo.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="certificateStep")
public class StepEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="certificateStepId")
    private String id;
    private String name;
    private String description;
    @OneToOne
    private FileEntity fileEntity;
    @OneToOne
    private StepEntity nextStep;
    @ManyToOne
    @JoinColumn(name = "certificateId")
    private CertificateEntity certificate;
    @OneToMany(mappedBy = "certificateStep", cascade=CascadeType.REMOVE)
    private List<GainedStepEntity> gainedStepEntityList = new ArrayList<>();

}
