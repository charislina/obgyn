package org.openmrs.module.obgyn.states;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.BlastomereQuality;
import org.openmrs.module.obgyn.IVFProgram;
import org.openmrs.module.obgyn.IVFProgram.IVFType;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class EmbryoTransfer extends IVFState implements Serializable {
	
	public EmbryoTransfer(boolean isFrozen) {
		super(IVFState.EMBRYO_TRANSFER);
		this.setEmbryoFreezing(isFrozen);
	}

	public EmbryoTransfer() {
		this(false);
	}
	
	private static final long serialVersionUID = 1L;

	private boolean isEmbryoFreezing;

	private Date embryoTransferDate;
		
	private int embryoTransferDay;
	private int blastocyteDay;
	
	private int transferredEmbryos;
	private int transferredEmbryosQualityIndex;
	
	private int frozenEmbryos;
	private int embryoFreezingDay;
	
	private String cervicalOrientation;
	private String catheter;
	private String catheterOtherText;
	
	private boolean usedTweezers;
	public String getCatheterOtherText() {
		return catheterOtherText;
	}

	public void setCatheterOtherText(String catheterOtherText) {
		this.catheterOtherText = catheterOtherText;
	}

	private boolean usedUltrasound;
	
	private boolean usedAnesthesia;
	private String medicalFacility;
	
	private HashMap<Integer, BlastomereQuality> embryoList = new HashMap<Integer, BlastomereQuality>();
	
	private String embryoTransferDifficulty;
	public HashMap<Integer, BlastomereQuality> getEmbryoList() {
		return embryoList;
	}

	public void setEmbryoList(HashMap<Integer, BlastomereQuality> embryoList) {
		this.embryoList = embryoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String embryoTransferNotes;
	
	public Date getEmbryoTransferDate() {
		return embryoTransferDate;
	}

	public void setEmbryoTransferDate(Date embryoTransferDate) {
		this.embryoTransferDate = embryoTransferDate;
	}

	public int getEmbryoTransferDay() {
		return embryoTransferDay;
	}

	public void setEmbryoTransferDay(int embryoTransferDay) {
		this.embryoTransferDay = embryoTransferDay;
	}

	public int getBlastocyteDay() {
		return blastocyteDay;
	}

	public void setBlastocyteDay(int blastocyteDay) {
		this.blastocyteDay = blastocyteDay;
	}

	public int getEmbryoFreezingDay() {
		return embryoFreezingDay;
	}

	public void setEmbryoFreezingDay(int eggFreezingDay) {
		this.embryoFreezingDay = eggFreezingDay;
	}

	public int getTransferredEmbryos() {
		return transferredEmbryos;
	}

	public void setTransferredEmbryos(int transferredEmbryos) {
		this.transferredEmbryos = transferredEmbryos;
	}

	public int getFrozenEmbryos() {
		return frozenEmbryos;
	}

	public void setFrozenEmbryos(int frozenEmbryos) {
		this.frozenEmbryos = frozenEmbryos;
	}

	public int getTransferredEmbryosQualityIndex() {
		return transferredEmbryosQualityIndex;
	}

	public void setTransferredEmbryosQualityIndex(int transferredEmbryosQualityIndex) {
		this.transferredEmbryosQualityIndex = transferredEmbryosQualityIndex;
	}

	public String getCervicalOrientation() {
		return cervicalOrientation;
	}

	public void setCervicalOrientation(String cervicalOrientation) {
		this.cervicalOrientation = cervicalOrientation;
	}

	public String getCatheter() {
		return catheter;
	}

	public void setCatheter(String catheter) {
		this.catheter = catheter;
	}

	public boolean isUsedTweezers() {
		return usedTweezers;
	}

	public void setUsedTweezers(boolean usedTweezers) {
		this.usedTweezers = usedTweezers;
	}

	public boolean isUsedUltrasound() {
		return usedUltrasound;
	}

	public void setUsedUltrasound(boolean usedUltrasound) {
		this.usedUltrasound = usedUltrasound;
	}

	public boolean isUsedAnesthesia() {
		return usedAnesthesia;
	}

	public void setUsedAnesthesia(boolean usedAnesthesia) {
		this.usedAnesthesia = usedAnesthesia;
	}

	public String getEmbryoTransferDifficulty() {
		return embryoTransferDifficulty;
	}

	public void setEmbryoTransferDifficulty(String embryoTransferDifficulty) {
		this.embryoTransferDifficulty = embryoTransferDifficulty;
	}


	public boolean isEmbryoFreezing() {
		return isEmbryoFreezing;
	}

	public void setEmbryoFreezing(boolean isEmbryoFreezing) {
		this.isEmbryoFreezing = isEmbryoFreezing;
	}

	public String getEmbryoTransferNotes() {
		return embryoTransferNotes;
	}

	public void setEmbryoTransferNotes(String embryoTransferNotes) {
		this.embryoTransferNotes = embryoTransferNotes;
	}


	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.embryo_transfer");
	}
	
	@Override
	public void monitorBiochemically(IVFProgram.IVFType type) {
	}

	@Override
	public void abandonProgram(IVFType type) {
	}
	
	@Override
	public void freezeAllEmbryos(IVFType type) {
	}

	public String getMedicalFacility() {
		return medicalFacility;
	}

	public void setMedicalFacility(String medicalFacility) {
		this.medicalFacility = medicalFacility;
	}

}
