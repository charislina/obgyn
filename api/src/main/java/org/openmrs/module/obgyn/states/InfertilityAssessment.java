package org.openmrs.module.obgyn.states;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.IVFProgram;
import org.openmrs.module.obgyn.IVFProgram.IVFType;
import org.springframework.format.annotation.DateTimeFormat;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class InfertilityAssessment extends IVFState implements Serializable {
	private static final long serialVersionUID = 1L;

	private String infertilityAssessment;
	private String infertilityAssessmentOtherText;
	private String cervicalAssessment;
	private Date cervicalDilatationDate;
	private String cervicalDilatationText;
	private String embryoTransferNote;
	private String spermFactor;
	private Date ovarianAssessmentDate;
	private String leftOvaryAssessment;
	private String rightOvaryAssessment;

	public InfertilityAssessment() {
		super(IVFState.INFERTILITY_ASSESSMENT);
	}

	public String getInfertilityAssessment() {
		return infertilityAssessment;
	}

	public void setInfertilityAssessment(String infertilityAssessment) {
		this.infertilityAssessment = infertilityAssessment;
	}

	public String getInfertilityAssessmentOtherText() {
		return infertilityAssessmentOtherText;
	}

	public void setInfertilityAssessmentOtherText(String infertilityAssessmentOtherText) {
		this.infertilityAssessmentOtherText = infertilityAssessmentOtherText;
	}

	public String getCervicalAssessment() {
		return cervicalAssessment;
	}

	public void setCervicalAssessment(String cervicalAssessment) {
		this.cervicalAssessment = cervicalAssessment;
	}

	public Date getCervicalDilatationDate() {
		return cervicalDilatationDate;
	}

	public void setCervicalDilatationDate(Date cervicalDilatationDate) {
		this.cervicalDilatationDate = cervicalDilatationDate;
	}

	public String getCervicalDilatationText() {
		return cervicalDilatationText;
	}

	public void setCervicalDilatationText(String cervicalDilatationText) {
		this.cervicalDilatationText = cervicalDilatationText;
	}

	public String getEmbryoTransferNote() {
		return embryoTransferNote;
	}

	public void setEmbryoTransferNote(String eggTransferNote) {
		this.embryoTransferNote = eggTransferNote;
	}

	public String getSpermFactor() {
		return spermFactor;
	}

	public void setSpermFactor(String spermFactor) {
		this.spermFactor = spermFactor;
	}

	public Date getOvarianAssessmentDate() {
		return ovarianAssessmentDate;
	}

	public void setOvarianAssessmentDate(Date ovarianAssessmentDate) {
		this.ovarianAssessmentDate = ovarianAssessmentDate;
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


	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.infertility_assessment");
	}

	@Override
	public void stimulate(IVFType type) {
	}

	@Override
	public void downregulate(IVFType type) {
	}
	
	@Override
	public void abandonProgram(IVFType type) {
	}
}
