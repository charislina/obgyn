package org.openmrs.module.obgyn;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openmrs.BaseOpenmrsObject;

public class BlastomereQuality  implements Serializable {

	@JsonIgnore
	private int encounterId;
	private int cellCount;
	private float cellSizeDegree;
	private int qualityIndex;
	
	public int getCellCount() {
		return cellCount;
	}

	public void setCellCount(int cellCount) {
		this.cellCount = cellCount;
	}

	public float getCellSizeDegree() {
		return cellSizeDegree;
	}

	public void setCellSizeDegree(float cellSizeDegree) {
		this.cellSizeDegree = cellSizeDegree;
	}

	private static final long serialVersionUID = 1L;

	public int getQualityIndex() {
		return qualityIndex;
	}

	public void setQualityIndex(int qualityIndex) {
		this.qualityIndex = qualityIndex;
	}

	public int getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(int encounterId) {
		this.encounterId = encounterId;
	}

}
