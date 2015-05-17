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
public class SpermCollection extends IVFState implements Serializable {
	
	public SpermCollection() {
		super(SPERM_COLLECTION);
	}

	private static final long serialVersionUID = 1L;

	private String spermOrigin;
	private float spermVolume;
	private int spermCount;
	private int spermMotility;
	
	private int gradeAPercentage;
	private int spermMorphology;
	
	private String spermQualityText;
	
	
	public float getSpermVolume() {
		return spermVolume;
	}

	public void setSpermVolume(float spermVolume) {
		this.spermVolume = spermVolume;
	}

	public int getSpermCount() {
		return spermCount;
	}

	public void setSpermCount(int spermCount) {
		this.spermCount = spermCount;
	}

	public int getSpermMotility() {
		return spermMotility;
	}

	public void setSpermMotility(int spermMotility) {
		this.spermMotility = spermMotility;
	}

	public int getGradeAPercentage() {
		return gradeAPercentage;
	}

	public void setGradeAPercentage(int gradeAPercentage) {
		this.gradeAPercentage = gradeAPercentage;
	}

	public int getSpermMorphology() {
		return spermMorphology;
	}

	public void setSpermMorphology(int spermMorphology) {
		this.spermMorphology = spermMorphology;
	}

	public String getSpermQualityText() {
		return spermQualityText;
	}

	public void setSpermQualityText(String spermQualityText) {
		this.spermQualityText = spermQualityText;
	}
	
	public String getSpermOrigin() {
		return spermOrigin;
	}

	public void setSpermOrigin(String spermOrigin) {
		this.spermOrigin = spermOrigin;
	}


	public String getName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.sperm_collection");
	}


	@Override
	public void fertilize(IVFProgram.IVFType type) {
	}

	
	@Override
	public void abandonProgram(IVFType type) {
	}
	
}
