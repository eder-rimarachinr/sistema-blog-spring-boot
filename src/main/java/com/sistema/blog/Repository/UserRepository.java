package com.sistema.blog.Repository;

import com.sistema.blog.Models.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByEmail(String email);

    Optional<Usuarios> findByUsernameOrEmail(String username, String email);

    Optional<Usuarios> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
