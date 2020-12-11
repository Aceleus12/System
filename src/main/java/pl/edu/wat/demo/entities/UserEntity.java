package pl.edu.wat.demo.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

import java.util.ArrayList;

@Entity
@Data
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="userId")
    private String id;
    private String email;
    private String surname;
    private String name;
    private String pesel;
    private String fatherName;
    @OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE)
    private List<GainedCertificateEntity> gainedCertificateEntityList  = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE)
    private List<GainedStepEntity> gainedStepEntityList = new ArrayList<>();
}
