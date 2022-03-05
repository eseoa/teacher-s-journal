package ru.arkady.journal.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.arkady.journal.entities.enums.Role;
import ru.arkady.journal.entities.enums.Status;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private int teacherId;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "journals",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "class_id")}
    )
    private List<SchoolClass> classes;

}
