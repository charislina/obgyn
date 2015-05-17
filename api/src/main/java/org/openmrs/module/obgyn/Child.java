package org.openmrs.module.obgyn;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;
import org.springframework.format.annotation.DateTimeFormat;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Child extends BaseOpenmrsObject implements Serializable, Comparable<Child> {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private int encounterId;
	
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthDate;
	private String fatherFirstName;
	private String fatherLastName;
	private String sex;
	private String firstName;
	private String lastName;
	private String obstetrician;
	private Integer childId;
	private String name;
	private String fatherName;
	private GynHistory history;
	private boolean stillAlive;
	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getFatherFirstName() {
		return fatherFirstName;
	}

	public void setFatherFirstName(String fatherFirstName) {
		this.fatherFirstName = fatherFirstName;
	}

	public String getFatherLastName() {
		return fatherLastName;
	}

	public void setFatherLastName(String fartherLastName) {
		this.fatherLastName = fartherLastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JsonIgnore
	public String getSexString() {
		if (sex == null)
			return "";
		
		if (sex.equalsIgnoreCase("Female gender"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.female");

		if (sex.equalsIgnoreCase("Male gender"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.male");

		if (sex.equalsIgnoreCase("Unknown"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.unknown");

		return sex;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getObstetrician() {
		return obstetrician;
	}

	public void setObstetrician(String obstetrician) {
		this.obstetrician = obstetrician;
	}

	@Override
	public Integer getId() {
		return childId;
	}

	@Override
	public void setId(Integer arg0) {
		childId = arg0;
	}

	public GynHistory getHistory() {
		return history;
	}

	public void setHistory(GynHistory history) {
		this.history = history;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public boolean isStillAlive() {
		return stillAlive;
	}

	public void setStillAlive(boolean stillAlive) {
		this.stillAlive = stillAlive;
	}

	@Override
	public int compareTo(Child arg0) {
		if (arg0 == null || arg0.getBirthDate() == null)
			return 1;
		if (this.birthDate == null)
			return -1;
		
		return this.birthDate.compareTo(arg0.getBirthDate());
	}


	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	
	public int getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(int encounterId) {
		this.encounterId = encounterId;
	}

}
