package pl.edu.wat.demo.entities;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="stepProceeded")
public class GainedStepEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="stepProceededId")
    private String id;
    private boolean confirmed = false;
    private Date startDate;
    private Date stopDate;
    @OneToOne
    private FileEntity fileEntity;
    private String comment;
    @OneToOne
    private GainedCertificateEntity gainedCertificateEntity;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "certificateStepId")
    private StepEntity certificateStep;
}
