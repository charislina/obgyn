package org.openmrs.module.obgyn.states;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.IVFProgram;
import org.openmrs.module.obgyn.IVFProgram.IVFType;
import org.springframework.format.annotation.DateTimeFormat;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class EggCollection extends IVFState implements Serializable {
	
	
	public EggCollection() {
		super(IVFState.EGG_COLLECTION);
	}

	private static final long serialVersionUID = 1L;

	private Date collectionDate;
	private int collectedEggs;
	private int difficultyIndex;
	private boolean hasOHSS;
	private int collectedEggsFromLeftOvary;
	private int collectedEggsFromRightOvary;

	private String collectedEggsFromLeftOvaryText;
	private String collectedEggsFromRightOvaryText;

	private String eggCollectionNote;
	private String futurePlanningNote;
	
	private String embryologist;
	private String midwife;
	private String anesthetist;
	private String medicalFacility;
	
	public String getEmbryologist() {
		return embryologist;
	}

	public void setEmbryologist(String embryologist) {
		this.embryologist = embryologist;
	}

	public String getMidwife() {
		return midwife;
	}

	public void setMidwife(String midwife) {
		this.midwife = midwife;
	}

	public String getAnesthetist() {
		return anesthetist;
	}

	public void setAnesthetist(String anesthetist) {
		this.anesthetist = anesthetist;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public int getCollectedEggs() {
		return collectedEggs;
	}

	public void setCollectedEggs(int collectedEggs) {
		this.collectedEggs = collectedEggs;
	}

	public int getDifficultyIndex() {
		return difficultyIndex;
	}

	public void setDifficultyIndex(int difficultyIndex) {
		this.difficultyIndex = difficultyIndex;
	}

	public boolean isHasOHSS() {
		return hasOHSS;
	}

	public void setHasOHSS(boolean hasOHSS) {
		this.hasOHSS = hasOHSS;
	}

	public int getCollectedEggsFromLeftOvary() {
		return collectedEggsFromLeftOvary;
	}

	public void setCollectedEggsFromLeftOvary(int collectedEggsFromLeftOvary) {
		this.collectedEggsFromLeftOvary = collectedEggsFromLeftOvary;
	}

	public int getCollectedEggsFromRightOvary() {
		return collectedEggsFromRightOvary;
	}

	public void setCollectedEggsFromRightOvary(int collectedEggsFromRightOvary) {
		this.collectedEggsFromRightOvary = collectedEggsFromRightOvary;
	}

	public String getCollectedEggsFromLeftOvaryText() {
		return collectedEggsFromLeftOvaryText;
	}

	public void setCollectedEggsFromLeftOvaryText(String collectedEggsFromLeftOvaryText) {
		this.collectedEggsFromLeftOvaryText = collectedEggsFromLeftOvaryText;
	}

	public String getCollectedEggsFromRightOvaryText() {
		return collectedEggsFromRightOvaryText;
	}

	public void setCollectedEggsFromRightOvaryText(String collectedEggsFromRightOvaryText) {
		this.collectedEggsFromRightOvaryText = collectedEggsFromRightOvaryText;
	}

	public String getEggCollectionNote() {
		return eggCollectionNote;
	}

	public void setEggCollectionNote(String eggCollectionNote) {
		this.eggCollectionNote = eggCollectionNote;
	}

	public String getFuturePlanningNote() {
		return futurePlanningNote;
	}

	public void setFuturePlanningNote(String futurePlanningNote) {
		this.futurePlanningNote = futurePlanningNote;
	}

	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.egg_collection");
	}

	

	@Override
	public void collectSperm(IVFProgram.IVFType type) {
	}

	@Override
	public void abandonProgram(IVFType type) {
	}
	
	public String getMedicalFacility() {
		return medicalFacility;
	}

	public void setMedicalFacility(String medicalFacility) {
		this.medicalFacility = medicalFacility;
	}

	
	
}