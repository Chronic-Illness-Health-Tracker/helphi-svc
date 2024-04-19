package com.helphi.repository;

import com.helphi.api.Address;
import com.helphi.api.user.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRegistrationRepository extends JpaRepository<Registration, UUID> {

    @Query("SELECT a FROM Registration a WHERE a.code = :code")
    Optional<Registration> getbyCode(@Param("code") String code);

    @Query("SELECT a FROM Registration a WHERE a.referenceId = :userId")
    Optional<Registration> getbyUserId(@Param("userId") UUID userId);
}
