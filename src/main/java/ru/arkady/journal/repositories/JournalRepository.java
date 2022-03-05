package ru.arkady.journal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arkady.journal.entities.SchoolClass;
import ru.arkady.journal.entities.Journal;
import ru.arkady.journal.entities.Teacher;

import java.util.List;
import java.util.Optional;


public interface JournalRepository extends JpaRepository<Journal, Integer> {
    Optional<Journal> findByJournalId(int id);
    List<Journal> findByJournalTypeAndTeacher(Journal.JournalType journalType, Teacher teacher);
    Optional<Journal>findBySchoolClassAndTeacherAndJournalType(SchoolClass schoolClass, Teacher teacher, Journal.JournalType journalType);
}
