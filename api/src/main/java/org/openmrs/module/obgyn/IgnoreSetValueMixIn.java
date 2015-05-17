package org.openmrs.module.obgyn;

import org.codehaus.jackson.annotate.JsonIgnore;

public abstract class IgnoreSetValueMixIn {    
	@JsonIgnore public abstract Boolean isBirthdateEstimated();
	@JsonIgnore public abstract Boolean getBirthdateEstimated();
	@JsonIgnore public abstract Boolean getVoided();
	@JsonIgnore public abstract Boolean isVoided();
	
}