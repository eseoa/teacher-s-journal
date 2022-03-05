package ru.arkady.journal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkady.journal.entities.*;
import ru.arkady.journal.entities.SchoolClass;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findBySchoolClassAndSubject(SchoolClass schoolClass, Subject subject);
    Optional<Lesson> findByLessonId(int id);

}
