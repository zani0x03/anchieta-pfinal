package com.anchieta.login.repository;

import com.anchieta.login.model.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface SistemaRepository extends JpaRepository<Sistema, UUID> {
}
