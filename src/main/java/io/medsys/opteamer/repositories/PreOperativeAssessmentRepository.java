package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.PreOperativeAssessment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 17.
 */

public interface PreOperativeAssessmentRepository extends CrudRepository<PreOperativeAssessment, String> {

    Optional<PreOperativeAssessment> findByName(String name);

}
