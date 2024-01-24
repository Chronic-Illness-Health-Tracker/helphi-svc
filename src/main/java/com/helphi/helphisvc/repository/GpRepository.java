package com.helphi.helphisvc.repository;

import com.helphi.helphisvc.dto.GpDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpRepository extends JpaRepository<GpDTO, String> {
}
