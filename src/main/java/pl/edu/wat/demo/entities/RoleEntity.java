package pl.edu.wat.demo.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "roleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;


}
