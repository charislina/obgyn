package org.openmrs.module.obgyn;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class VisitNote extends BaseOpenmrsObject implements Serializable, Comparable<VisitNote> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private int encounterId;

	private int visitType;
	private String note;
	private Date timestamp;
	private String location;
	private String provider;

	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public int getVisitType() {
		return visitType;
	}

	public void setVisitType(int visitType) {
		this.visitType = visitType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Override
	public int compareTo(VisitNote o) {		
		
		if (o == null || o.getTimestamp() == null)
			return 1;
		
		if (this.timestamp == null)
			return -1;
		
		return this.timestamp.compareTo(o.getTimestamp());
	}

	
	public String getVisitTypeStr() {
		switch(visitType) {
			case 1: 
				return Context.getMessageSourceService().getMessage("OBGyn7.History.list.gynae_visit");		
			case 2:
				return Context.getMessageSourceService().getMessage("OBGyn7.History.list.pregnancy_visit");		

			case 3:
				return Context.getMessageSourceService().getMessage("OBGyn7.History.list.routine_visit");		

			case 5:
				return Context.getMessageSourceService().getMessage("OBGyn7.History.list.ivf_visit");		

			case 6:
				return Context.getMessageSourceService().getMessage("OBGyn7.History.list.alimentation_visit");		

			case 4:
				return Context.getMessageSourceService().getMessage("OBGyn7.History.list.childbed_visit");		

		}
		
		return "";
	}

	public int getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(int encounterId) {
		this.encounterId = encounterId;
	}
	
}
