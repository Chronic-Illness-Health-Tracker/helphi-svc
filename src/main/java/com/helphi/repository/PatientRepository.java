package com.helphi.repository;

import com.helphi.api.user.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    @Query("SELECT p FROM Patient p JOIN p.conditions c WHERE c.id = :conditionId")
    List<Patient> findByHealthConditionId(@Param("conditionId") UUID conditionId);

    @Query("SELECT p FROM Patient p WHERE p.nhsNumber LIKE %:nhsNumber%")
    List<Patient> findByNHSNumber(@Param("nhsNumber") String nhsNumber);

    @Query("SELECT p FROM Patient p " +
            "WHERE CONCAT(p.forename, ' ', p.middlenames, ' ', p.lastname) LIKE %:searchValue% OR " +
            "CONCAT(p.forename, ' ', p.lastname) LIKE %:searchValue%")
    List<Patient> findByName(@Param("searchValue") String searchValue);


    @Query("SELECT p FROM Patient p WHERE p.userId = :userId")
    Optional<Patient> findByUserId(@Param("userId") String userId);

}
