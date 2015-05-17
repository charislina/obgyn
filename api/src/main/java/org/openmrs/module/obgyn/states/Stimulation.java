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
public class Stimulation extends IVFState implements Serializable {
	
	public Stimulation() {
		super(STIMULATION);
	}

	private static final long serialVersionUID = 1L;
	private Date lastMenstrualPeriodBeforeTherapy;	
	private Date stimulationTherapyStartDate;

	private String stimulationProtocol;
	private String stimulationDrug;
	
	private HashMap<String, DownregulationAssessment> downregulationAssessments;
	private HashMap<String, StimulationAssessment> stimulationAssessments;
	private Date downregulationStartDate;
	private Date antagonistStartDate;
	
	private String downregulationMethod;
	private Date lastMenstrualPeriodDateAfterDRStart;
	
	private String downRegulationDrug;
	
	public Date getDownregulationStartDate() {
		return downregulationStartDate;
	}

	public void setDownregulationStartDate(Date downregulationStartDate) {
		this.downregulationStartDate = downregulationStartDate;
	}

	public String getDownregulationMethod() {
		return downregulationMethod;
	}

	public void setDownregulationMethod(String downregulationMethod) {
		this.downregulationMethod = downregulationMethod;
	}

	public Date getLastMenstrualPeriodDateAfterDRStart() {
		return lastMenstrualPeriodDateAfterDRStart;
	}

	public void setLastMenstrualPeriodDateAfterDRStart(Date lastMenstrualPeriodDateAfterDRStart) {
		this.lastMenstrualPeriodDateAfterDRStart = lastMenstrualPeriodDateAfterDRStart;
	}

	public String getDownRegulationDrug() {
		return downRegulationDrug;
	}

	public void setDownRegulationdrug(String downRegulationDrug) {
		this.downRegulationDrug = downRegulationDrug;
	}

	private boolean isAgonist;
	
	
	public HashMap<String, DownregulationAssessment> getDownregulationAssessments() {
		return downregulationAssessments;
	}

	public void setDownregulationAssessments(HashMap<String, DownregulationAssessment> downregulationAssessments) {
		this.downregulationAssessments = downregulationAssessments;
	}

	public HashMap<String, StimulationAssessment> getStimulationAssessments() {
		return stimulationAssessments;
	}

	public void setStimulationAssessments(HashMap<String, StimulationAssessment> stimulationAssessments) {
		this.stimulationAssessments = stimulationAssessments;
	}

	public String getStimulationDrug() {
		return stimulationDrug;
	}

	public void setStimulationDrug(String stimulationDrug) {
		this.stimulationDrug = stimulationDrug;
	}

	private String stimulationTherapyNotes;
	
	

	public Date getStimulationTherapyStartDate() {
		return stimulationTherapyStartDate;
	}

	public void setStimulationTherapyStartDate(Date stimulationTherapyStartDate) {
		this.stimulationTherapyStartDate = stimulationTherapyStartDate;
	}



	public String getStimulationProtocol() {
		return stimulationProtocol;
	}

	public void setStimulationProtocol(String stimulationProtocol) {
		this.stimulationProtocol = stimulationProtocol;
	}


	public String getStimulationTherapyNotes() {
		return stimulationTherapyNotes;
	}

	public void setStimulationTherapyNotes(String stimulationTherapyNotes) {
		this.stimulationTherapyNotes = stimulationTherapyNotes;
	}

	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.stimulation");
	}

	public boolean getIsAgonist() {
		return isAgonist;
	}

	public void setAgonist(boolean isAgonist) {
		this.isAgonist = isAgonist;
	}

	public Date getLastMenstrualPeriodBeforeTherapy() {
		return lastMenstrualPeriodBeforeTherapy;
	}

	public void setLastMenstrualPeriodBeforeTherapy(Date lastMenstrualPeriodBeforeTherapy) {
		this.lastMenstrualPeriodBeforeTherapy = lastMenstrualPeriodBeforeTherapy;
	}
	
	
	@Override
	public void collectEggs(IVFProgram.IVFType type) {
	}

	public Date getAntagonistStartDate() {
		return antagonistStartDate;
	}

	public void setAntagonistStartDate(Date antagonistStartDate) {
		this.antagonistStartDate = antagonistStartDate;
	}

	
	
}
