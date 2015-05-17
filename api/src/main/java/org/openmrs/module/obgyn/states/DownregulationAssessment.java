
package org.openmrs.module.obgyn.states;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DownregulationAssessment implements Serializable, Comparable<DownregulationAssessment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date assessmentDate;
	private int downregulationDay;
	private int estradiolE2;
	private float estrogenUnits;
	private String leftOvaryAssessment;
	private String rightOvaryAssessment;
	private float endometriumLength;
	
	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public int getEstradiolE2() {
		return estradiolE2;
	}

	public void setEstradiolE2(int estradiolE2) {
		this.estradiolE2 = estradiolE2;
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
	public int compareTo(DownregulationAssessment arg0) {
		
		return this.getAssessmentDate().compareTo(arg0.getAssessmentDate());
	}

	public int getDownregulationDay() {
		return downregulationDay;
	}

	public void setDownregulationDay(int downregulationDay) {
		this.downregulationDay = downregulationDay;
	}

	public float getEstrogenUnits() {
		return estrogenUnits;
	}

	public void setEstrogenUnits(float estrogenUnits) {
		this.estrogenUnits = estrogenUnits;
	}


}
