package ru.arkady.journal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.arkady.journal.entities.*;

import javax.transaction.Transactional;
import java.util.Optional;

public interface GradeRepository extends CrudRepository<Grade, Integer> {

    Optional<Grade> findByGradeId(int id);
    @Transactional
    void deleteByGradeId(int id);

}
