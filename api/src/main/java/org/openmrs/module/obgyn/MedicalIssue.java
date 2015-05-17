package org.openmrs.module.obgyn;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class MedicalIssue extends BaseOpenmrsObject implements Serializable, Comparable<MedicalIssue>  {

	/**
	 * 
	 */
	@JsonIgnore
	private int encounterId;
	
	private static final long serialVersionUID = -3257020227321624445L;
	private boolean isPast;
	private String stillPresent;
	private Date diagnosisDate;
	private Date diagnosisEndDate;
	private String medicalHistoryAdded;
	private String medicalHistoryAddedText;
	private String type;
	
	public Date getDiagnosisDate() {
		return diagnosisDate;
	}

	public void setDiagnosisDate(Date diagnosisDate) {
		this.diagnosisDate = diagnosisDate;
	}

	public Date getDiagnosisEndDate() {
		return diagnosisEndDate;
	}

	public void setDiagnosisEndDate(Date diagnosisEndDate) {
		this.diagnosisEndDate = diagnosisEndDate;
	}

	public String getMedicalHistoryAdded() {
		return medicalHistoryAdded;
	}

	public void setMedicalHistoryAdded(String medicalHistoryAdded) {
		this.medicalHistoryAdded = medicalHistoryAdded;
	}

	public String getMedicalHistoryAddedText() {
		return medicalHistoryAddedText;
	}

	public void setMedicalHistoryAddedText(String medicalHistoryAddedText) {
		this.medicalHistoryAddedText = medicalHistoryAddedText;
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

	public boolean isPast() {
		return isPast;
	}

	public void setPast(boolean isPast) {
		this.isPast = isPast;
	}

	public String getStillPresent() {
		return stillPresent;
	}

	public void setStillPresent(String stillPresent) {
		this.stillPresent = stillPresent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int compareTo(MedicalIssue arg0) {
		if (arg0 == null || arg0.getDiagnosisDate() == null)
			return 1;
		
		if (this.diagnosisDate == null)
			return -1;
		
		return this.diagnosisDate.compareTo(arg0.getDiagnosisDate());
	}

	public int getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(int encounterId) {
		this.encounterId = encounterId;
	}

}
