package ru.arkady.journal.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table (name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int studentId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private SchoolClass schoolClass;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private List<Grade> grades;

}
