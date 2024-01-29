package com.helphi.repository;

import com.helphi.api.Gp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GpRepository extends JpaRepository<Gp, UUID> {
}
