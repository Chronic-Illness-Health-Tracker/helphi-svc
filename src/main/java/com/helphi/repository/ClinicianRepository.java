package com.helphi.repository;

import com.helphi.api.user.Clinician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClinicianRepository extends JpaRepository<Clinician, UUID> {

    @Query("SELECT p FROM Clinician p WHERE p.userId = :userId")
    Optional<Clinician> findByUserId(@Param("userId") String userId);
}
