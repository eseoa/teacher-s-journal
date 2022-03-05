package ru.arkady.journal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkady.journal.entities.Subject;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Subject> findBySubjectId(int id);

}
