package org.openmrs.module.obgyn.states;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.api.context.Context;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ClinicalPregnancy extends IVFState implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2712992344251371795L;

	public ClinicalPregnancy() {
		super(IVFState.CLINICAL_PREGNANCY);
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.clinical_pregnancy");
	}

	public boolean isFinal() {
		return true;
	}

}