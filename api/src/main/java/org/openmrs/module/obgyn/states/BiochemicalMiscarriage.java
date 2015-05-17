package org.openmrs.module.obgyn.states;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.api.context.Context;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BiochemicalMiscarriage extends IVFState implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2712992344251371795L;

	public BiochemicalMiscarriage() {
		super(IVFState.BIOCHEMICAL_MISCARRIAGE);
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.biochemical_miscarriage");
	}
	
	public boolean isFinal() {
		return true;
	}
	
}