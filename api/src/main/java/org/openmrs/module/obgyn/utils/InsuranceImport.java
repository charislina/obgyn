package org.openmrs.module.obgyn.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;

public class InsuranceImport {

	private String filename;
	private String encoding;
	
	public InsuranceImport(String filename, String encoding) {
		this.filename = filename;
		this.encoding = encoding;
	}
	
	public void readFile() {
		int countLines = 0;
		int updatePatients = 0;
		System.out.println("reading " + this.filename);
		try {
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding));
			String line = null;
			
			PatientService patientService = Context.getPatientService();
			Patient p = null;
			List<PatientIdentifierType> list = new ArrayList<PatientIdentifierType>();
			list.add(new PatientIdentifierType(1));
			
			while ((line = reader.readLine()) != null) {
				try {
					String[] split=line.split("\t");
					List<Patient> patients = patientService.getPatients(null, split[0], list, true);
					if (!patients.isEmpty()) {
						p = patients.get(0);	
						addInsuranceTypes(p, split, countLines);							
					}
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("ERROR ROW " + countLines + " " + e.getMessage());
				}
				countLines++;
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.format("IOException: %s%n", e);
		}
		System.out.println("Found " + countLines + " lines");
		System.out.println("Updated " + updatePatients + " patients");
	}
	
	
	public void addInsuranceTypes(Patient patient, String[] fields, int row) throws Exception {
	
		try {
			
				for (int i = 1;i < fields.length;i++) {
				
					PersonAttributeType attrType = getAttributeTypeForIndex(i);
					if (attrType != null) {
						String attrValue = getAttributeValueForType(attrType.getId(), fields[i]);
						if (attrValue != null) {
							patient.addAttribute(new PersonAttribute(attrType, attrValue));
							//System.out.print("added attr "+ attrType.getId() + " " + attrValue +"\t");						
						}
					} 
			
				}

				Context.getPatientService().savePatient(patient);	
				
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private PersonAttributeType getAttributeTypeForIndex(int fieldIndex) {
		int attrId = -1;
		
		switch(fieldIndex) {
	
		
			
		case 1:	
			//Insurance type
			attrId = 	11;
			break;
			
		case 2:	
			//Second insurance type
			attrId = 	27;
			break;
			
		case 3:	
			//Private insurance
			attrId = 	17;
			break;
			
		case 4:
			//Second Private Insurance Type
			attrId = 	28;
			break;
			
		
		case 5:	
			//spouse insurance
			attrId = 	30;
			break;
			
		case 6:	
			//private insurance
			attrId = 	31;
			break;
			
		
		
		default: 
			return null;
		}
		
		return new PersonAttributeType(new Integer(attrId));
	}

	private String getBooleanValue(String value, boolean defaultValue) {
		
		value = (value!=null?value.toLowerCase():value);
		
		if (value == "yes" || value == "true")
			return "true";
	
		if (value == "false" || value == "no")
			return "false";
		
		return (defaultValue?"true":"false");
		
	}
	
	private String getAttributeValueForType(int typeId, String value) {
		String attrValue = null;
		
		switch(typeId) {
		
		case 11:	
			
			//Insurance type
			attrValue = getConceptIdFromValue(value);
			break;
		case 27:	
			
			//Second insurance type
			attrValue = getConceptIdFromValue(value);
			break;
		case 17:	
			
			//Private insurance
			attrValue = getConceptIdFromValue(value);
			break;
		case 28:
			
			//Second Private Insurance Type
			attrValue = getConceptIdFromValue(value);
			break;
		
		case 30:	
			
			//spouse insurance
			attrValue = getConceptIdFromValue(value);
			break;
		case 31:	
			
			//spouse private insurance
			attrValue = getConceptIdFromValue(value);
			break;
		
		default: 
			return null;
		}
		
		return attrValue;
	}
	
	private String getConceptIdFromValue(String value) {
		
		if (value == null || value.equals(""))
			return null;
		
		if (value.equals("ΙΚΑ") || value.equals("IKA"))
			return "163197";
		
		if (value.equals("ΙΔΙΩΤΙΚΗ"))
			return "5622";
		if (value.equals("INTERAMERICAN"))
			return "163199";
		
		if (value.equals("ALLIANZ"))
			return "163454";
		
		ConceptService conceptService = Context.getConceptService();
		Concept concept = conceptService.getConceptByIdOrName(value);
		if (concept == null) {			
			System.out.println("did not find concept for *" + value+"*");
			return null;
		}
		//System.out.println("found concept for " + value + " " + concept.getConceptId());
		return concept.getConceptId()+"";
	}
	
	
	private Date getDateFromStr(String dateStr, int daysToAdd) {
		try {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Athens"));
		String[] parts = dateStr.split("/");
		int d = Integer.parseInt(parts[0]);
		int m = Integer.parseInt(parts[1]);
		int y = Integer.parseInt(parts[2]);
		if (y> 15 && y < 100)
			y+=1900;
		else if (y < 15)
			y+=2000;
		
		cal.set(Calendar.DATE, d+daysToAdd);
		cal.set(Calendar.YEAR, y);
		cal.set(Calendar.MONTH, m-1);
		
		return cal.getTime();
		} catch(Exception e) {
			
		}
		return null;
	}
	

}
