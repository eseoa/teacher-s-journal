package ru.arkady.journal.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classsubject")
public class ClassSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private SchoolClass schoolClass;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private Subject subject;
}
