package org.openmrs.module.obgyn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Surgery extends BaseOpenmrsObject implements Serializable, Comparable<Surgery> {

	@JsonIgnore
	private int encounterId;	
	
	private Date date;
	private ArrayList<String> types;
	private String complaints;
	private String result;
	private String note;
	private boolean isPast;
	private boolean paidByPatient;
	private boolean performedByProvider;
	
	private int documentObsId;
	
	public Surgery() {
		
		types = new ArrayList<String>();
	}
	
	public void addType(String type) {
		types.add(type);
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getComplaints() {
		return complaints;
	}
	public void setComplaints(String complaints) {
		this.complaints = complaints;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public boolean isPaidByPatient() {
		return paidByPatient;
	}

	public void setPaidByPatient(boolean paidByPatient) {
		this.paidByPatient = paidByPatient;
	}

	public boolean getPerformedByProvider() {
		return performedByProvider;
	}

	public void setPerformedByProvider(boolean performedByProvider) {
		this.performedByProvider = performedByProvider;
	}
	
	public String toString() {
		return "Surgery : " + getDate() + ", " + getTypes().toArray().toString() + ", "+ getResult() +", "+getComplaints() +","+getPerformedByProvider() + ", "+isPaidByPatient();
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer arg0) {
		// TODO Auto-generated method stub
		
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ArrayList<String> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}

	public boolean isPast() {
		return isPast;
	}

	public void setPast(boolean isPast) {
		this.isPast = isPast;
	}


	public int getDocumentObsId() {
		return documentObsId;
	}

	public void setDocumentObsId(int documentObsId) {
		this.documentObsId = documentObsId;
	}

	public int compareTo(Surgery otherSurgery) {
		
		if (otherSurgery == null || otherSurgery.getDate() == null)
			return 1;
		
		if (this.date == null)
			return -1;
		
		return this.date.compareTo(otherSurgery.getDate());		
	}

	public int getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(int encounterId) {
		this.encounterId = encounterId;
	}

}
