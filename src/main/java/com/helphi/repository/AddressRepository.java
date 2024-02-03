package com.helphi.repository;

import com.helphi.api.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Query("SELECT a FROM Address a WHERE UPPER(a.addresslineOne) = UPPER(:addressLineOne) AND UPPER(a.addresslineTwo)= UPPER(:addressLineTwo) AND UPPER(a.postcode) = UPPER(:postcode)")
    List<Address> getAddressByValues(@Param("addressLineOne") String addressLineOne,
                            @Param("addressLineTwo") String addressLineTwo,
                            @Param("postcode") String postcode);
}
