package com.helphi.repository;

import com.helphi.api.user.Clinician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClinicianRepository extends JpaRepository<Clinician, UUID> {
}
