package com.helphi.repository;

import com.helphi.api.GpSurgery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GpSurgeryRepository extends JpaRepository<GpSurgery, UUID> {
}
