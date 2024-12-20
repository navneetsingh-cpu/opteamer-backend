package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.TeamMember;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 17.
 */

public interface TeamMemberRepository extends CrudRepository<TeamMember, Long> {
    Optional<TeamMember> findById(Long id);
}
