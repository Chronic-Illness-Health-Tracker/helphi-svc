package com.helphi.helphisvc.repository;

import com.helphi.api.GpSurgery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpSurgeryRepository extends JpaRepository<GpSurgery, String> {
}
