package pl.edu.wat.demo.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

import java.util.ArrayList;
import java.util.Set;

@Entity
@Data
@Table(name="user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class UserEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="userId")
    private String id;
    private String username;
    private String email;
    private String surname;
    private String name;
    private String pesel;
    private String fatherName;
    @OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE)
    private List<GainedCertificateEntity> gainedCertificateEntityList  = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE)
    private List<GainedStepEntity> gainedStepEntityList = new ArrayList<>();
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<RoleEntity> roleEntities = new HashSet<>();
}
