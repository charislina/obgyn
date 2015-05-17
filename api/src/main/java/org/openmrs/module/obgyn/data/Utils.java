package org.openmrs.module.obgyn.data;

import org.openmrs.Concept;

public class Utils {

	private Utils() {}
	
	public static boolean isConcept(Concept concept, Integer targetConceptId) {
		if (concept == null)
			return false;

		if ( targetConceptId == null)
			return false;

		return concept.getConceptId().equals(targetConceptId) ;

	}
	
	public static int getDayFromDayStr(String dayStr) {
		if (dayStr == null)
			return 0;

		if ("second day".equalsIgnoreCase(dayStr))
			return 2;
		if ("third day".equalsIgnoreCase(dayStr))
			return 2;
		if ("fifth day".equalsIgnoreCase(dayStr))
			return 2;
		if ("sixth day".equalsIgnoreCase(dayStr))
			return 2;

		return 0;
	}

	
}
