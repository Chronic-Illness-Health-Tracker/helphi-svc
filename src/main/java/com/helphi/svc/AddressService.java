package com.helphi.svc;

import com.helphi.api.Address;
import com.helphi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Optional<Address> getAddressById(UUID addressId) {
        return this.addressRepository.findById(addressId);
    }

    public Optional<Address> getAddressByValues(Address address) {
        List<Address> addresses = this.addressRepository.getAddressByValues(address.getAddresslineOne(),
                address.getAddresslineTwo(),
                address.getPostcode());

        Address addressToReturn = null;

        if (!addresses.isEmpty()) {
            addressToReturn = addresses.get(0);
        }

        return Optional.ofNullable(addressToReturn);
    }
}
