package com.helphi.helphisvc.repository;

import com.helphi.api.Gp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpRepository extends JpaRepository<Gp, String> {
}
