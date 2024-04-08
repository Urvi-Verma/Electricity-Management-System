package com.railworld.Electricity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "electricity")
public class Electricity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    private double unit;
    
    @Column(name = "charges")
    private double charges;
    

	public Electricity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Electricity(int id, String name, int unit, double charges) {
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.charges = charges;
	}

	@Override
	public String toString() {
		return "Electricity [id=" + id + ", name=" + name + ", unit=" + unit + ", charges=" + charges + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnit() {
		return unit;
	}

	public void setUnit(double unit) {
		this.unit = unit;
	}

	public double getCharges() {
		return charges;
	}

	public void setCharges(double charges) {
		this.charges = charges;
	}


	
}
