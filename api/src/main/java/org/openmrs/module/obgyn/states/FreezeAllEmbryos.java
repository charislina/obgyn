package org.openmrs.module.obgyn.states;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.IVFProgram.IVFType;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FreezeAllEmbryos extends IVFState {

	public FreezeAllEmbryos() {
		super(IVFState.FREEZE_ALL_EMBRYOS);		
	}
	
	public boolean isFinal() {
		return true;
	}

	@Override
	public void freezeAllEmbryos(IVFType type) {
	}
	
	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.freeze_all_embryos");
	}

}
