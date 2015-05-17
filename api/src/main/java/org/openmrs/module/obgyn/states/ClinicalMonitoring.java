package org.openmrs.module.obgyn.states;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.IVFProgram;
import org.openmrs.module.obgyn.IVFProgram.IVFType;
import org.openmrs.module.obgyn.UltraSound;


//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ClinicalMonitoring extends IVFState implements Serializable {

	public ClinicalMonitoring() {
		super(CLINICAL_MONITORING);
	}

	private static final long serialVersionUID = 1L;
	
	private HashMap<String, UltraSound> ultraSounds = new HashMap<String,UltraSound>();

	private String note;


	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.clinical_monitoring");
	}

	
	@Override
	public void assertClinicalPregnancy(IVFType type) {
	}

	@Override
	public void assertClinicalMiscarriage(IVFType type) {
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public  HashMap<String, UltraSound> getUltraSounds() {
		return ultraSounds;
	}


	public void setUltraSounds( HashMap<String, UltraSound> ultraSounds) {
		this.ultraSounds = ultraSounds;
	}

}
