package ru.arkady.journal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import ru.arkady.journal.entities.SchoolClass;
import ru.arkady.journal.entities.Student;

import java.util.List;



public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findBySchoolClass(SchoolClass schoolClass);
    Student findByStudentId(int id);
}
