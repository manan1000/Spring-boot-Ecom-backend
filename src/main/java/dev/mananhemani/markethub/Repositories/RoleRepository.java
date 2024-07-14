package dev.mananhemani.markethub.Repositories;

import dev.mananhemani.markethub.Models.AppRole;
import dev.mananhemani.markethub.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByRoleName(AppRole appRole);
}
