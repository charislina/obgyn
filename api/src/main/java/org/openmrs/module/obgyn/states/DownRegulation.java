package org.openmrs.module.obgyn.states;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.IVFProgram;
import org.openmrs.module.obgyn.IVFProgram.IVFType;
import org.springframework.format.annotation.DateTimeFormat;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DownRegulation extends IVFState implements Serializable {
	
	public DownRegulation() {
		super(IVFState.DOWN_REGULATION);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	
	private Date lastMenstrualPeriodBeforeTherapy;	
	private Date downRegulationStartDate;

	private String downRegulationMethod;
	private HashMap<String, DownregulationAssessment> downregulationAssessments;

	private String drug;
	private boolean isAgonist;
	
	private Date lastMenstrualPeriodAfterTherapy;	
	
	public Date getLastMenstrualPeriodBeforeTherapy() {
		return lastMenstrualPeriodBeforeTherapy;
	}

	public void setLastMenstrualPeriodBeforeTherapy(Date lastMenstrualPeriodBeforeTherapy) {
		this.lastMenstrualPeriodBeforeTherapy = lastMenstrualPeriodBeforeTherapy;
	}

	public Date getDownRegulationStartDate() {
		return downRegulationStartDate;
	}

	public void setDownRegulationStartDate(Date downRegulationStartDate) {
		this.downRegulationStartDate = downRegulationStartDate;
	}

	public String getDownRegulationMethod() {
		return downRegulationMethod;
	}

	public void setDownRegulationMethod(String downRegulationMethod) {
		this.downRegulationMethod = downRegulationMethod;
	}


	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public boolean getIsAgonist() {
		return isAgonist;
	}

	public void setAgonist(boolean isAgonist) {
		this.isAgonist = isAgonist;
	}

	public Date getLastMenstrualPeriodAfterTherapy() {
		return lastMenstrualPeriodAfterTherapy;
	}

	public void setLastMenstrualPeriodAfterTherapy(Date lastMenstrualPeriodAfterTherapy) {
		this.lastMenstrualPeriodAfterTherapy = lastMenstrualPeriodAfterTherapy;
	}

	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.down_regulation");
	}
	
	@Override
	public void receiveEggDonation(IVFProgram.IVFType type) {
	}

	
	@Override
	public void transferFrozenEmbryos(IVFProgram.IVFType type) {
	}

	@Override
	public void abandonProgram(IVFType type) {
	}
	
	public HashMap<String, DownregulationAssessment> getDownregulationAssessments() {
		return downregulationAssessments;
	}

	public void setDownregulationAssessments(HashMap<String, DownregulationAssessment> downregulationAssessments) {
		this.downregulationAssessments = downregulationAssessments;
	}

	
	
}
