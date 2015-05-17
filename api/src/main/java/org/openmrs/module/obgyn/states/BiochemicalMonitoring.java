package org.openmrs.module.obgyn.states;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.IVFProgram;
import org.openmrs.module.obgyn.IVFProgram.IVFType;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BiochemicalMonitoring extends IVFState implements Serializable {

	public BiochemicalMonitoring() {
		super(BIOCHEMICAL_MONITORING);
	}

	private static final long serialVersionUID = 1L;
	private int bHCG1;	
	private int bHCG2;
	private int bHCG3;
	
	private Date bHCG1Date;
	private Date bHCG2Date;
	private Date bHCG3Date;
	
	private String bHCG1Text;
	private String bHCG2Text;
	private String bHCG3Text;
	
	public int getbHCG3() {
		return bHCG3;
	}

	public void setbHCG3(int bHCG3) {
		this.bHCG3 = bHCG3;
	}

	public Date getbHCG3Date() {
		return bHCG3Date;
	}

	public void setbHCG3Date(Date bHCG3Date) {
		this.bHCG3Date = bHCG3Date;
	}

	public String getbHCG3Text() {
		return bHCG3Text;
	}

	public void setbHCG3Text(String bHCG3Text) {
		this.bHCG3Text = bHCG3Text;
	}

	private String note;

	public int getbHCG1() {
		return bHCG1;
	}

	public void setbHCG1(int bHCG1) {
		this.bHCG1 = bHCG1;
	}

	public int getbHCG2() {
		return bHCG2;
	}

	public void setbHCG2(int bHCG2) {
		this.bHCG2 = bHCG2;
	}


	public Date getbHCG1Date() {
		return bHCG1Date;
	}

	public void setbHCG1Date(Date bHCG1Date) {
		this.bHCG1Date = bHCG1Date;
	}

	public Date getbHCG2Date() {
		return bHCG2Date;
	}

	public void setbHCG2Date(Date bHCG2Date) {
		this.bHCG2Date = bHCG2Date;
	}

	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.biochemical_monitoring");
	}


	@Override
	public void monitorClinically(IVFProgram.IVFType type) {
	}

	@Override
	public void assertBiochemicalPregnancy(IVFType type) {
	}

	@Override
	public void assertBiochemicalMiscarriage(IVFType type) {
	}

	public String getbHCG1Text() {
		return bHCG1Text;
	}

	public void setbHCG1Text(String bHCG1Text) {
		this.bHCG1Text = bHCG1Text;
	}

	public String getbHCG2Text() {
		return bHCG2Text;
	}

	public void setbHCG2Text(String bHCG2Text) {
		this.bHCG2Text = bHCG2Text;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
