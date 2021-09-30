package com.telemachapi.app.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "service")
public class Service {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "service")
	private String service;

	@Column(name = "value")
	private String value;
	
	@Column(name = "comment")
	private String comment;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false )
    @JsonIgnore
    private Address address;
	
	public Service() {

	}

	public Service(String service, String value, String comment) {
		this.service = service;
		this.value = value;
		this.comment = comment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	//@Transient
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	
}
