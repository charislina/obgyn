package org.openmrs.module.obgyn;

import java.util.ArrayList;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.module.obgyn.IVFProgram.IVFType;
import org.openmrs.module.obgyn.states.BiochemicalMiscarriage;
import org.openmrs.module.obgyn.states.ClinicalMiscarriage;
import org.openmrs.module.obgyn.states.ClinicalMonitoring;
import org.openmrs.module.obgyn.states.ClinicalPregnancy;
import org.openmrs.module.obgyn.states.DownRegulation;
import org.openmrs.module.obgyn.states.EggCollection;
import org.openmrs.module.obgyn.states.EggDonation;
import org.openmrs.module.obgyn.states.EmbryoTransfer;
import org.openmrs.module.obgyn.states.Fertilization;
import org.openmrs.module.obgyn.states.FreezeAllEmbryos;
import org.openmrs.module.obgyn.states.FrozenEmbryoTransfer;
import org.openmrs.module.obgyn.states.IVFState;
import org.openmrs.module.obgyn.states.BiochemicalMonitoring;
import org.openmrs.module.obgyn.states.ProgramAbandon;
import org.openmrs.module.obgyn.states.SpermCollection;
import org.openmrs.module.obgyn.states.Stimulation;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class IVFWorkflow {

	private IVFProgram.IVFType type;
	
	private Integer workflowId;
	private String workflowName;
	
	private IVFState currentState;
	
	private ArrayList<IVFState> statesList = new ArrayList<IVFState>();

	public ArrayList<IVFState> getStatesList() {
		return statesList;
	}

	public void setStatesList(ArrayList<IVFState> statesList) {
		this.statesList = statesList;
	}

	public Integer getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Integer workflowId) {
		this.workflowId = workflowId;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String worklowName) {
		this.workflowName = worklowName;
		this.type = getTypeFromName(this.workflowName);
	}

	public IVFState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(IVFState currentState) {
		this.currentState = currentState;
	}
	
	
	public boolean isTransitionToStateAllowed(int stateType) {
		
		if (this.currentState == null || this.currentState.isFinal())
			return false;
		
		boolean transitionAllowed = false;
		
		try {
		
		switch (stateType) {
		
			case IVFState.STIMULATION:
					currentState.stimulate(this.type);
					break;

			case IVFState.DOWN_REGULATION:
				currentState.downregulate(this.type);
				break;

			case IVFState.EGG_COLLECTION:
				currentState.collectEggs(this.type);
				break;

			case IVFState.EGG_DONATION:
				currentState.receiveEggDonation(this.type);
				break;

			case IVFState.SPERM_COLLECTION:
				currentState.collectSperm(this.type);
				break;

			case IVFState.FERTILIZATION:
				currentState.fertilize(this.type);
				break;

			case IVFState.FERTILIZATION_AFTER_DONATION:
				currentState.fertilizeWithDonor(this.type);
				break;
				
			case IVFState.EMBRYO_TRANSFER:
				currentState.transferEmbryos(this.type);
				break;

			case IVFState.FROZEN_EMBRYO_TRANSFER:
				currentState.transferFrozenEmbryos(this.type);
				break;
				
			case IVFState.BIOCHEMICAL_MONITORING:
				currentState.monitorBiochemically(this.type);
				break;

			case IVFState.CLINICAL_MONITORING:
				currentState.monitorClinically(this.type);
				break;

			case IVFState.NO_PREGNANCY:
				currentState.assertNoPregnancy(this.type);
				break;

			case IVFState.BIOCHEMICAL_MISCARRIAGE:
				currentState.assertBiochemicalMiscarriage(this.type);
				break;

			case IVFState.CLINICAL_PREGNANCY:
				currentState.assertClinicalPregnancy(this.type);
				break;

			case IVFState.CLINICAL_MISCARRIAGE:
				currentState.assertClinicalMiscarriage(this.type);
				break;
			
			case IVFState.ABANDON:
				currentState.abandonProgram(this.type);
				break;

		}
		
		transitionAllowed = true;
		
		} catch (IllegalArgumentException e) {
			transitionAllowed = false;
		}
		
		return transitionAllowed ;
	}
	
	
	public static IVFProgram.IVFType getTypeFromName(String name) {
		
		if (name == null)
			return IVFProgram.IVFType.UNKNOWN;
		
		if (name.equalsIgnoreCase("Donor Egg IVF"))
			return IVFProgram.IVFType.DONOR_EGG_IVF;

		if (name.equalsIgnoreCase("Frozen Embryo IVF"))
			return IVFProgram.IVFType.FROZEN_EMBRYO_TRANSFER_IVF;

		if (name.equalsIgnoreCase("Own-egg IVF"))
			return IVFProgram.IVFType.OWN_EGG_IVF;
		
		return IVFProgram.IVFType.UNKNOWN;
	}
	
		

	public IVFProgram.IVFType getType() {
		return type;
	}

	public void setType(IVFProgram.IVFType type) {
		this.type = type;
	}
	
	
	public ArrayList<IVFState> getNextStates() {
		
		if (currentState.isFinal())
		//	return null;
		return new ArrayList<IVFState>();
		
		int currentStateType = currentState.getType();
		
		ArrayList<IVFState> statesList = new ArrayList<IVFState>();
		switch(currentStateType) {
			case IVFState.INFERTILITY_ASSESSMENT:
				if (this.getType() == IVFType.OWN_EGG_IVF)
					statesList.add(new Stimulation());
				else 
					statesList.add(new DownRegulation());
				statesList.add(new ProgramAbandon());
				break;
			
			case IVFState.DOWN_REGULATION:
				if (this.getType() == IVFType.DONOR_EGG_IVF)
					statesList.add(new EggDonation());
				else if (this.getType() == IVFType.FROZEN_EMBRYO_TRANSFER_IVF)
					statesList.add(new FrozenEmbryoTransfer());
				statesList.add(new ProgramAbandon());

				break;
				
			case IVFState.STIMULATION:
				statesList.add(new EggCollection());
				statesList.add(new ProgramAbandon());

				break;

			case IVFState.EGG_COLLECTION:
			case IVFState.EGG_DONATION:
				statesList.add(new SpermCollection());
				statesList.add(new ProgramAbandon());

				break;

				
			case IVFState.SPERM_COLLECTION:
				statesList.add(new Fertilization());
				statesList.add(new ProgramAbandon());

				break;

			case IVFState.FERTILIZATION:
				statesList.add(new EmbryoTransfer());
				statesList.add(new ProgramAbandon());
				break;

			case IVFState.EMBRYO_TRANSFER:
				statesList.add(new BiochemicalMonitoring());
				statesList.add(new ProgramAbandon());
				statesList.add(new FreezeAllEmbryos());				
				break;
				
			case IVFState.FROZEN_EMBRYO_TRANSFER:
				statesList.add(new BiochemicalMonitoring());
				statesList.add(new ProgramAbandon());
				break;
				
			case IVFState.BIOCHEMICAL_MONITORING:
				statesList.add(new BiochemicalMiscarriage());
				statesList.add(new ClinicalMonitoring());
				
				break;

			case IVFState.CLINICAL_MONITORING:
				statesList.add(new ClinicalMiscarriage());
				statesList.add(new ClinicalPregnancy());

				break;
		}
		return statesList;
		
	}
}
