package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.Role;
import io.medsys.opteamer.model.enums.ERole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Navneet
 * @version 1.0
 * @since 2024. 06. 22.
 */

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
