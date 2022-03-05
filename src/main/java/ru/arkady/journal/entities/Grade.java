package ru.arkady.journal.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int gradeId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id")
    private Lesson lesson;
    @Column(name = "grade")
    private int grade;



}
