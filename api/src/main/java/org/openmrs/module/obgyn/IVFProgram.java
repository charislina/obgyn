package org.openmrs.module.obgyn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.module.obgyn.states.IVFState;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class IVFProgram extends BaseOpenmrsObject implements Serializable, Comparable<IVFProgram> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum IVFType {
		FROZEN_EMBRYO_TRANSFER_IVF,
		DONOR_EGG_IVF,
		OWN_EGG_IVF,
		UNKNOWN
	}
		
	private String name;
	private ArrayList<IVFState> states= new ArrayList<IVFState>();
	private IVFType type;
	private boolean completed;
	private Date enrollementDate;
	private Date completionDate;
	private String workflowName;
	private int programId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean getCompleted() {
		return completed;
	}

	public void setCompleted(boolean isCompleted) {
		this.completed = isCompleted;
	}

	public Date getEnrollementDate() {
		return enrollementDate;
	}

	public void setEnrollementDate(Date enrollementDate) {
		this.enrollementDate = enrollementDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
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

	public IVFType getType() {
		return type;
	}

	public void setType(IVFType type) {
		this.type = type;
	}

	public ArrayList<IVFState> getStates() {
		return states;
	}

	public void setStates(ArrayList<IVFState> states) {
		this.states = states;
	}

	
	
	public void addState(IVFState state) {
		this.getStates().add(state);
	}
	

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}
	
	public IVFState getFinalState() {
		int num =  states.size();

		if (this.completionDate != null && num > 0) 			
			return states.get(num-1);
		
		return null;
	}

	@Override
	public int compareTo(IVFProgram otherProgram) {
		if (otherProgram.getCompletionDate() == null)
			return 1;
		
		if (this.getCompletionDate() == null)
			return -1;
		
		return -this.getCompletionDate().compareTo(otherProgram.getCompletionDate());
	}

}
