package com.telemachapi.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.telemachapi.app.model.Service;

/**
 * Contains definitions of functions for querying data with JPA
 * @author Matej
 *
 */
public interface ServiceRepository extends JpaRepository<Service, Long> {
	List<Service> findAll();
}


