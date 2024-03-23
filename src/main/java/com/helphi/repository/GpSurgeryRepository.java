package com.helphi.repository;

import com.helphi.api.GpSurgery;
import com.helphi.api.user.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GpSurgeryRepository extends JpaRepository<GpSurgery, UUID> {

    @Query("SELECT p FROM GpSurgery p WHERE LOWER(p.name) LIKE %:surgeryName%")
    List<GpSurgery> findByName(@Param("surgeryName") String surgeryName);
}
