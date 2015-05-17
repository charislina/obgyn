package org.openmrs.module.obgyn.states;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.IVFProgram;
import org.openmrs.module.obgyn.IVFProgram.IVFType;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class IVFState implements State, Comparable<IVFState> {

	
	public static final int INFERTILITY_ASSESSMENT = 1;
	public static final int STIMULATION= 2;
	public static final int DOWN_REGULATION = 3;
	public static final int EGG_COLLECTION= 4;
	public static final int EGG_DONATION= 5;
	
	public static final int SPERM_COLLECTION= 6;
	public static final int FERTILIZATION= 7;
	
	public static final int EMBRYO_TRANSFER= 8;
	public static final int DEFROZE_EMBRYO= 9;
	
	public static final int FROZEN_EMBRYO_TRANSFER= 10;
	public static final int BIOCHEMICAL_MONITORING= 11;
	public static final int CLINICAL_MONITORING= 17;
	
	public static final int NO_PREGNANCY= 13;
	public static final int BIOCHEMICAL_MISCARRIAGE= 14;
	public static final int CLINICAL_MISCARRIAGE= 15;
	public static final int CLINICAL_PREGNANCY= 16;
	public static final int FERTILIZATION_AFTER_DONATION= 18;
	public static final int ABANDON= 19;
	public static final int FREEZE_ALL_EMBRYOS = 20;
	public static final int UNKNOWN= -1;
	
	public static final String INFERTILITY_ASESSMENT_CONCEPT = "IVF Infertility Assessment";
	public static final String STIMULATION_CONCEPT = "IVF Stimulation";
	public static final String DOWN_REGULATION_CONCEPT = "IVF Down-regulation";
	public static final String EGG_COLLECTION_CONCEPT = "IVF Egg Collection";
	public static final String EGG_DONATION_CONCEPT = "IVF Egg Donation";	
	public static final String SPERM_COLLECTION_CONCEPT = "IVF Sperm Collection";
	public static final String FERTILIZATION_CONCEPT = "IVF Fertilization";
	public static final String EMBRYO_TRANSFER_CONCEPT = "IVF Embryotransfer";
	public static final String FROZEN_EMBRYO_TRANSFER_CONCEPT = "IVF Frozen Embryo Transfer";
	public static final String BIOCHEMICAL_MONITORING_CONCEPT = "IVF Biochemical Monitoring";
	public static final String CLINICAL_MONITORING_CONCEPT = "IVF Clinical Monitoring";
	public static final String CLINICAL_PREGNANCY_CONCEPT = "IVF Clinical Pregnancy";
	public static final String BIOCHEMICAL_PREGNANCY_CONCEPT = "IVF Biochemical Pregnancy";
	public static final String CLINICAL_MISCARRIAGE_CONCEPT = "IVF Clinical Miscarriage";
	public static final String BIOCHEMICAL_MISCARRIAGE_CONCEPT = "IVF Biochemical Miscarriage";
	public static final String DONOR_FERTILIZATION_CONCEPT = "IVF Donor Fertilization";
	public static final String ABANDON_PROGRAM_CONCEPT = "IVF Abandon Program";
	public static final String FREEZE_ALL_EMBRYOS_CONCEPT = "IVF Freeze All Embryos";
	
	public static final String UNKNOWN_NAME= "IVF Unknown";
	
	@JsonIgnore
	private int encounterId;	
	@JsonIgnore
	public ArrayList<IVFState> nextStates;
	
	private int type;
	private Date startDate;
	private Date endDate;
	private int stateId;
	private boolean active;
	private String workflowName;
	private String name;
	private String conceptName;
	
	public IVFState(int type) {
		this.type = type;
		switch(this.type) {
		case INFERTILITY_ASSESSMENT :
			this.conceptName = INFERTILITY_ASESSMENT_CONCEPT;
			break;
		case STIMULATION :
			this.conceptName = STIMULATION_CONCEPT;
			break;
		case DOWN_REGULATION :
			this.conceptName = DOWN_REGULATION_CONCEPT;
			break;
		case EGG_COLLECTION :
			this.conceptName = EGG_COLLECTION_CONCEPT;
			break;
		case EGG_DONATION :
			this.conceptName = EGG_DONATION_CONCEPT;
			break;
		case SPERM_COLLECTION :
			this.conceptName = SPERM_COLLECTION_CONCEPT;
			break;
		case FERTILIZATION :
			this.conceptName = FERTILIZATION_CONCEPT;
			break;
		case EMBRYO_TRANSFER :
			this.conceptName = EMBRYO_TRANSFER_CONCEPT;
			break;
		case FROZEN_EMBRYO_TRANSFER :
			this.conceptName = FROZEN_EMBRYO_TRANSFER_CONCEPT;
			break;
		case BIOCHEMICAL_MONITORING :
			this.conceptName = BIOCHEMICAL_MONITORING_CONCEPT;
			break;
		case CLINICAL_MONITORING :
			this.conceptName = CLINICAL_MONITORING_CONCEPT;
			break;
		case BIOCHEMICAL_MISCARRIAGE :
			this.conceptName = BIOCHEMICAL_MISCARRIAGE_CONCEPT;
			break;
		case CLINICAL_MISCARRIAGE:
			this.conceptName = CLINICAL_MISCARRIAGE_CONCEPT;
			break;
		case CLINICAL_PREGNANCY :
			this.conceptName = CLINICAL_PREGNANCY_CONCEPT;
			break;
		case FERTILIZATION_AFTER_DONATION :
			this.conceptName = DONOR_FERTILIZATION_CONCEPT;
			break;
		case ABANDON :
			this.conceptName = ABANDON_PROGRAM_CONCEPT;
			break;
			
		case FREEZE_ALL_EMBRYOS :
			this.conceptName = FREEZE_ALL_EMBRYOS_CONCEPT;
			break;
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		
	}
	
	public String getName() {
		
		return null;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String n) {
		this.conceptName = n;
	}
	
	public int getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(int encounterId) {
		this.encounterId = encounterId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public int compareTo(IVFState otherState) {
		if (otherState == null)
			return 1;
		if (this.getStateId() < otherState.getStateId())
			return -1;
		if (this.getStateId() > otherState.getStateId())
			return 1;
		return 0;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isInitial() {
		return (type == INFERTILITY_ASSESSMENT);
	}
	
	public boolean isFinal() {
		return false;
	}

	@Override
	public void stimulate(IVFType type) {
		throw new IllegalArgumentException("Transition not allowed");
	}

	@Override
	public void downregulate(IVFType type) {
		throw new IllegalArgumentException("Transition not allowed");
	}

	
	@Override
	public void receiveEggDonation(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}

	@Override
	public void collectEggs(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}

	@Override
	public void collectSperm(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}

	@Override
	public void receiveDonatedEggs(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}

	@Override
	public void fertilize(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}
	
	@Override
	public void fertilizeWithDonor(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}

	@Override
	public void transferEmbryos(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}

	@Override
	public void transferFrozenEmbryos(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}

	@Override
	public void monitorBiochemically(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}

	@Override
	public void monitorClinically(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}

	@Override
	public void defrozeEmbryos(IVFProgram.IVFType type) {
		throw new IllegalArgumentException("Transition is not supported");
	}	
	@Override
	public void assertBiochemicalPregnancy(IVFType type) {
		throw new IllegalArgumentException("Transition not allowed");
	}

	@Override
	public void assertClinicalPregnancy(IVFType type) {
		throw new IllegalArgumentException("Transition not allowed");
	}

	@Override
	public void assertNoPregnancy(IVFType type) {
		throw new IllegalArgumentException("Transition not allowed");
	}

	@Override
	public void assertBiochemicalMiscarriage(IVFType type) {
		throw new IllegalArgumentException("Transition not allowed");
	}

	@Override
	public void assertClinicalMiscarriage(IVFType type) {
		throw new IllegalArgumentException("Transition not allowed");
	}

	@Override
	public void abandonProgram(IVFType type) {
		throw new IllegalArgumentException("Transition not allowed");
	}
	
	@Override
	public void freezeAllEmbryos(IVFType type) {
		throw new IllegalArgumentException("Transition not allowed");
	}
	
	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	
	public ArrayList<IVFState> getNextStates() {
		return nextStates;
	}

	public void setNextStates(ArrayList<IVFState> nextStates) {
		this.nextStates = nextStates;
	}
	
	@JsonIgnore
	public HashMap<Integer, String> getNextStatesMap() {
		HashMap<Integer,String> map = new HashMap<Integer,String>();
		if (nextStates == null || nextStates.isEmpty())
			return map;
		
		for (Iterator<IVFState> it = nextStates.iterator();it.hasNext();) {
			IVFState s = it.next();
			map.put(s.getStateId(), s.getName());
		}
		return map;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
