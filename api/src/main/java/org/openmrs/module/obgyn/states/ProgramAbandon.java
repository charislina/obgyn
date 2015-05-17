package org.openmrs.module.obgyn.states;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.IVFProgram;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ProgramAbandon extends IVFState implements Serializable {

	private static final long serialVersionUID = 1L;

	public ProgramAbandon() {
		super(IVFState.ABANDON);		
	}

	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.program_abandon");
	}
	
	public boolean isFinal() {
		return true;
	}

}
