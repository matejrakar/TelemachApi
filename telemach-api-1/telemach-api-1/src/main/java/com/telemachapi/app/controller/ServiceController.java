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

@RestController
@RequestMapping(value = "/services", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
public class ServiceController {
	@Autowired 
	ServiceRepository serviceRepository;
	@Autowired
	AddressRepository addressRepository;
	
	@GetMapping("")
	  public ResponseEntity<List<Service>> getAll() {
	    try {
	      List<Service> services = new ArrayList<Service>();
	      serviceRepository.findAll().forEach(services::add);

	      if (services.isEmpty()) {
	    	//System.out.println("Addresses are empty.");
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      //System.out.println("There is something in addresses. Returning...");
	      return new ResponseEntity<>(services, HttpStatus.OK);
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	
	@GetMapping("/{id}")
	  public ResponseEntity<Service> getServiceById(@Valid @PathVariable("id") long id) {
	    Optional<Service> serviceData = serviceRepository.findById(id);

	    if (serviceData.isPresent()) {
	      return new ResponseEntity<>(serviceData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
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
