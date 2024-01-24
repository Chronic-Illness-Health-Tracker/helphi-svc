package com.helphi.helphisvc.repository;

import com.helphi.helphisvc.dto.GpSurgeryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpSurgeryRepository extends JpaRepository<GpSurgeryDTO, String> {
}
