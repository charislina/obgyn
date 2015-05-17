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
public class Fertilization extends IVFState implements Serializable {
	
	public Fertilization() {
		super(FERTILIZATION);
	}

	private static final long serialVersionUID = 1L;
	
	private int donatedEggs;
	private int collectedEggs;
	private int eggsForICSI;
	private int eggsForIMSI;
	private int fertilizedEggs;

	private String fertilizationMethod;
	private boolean usedAssistedHatching;
	private boolean usedTimeLapse;
	private boolean usedBlastocyst;
	
	public boolean getUsedAssistedHatching() {
		return usedAssistedHatching;
	}

	public void setUsedAssistedHatching(boolean usedAssistedHatching) {
		this.usedAssistedHatching = usedAssistedHatching;
	}

	public boolean getUsedTimeLapse() {
		return usedTimeLapse;
	}

	public void setUsedTimeLapse(boolean usedTimeLapse) {
		this.usedTimeLapse = usedTimeLapse;
	}

	public boolean getUsedBlastocyst() {
		return usedBlastocyst;
	}

	public void setUsedBlastocyst(boolean usedBlastocyst) {
		this.usedBlastocyst = usedBlastocyst;
	}

	private boolean pgdPgs;
	private int embryosSuitableForBiopsy;
	private String screeningReason;
	private int healthyEmbryosAfterScreening;
	private int embryosAfterScreeningQualityIndex;
	private String medicalFacility;
	
	
	public boolean isPgdPgs() {
		return pgdPgs;
	}

	public void setPgdPgs(boolean pgdPgs) {
		this.pgdPgs = pgdPgs;
	}

	public int getEmbryosSuitableForBiopsy() {
		return embryosSuitableForBiopsy;
	}

	public void setEmbryosSuitableForBiopsy(int embryosSuitableForBiopsy) {
		this.embryosSuitableForBiopsy = embryosSuitableForBiopsy;
	}

	public String getScreeningReason() {
		return screeningReason;
	}

	public void setScreeningReason(String screeningReason) {
		this.screeningReason = screeningReason;
	}

	public int getHealthyEmbryosAfterScreening() {
		return healthyEmbryosAfterScreening;
	}

	public void setHealthyEmbryosAfterScreening(int healthyEmbryosAfterScreening) {
		this.healthyEmbryosAfterScreening = healthyEmbryosAfterScreening;
	}

	public int getEmbryosAfterScreeningQualityIndex() {
		return embryosAfterScreeningQualityIndex;
	}

	public void setEmbryosAfterScreeningQualityIndex(int embryosAfterScreeningQualityIndex) {
		this.embryosAfterScreeningQualityIndex = embryosAfterScreeningQualityIndex;
	}
	
	public int getCollectedEggs() {
		return collectedEggs;
	}

	public void setCollectedEggs(int collectedEggs) {
		this.collectedEggs = collectedEggs;
	}

	public int getEggsForICSI() {
		return eggsForICSI;
	}

	public void setEggsForICSI(int eggsForICSI) {
		this.eggsForICSI = eggsForICSI;
	}

	public int getEggsForIMSI() {
		return eggsForIMSI;
	}

	public void setEggsForIMSI(int eggsForIMSI) {
		this.eggsForIMSI = eggsForIMSI;
	}

	public int getFertilizedEggs() {
		return fertilizedEggs;
	}

	public void setFertilizedEggs(int fertilizedEggs) {
		this.fertilizedEggs = fertilizedEggs;
	}

	public String getFertilizationMethod() {
		return fertilizationMethod;
	}

	public void setFertilizationMethod(String fertilizationMethod) {
		this.fertilizationMethod = fertilizationMethod;
	}
	

	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.fertilization");
	}


	@Override
	public void transferEmbryos(IVFProgram.IVFType type) {
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

	public int getDonatedEggs() {
		return donatedEggs;
	}

	public void setDonatedEggs(int donatedEggs) {
		this.donatedEggs = donatedEggs;
	}

	
}
