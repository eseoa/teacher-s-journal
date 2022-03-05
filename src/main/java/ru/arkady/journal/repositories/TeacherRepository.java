package ru.arkady.journal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkady.journal.entities.Teacher;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findByPhone(String phone);

    Optional<Teacher> findByTeacherId(int id);
}
