package com.telemachapi.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Provides entity for Address table in database. 
 * @author Matej
 *
 */
@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "streetNo")
	private int streetNo;

	@Column(name = "street")
	private String street;

	@Column(name = "city")
	private String city;
	
	@Column(name = "post")
	private String post;
	
	@Column(name = "postNo")
	private int postNo;
	
	@OneToMany(mappedBy = "address", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("address")
	private List<Service> serviceList = new ArrayList<>();
	

	public Address() {

	}
	
	public Address(int streetNo, String street, String city, String post, int postNo) {
		this.streetNo = streetNo;
		this.street = street;
		this.city = city;
		this.post = post;
		this.postNo = postNo;
	}

	public Address(int streetNo, String street, String city, String post, int postNo, List<Service> serviceList) {
		this.streetNo = streetNo;
		this.street = street;
		this.city = city;
		this.post = post;
		this.postNo = postNo;
		this.serviceList = serviceList;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(int streetNo) {
		this.streetNo = streetNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	
    public List<Service> getServiceList() {
        return serviceList;
    }
    
    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
    
    public void addToServiceList(Service service) {
    	service.setAddress(this);
        this.serviceList.add(service);
    }
}
	