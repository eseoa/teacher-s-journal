package ru.arkady.journal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkady.journal.entities.ClassSubject;

import java.util.Optional;

public interface ClassSubjectRepository extends JpaRepository<ClassSubject, Integer> {
}
