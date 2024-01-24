package com.helphi.helphisvc.repository;

import com.helphi.helphisvc.dto.AddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressDTO, String> {
}
