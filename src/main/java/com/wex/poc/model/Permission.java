package com.wex.poc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

@Indexed(index="permissions")
@Entity
@Table(name = "permission")
public class Permission {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "dsecription", nullable = false)
	private String dsecription;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDsecription() {
		return dsecription;
	}

	public void setDsecription(String dsecription) {
		this.dsecription = dsecription;
	}

}
