package ru.arkady.journal.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classes")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private int classId;
    @Column(name = "class_name")
    private String className;
    @Column(name = "students_count")
    private int studentsCount;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "classsubject",
            joinColumns = {@JoinColumn(name = "class_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id")}
    )
    private List<Subject> subjects;
}
