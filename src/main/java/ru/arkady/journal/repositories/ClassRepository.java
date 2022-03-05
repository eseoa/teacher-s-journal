package ru.arkady.journal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkady.journal.entities.SchoolClass;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<SchoolClass, Integer> {
    Optional<SchoolClass> findByClassId(int id);


}
