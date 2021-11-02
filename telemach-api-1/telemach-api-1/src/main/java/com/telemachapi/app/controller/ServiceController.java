package com.telemachapi.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telemachapi.app.model.Address;
import com.telemachapi.app.model.Service;
import com.telemachapi.app.repository.AddressRepository;
import com.telemachapi.app.repository.ServiceRepository;

/**
 * This REST controller contains functions for retrieving specific data from table Service or modifying it.
 * @author Matej
 *
 */
@RestController
@RequestMapping(value = "/services", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
public class ServiceController {
	@Autowired 
	ServiceRepository serviceRepository;
	@Autowired
	AddressRepository addressRepository;
	
	/**
	 * This function searches the ServiceRepository for all services.
	 * @return ResponseEntity containing Service object. Can return either in JSON or XML form, based on request.
	 */
	@GetMapping("")
	  public ResponseEntity<List<Service>> getAll() {
	    try {
	      List<Service> services = new ArrayList<Service>();
	      serviceRepository.findAll().forEach(services::add);

	      if (services.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(services, HttpStatus.OK);
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	/**
	 * This function searches the ServiceRepository for Service with given ID.
	 * @return ResponseEntity containing Service object. Can return either in JSON or XML form, based on request.
	 */
	@GetMapping("/{id}")
	  public ResponseEntity<Service> getServiceById(@Valid @PathVariable("id") long id) {
	    Optional<Service> serviceData = serviceRepository.findById(id);

	    if (serviceData.isPresent()) {
	      return new ResponseEntity<>(serviceData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
	/**
	 * This function creates a new Service entry in database, based on Service data in request body. It also specifies it's 
	 * Address with provided Address ID, as every Service needs to be connected to one Address.
	 * @return ResponseEntity containing newly created Service object. Can return either in JSON or XML form, based on request.
	 */
	@PostMapping("/create/{address_id}")//Service needs to have an address, therefore we have to specify it
	  public ResponseEntity<Service> createService(@Valid @RequestBody Service service, @Valid @PathVariable("address_id") long address_id) {
	    try {
	      Optional<Address> addressData = addressRepository.findById(address_id);
	      if (addressData.isPresent()) {
	    	  Address address = addressData.get();
	    	  service.setAddress(address);
	    	  return new ResponseEntity<>(serviceRepository.save(service), HttpStatus.CREATED);
	      }
	      else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	      }

	    } catch (NullPointerException e) {
	    	System.out.println(e.getMessage());
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	/**
	 * This function updated an existing Service entry in database, based on Service and its id, provided in request body.
	 * @return ResponseEntity containing updated Service object. Can return either in JSON or XML form, based on request.
	 */
	@PutMapping("/update/{id}")
	  public ResponseEntity<Service> updateService(@Valid @PathVariable("id") long id, @Valid @RequestBody Service service) {
		Optional<Service> serviceData = serviceRepository.findById(id);

	    try {
		    if (serviceData.isPresent()) {
		      Service _service = serviceData.get();
		      _service.setComment(service.getComment());
		      _service.setService(service.getService());
		      _service.setValue(service.getValue());
		      return new ResponseEntity<>(serviceRepository.save(_service), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		    }
		    catch(Exception e) {
		    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	  }
	
	/**
	 * This function deletes Service entry in database, based on Service ID provided in request.
	 * @return HttpStatus 204 No content upon successful deletion.
	 */
	@DeleteMapping("/delete/{id}")
	  public ResponseEntity<HttpStatus> deleteService(@Valid @PathVariable("id") long id) {
	    try {
	      Optional<Service> serviceData = serviceRepository.findById(id);
	      if (serviceData.isPresent()) {
	    	  Service service = serviceData.get();
	    	  service.getAddress().getServiceList().remove(service);
	    	  serviceRepository.deleteById(id);
	      }
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
}
