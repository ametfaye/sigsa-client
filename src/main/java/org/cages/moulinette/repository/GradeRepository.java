package org.cages.moulinette.repository;

import org.cages.moulinette.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("gradeRepository")
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
