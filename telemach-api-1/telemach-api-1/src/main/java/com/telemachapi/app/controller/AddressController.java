package com.telemachapi.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.telemachapi.app.model.Address;
import com.telemachapi.app.model.Service;
import com.telemachapi.app.repository.AddressRepository;

/**
 * This REST controller contains functions for retrieving specific data from table Address and sometimes it's child tables too
 * or modifying it.
 * @author Matej
 *
 */
@RestController
@RequestMapping(value = "/addresses", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
public class AddressController {
	@Autowired
	AddressRepository addressRepository;

	/**
	 * This function searches the AddressRepository for all addresses including their services.
	 * @return ResponseEntity containing Address object. Can return either in JSON or XML form, based on request.
	 */
	@GetMapping("")
	public ResponseEntity<List<Address>> getAll() {
		try {
			List<Address> addresses = new ArrayList<Address>();
			addressRepository.findAll().forEach(addresses::add);

			if (addresses.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(addresses, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This function searches the AddressRepository for all addresses with provided id, including their services.
	 * @return ResponseEntity containing Address object. Can return either in JSON or XML form, based on request.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Address> getAddressById(@Valid @PathVariable("id") long id) {
		Optional<Address> addressData = addressRepository.findById(id);
		if (addressData.isPresent()) {
			return new ResponseEntity<>(addressData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This function searches the AddressRepository for all addresses with provided address data, including their services.
	 * @return ResponseEntity containing Address object. Can return either in JSON or XML form, based on request.
	 */
	@GetMapping("/{streetno}/{street}/{city}/{post}/{postno}")
	public ResponseEntity<Address> getAddressByAttributes(@Valid @PathVariable("streetno") int streetNo, @Valid @PathVariable("street") String street, @Valid @PathVariable("city") String city, @Valid @PathVariable("post") String post, @Valid @PathVariable("postno") int postNo) {
		Optional<Address> addressData = addressRepository.findByStreetNoAndStreetAndCityAndPostAndPostNo(streetNo, street, city, post, postNo);
		if (addressData.isPresent()) {
			return new ResponseEntity<>(addressData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This function creates a new Address entry in database, based on Address provided in request body. It also inserts any Services,
	 * if they are present in Address object.
	 * @return ResponseEntity containing newly created Address object. Can return either in JSON or XML form, based on request.
	 */
	@PostMapping("/create")
	public ResponseEntity<Address> createAddress(@Valid @RequestBody Address address) {
		try {
			List<Service> services = new ArrayList<Service>();
			List<Service> servicesWithAddressId = new ArrayList<Service>();
			Address newAddress = new Address(address.getStreetNo(), address.getStreet(), address.getCity(), address.getPost(), address.getPostNo());
			if(address.getServiceList() != null) {
				services = address.getServiceList();
				for (Service service : services) {
					service.setAddress(newAddress);
					servicesWithAddressId.add(service);
				}
				newAddress.setServiceList(servicesWithAddressId);
			}
			Address _address = addressRepository.save(newAddress);

			return new ResponseEntity<>(_address, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * This function updated an existing Address entry in database, based on Address and its id, provided in request body. It also updates any Services,
	 * if they are present in Address object.
	 * @return ResponseEntity containing updated Address object. Can return either in JSON or XML form, based on request.
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<Address> updateAddress(@Valid @PathVariable("id") long id, @Valid @RequestBody Address address) {
		List<Service> services = new ArrayList<Service>();
		List<Service> servicesWithAddressId = new ArrayList<Service>();
		Optional<Address> addressData = addressRepository.findById(id);
		try {
			if (addressData.isPresent()) {
				Address _address = addressData.get();
				_address.setStreetNo(address.getStreetNo());
				_address.setStreet(address.getStreet());
				_address.setCity(address.getCity());
				_address.setPost(address.getPost());
				_address.setPostNo(address.getPostNo());

				if(address.getServiceList() != null) {
					services = address.getServiceList();
					for (Service service : services) {
						service.setAddress(_address);
						servicesWithAddressId.add(service);
					}
					_address.setServiceList(servicesWithAddressId);
				}
				return new ResponseEntity<>(addressRepository.save(_address), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * This function deletes Address entry in database, based on Address ID provided in request. It also deletes and services associated
	 * with this Address (Cascade option = on)
	 * @return HttpStatus 204 No content upon successful deletion.
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteAddress(@Valid @PathVariable("id") long id) {
		try {
			addressRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
