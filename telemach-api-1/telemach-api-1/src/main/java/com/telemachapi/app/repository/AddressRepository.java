package com.telemachapi.app.repository;

import com.telemachapi.app.model.Address;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains definitions of functions for querying data with JPA
 * @author Matej
 *
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
	List<Address> findAll();
	Optional<Address> findByStreetNoAndStreetAndCityAndPostAndPostNo(int streetNo, String street, String city, String post, int postNo);
	
	
}


