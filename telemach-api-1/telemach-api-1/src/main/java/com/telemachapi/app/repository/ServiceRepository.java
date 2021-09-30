package com.telemachapi.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.telemachapi.app.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
	List<Service> findAll();
}


