package com.helphi.repository;

import com.helphi.api.Gp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GpRepository extends JpaRepository<Gp, UUID> {

    @Query("SELECT p FROM Gp p WHERE p.surgery.id = :surgeryId")
    List<Gp> findBySurgeryId(@Param("surgeryId") UUID surgeryId);
}
