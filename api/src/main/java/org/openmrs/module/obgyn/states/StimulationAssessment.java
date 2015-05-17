package org.openmrs.module.obgyn.states;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class StimulationAssessment extends BaseOpenmrsObject implements Serializable, Comparable<StimulationAssessment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date assessmentDate;
	private int stimulationDay;
	private int estradiolE2;
	private float drugUnits;
	private String leftOvaryAssessment;
	private String rightOvaryAssessment;
	private float endometriumLength;
	private String drug;
	
	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public int getStimulationDay() {
		return stimulationDay;
	}

	public void setStimulationDay(int stimulationDay) {
		this.stimulationDay = stimulationDay;
	}

	public int getEstradiolE2() {
		return estradiolE2;
	}

	public void setEstradiolE2(int estradiolE2) {
		this.estradiolE2 = estradiolE2;
	}

	public float getDrugUnits() {
		return drugUnits;
	}

	public void setDrugUnits(float drugUnits) {
		this.drugUnits = drugUnits;
	}

	public String getLeftOvaryAssessment() {
		return leftOvaryAssessment;
	}

	public void setLeftOvaryAssessment(String leftOvaryAssessment) {
		this.leftOvaryAssessment = leftOvaryAssessment;
	}

	public String getRightOvaryAssessment() {
		return rightOvaryAssessment;
	}

	public void setRightOvaryAssessment(String rightOvaryAssessment) {
		this.rightOvaryAssessment = rightOvaryAssessment;
	}

	public float getEndometriumLength() {
		return endometriumLength;
	}

	public void setEndometriumLength(float endometriumLength) {
		this.endometriumLength = endometriumLength;
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

	@Override
	public int compareTo(StimulationAssessment arg0) {
		
		return this.getAssessmentDate().compareTo(arg0.getAssessmentDate());
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

}
