package com.helphi.repository;

import com.helphi.api.user.Clinitian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClinitianRepository extends JpaRepository<Clinitian, UUID> {
}
