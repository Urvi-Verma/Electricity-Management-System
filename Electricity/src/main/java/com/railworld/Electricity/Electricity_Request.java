package com.railworld.Electricity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "electricity_request")
public class Electricity_Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "eid")
	private int eid;
	
	@Column(name = "cust_billno")
	private int cust_billno;

	@Column(name = "cust_name")
	private String cust_name;

	@Column(name = "cust_request")
	private String cust_request;

	public Electricity_Request() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public int getCust_billno() {
		return cust_billno;
	}

	public void setCust_billno(int cust_billno) {
		this.cust_billno = cust_billno;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getCust_request() {
		return cust_request;
	}

	public void setCust_request(String cust_request) {
		this.cust_request = cust_request;
	}

	@Override
	public String toString() {
		return "Electricity_Request [eid=" + eid + ", cust_billno=" + cust_billno + ", cust_name=" + cust_name
				+ ", cust_request=" + cust_request + "]";
	}

	
	

}
