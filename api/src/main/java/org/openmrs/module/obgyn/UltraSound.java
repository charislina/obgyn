package org.openmrs.module.obgyn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UltraSound extends BaseOpenmrsObject implements Serializable, Comparable<UltraSound> {

	private Date date;
	private String note;
	
	private int numOfSacs;
	private ArrayList<Integer> heartRatesPerSac = new ArrayList<Integer>();
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(UltraSound arg0) {
		if (arg0 == null || arg0.getDate() == null)
			return 1;
		if (date == null)
			return -1;
		
		return date.compareTo(arg0.getDate());
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumOfSacs() {
		return numOfSacs;
	}

	public void setNumOfSacs(int numOfSacs) {
		this.numOfSacs = numOfSacs;
	}

	public void addHeartRatePerSac(int hr) {
		heartRatesPerSac.add(new Integer(hr));
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ArrayList<Integer> getHeartRatesPerSac() {
		return heartRatesPerSac;
	}

	public void setHeartRatesPerSac(ArrayList<Integer> heartRatesPerSac) {
		this.heartRatesPerSac = heartRatesPerSac;
	}

}
