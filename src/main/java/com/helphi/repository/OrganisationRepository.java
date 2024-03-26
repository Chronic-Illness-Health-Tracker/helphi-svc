package com.helphi.repository;

import com.helphi.api.GpSurgery;
import com.helphi.api.organisation.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {

    @Query("SELECT p FROM Organisation p WHERE LOWER(p.name) LIKE %:organisationName%")
    List<Organisation> findByName(@Param("organisationName") String organisationName);
}
