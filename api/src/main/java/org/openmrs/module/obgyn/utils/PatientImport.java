package org.openmrs.module.obgyn.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.PersonName;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;

public class PatientImport {

	private String filename;
	private String encoding;
	
	public PatientImport(String filename, String encoding) {
		this.filename = filename;
		this.encoding = encoding;
	}
	
	public boolean createPatient(String[] fields) {
		boolean success = false;
		PatientService patientService = Context.getPatientService();
		Patient patient = null;
		try {
		
			
			int identifier = Integer.parseInt(fields[0]);
			patient = new Patient();
			patient.addIdentifier(new PatientIdentifier(fields[0], new PatientIdentifierType(1), Context.getLocationService().getLocation(1)));
			System.out.print("created patient with id " + identifier + "\t");
			
			//given, middle, family
			String familyName = fields[1]; //family
			String givenName = fields[2]; //given
			String middleName = fields[3]; //middle
			
			PersonName personName = new PersonName(givenName, middleName, familyName);
			
			patient.addName(personName);
			System.out.print("added name\t");
			
			PersonAddress homeAddress = new PersonAddress();
			String homeAddressS = fields[4];
			String homeRegion = fields[5];
			String homeCity = fields[6];
			String homePostalCode = fields[7];
			homeAddress.setAddress1(homeAddressS);
			homeAddress.setAddress2(homeRegion);
			homeAddress.setCityVillage(homeCity);
			homeAddress.setPostalCode(homePostalCode);
			
			patient.addAddress(homeAddress);
			System.out.print("added home address\t");
			
			PersonAddress workAddress = new PersonAddress();
			String workAddressS = fields[8];
			String workRegion = fields[9];			
			System.out.print("added work adress\t");
			
			workAddress.setAddress1(workAddressS);
			workAddress.setAddress2(workRegion);
			patient.addAddress(workAddress);
			
			if (fields.length > 10) {
				//	System.out.print("added birthdate " + fields[25]+ "\t");
					Date d = getDateFromStr(fields[10]);
					if (d == null)
						d = new Date();
					patient.setBirthdate(d);
			}
							
			
			for (int i = 11;i < fields.length;i++) {
				
				PersonAttributeType attrType = getAttributeTypeForIndex(i);
				if (attrType != null) {
					String attrValue = getAttributeValueForType(attrType.getId(), fields[i]);
					if (attrValue != null) {
						patient.addAttribute(new PersonAttribute(attrType, attrValue));
						System.out.print("added attr "+ attrType.getId() + " " + attrValue +"\t");						
					}
				} 
			
			}
			

			
			patient.setGender("F");
			patientService.savePatient(patient);			
			success = true;
			
		} catch(Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	

	public void readFile() {
		int countLines = 0;
		int newPatients = 0;
		
		try {
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding));
			String line = null;
			String prevLine = "";
			ConceptService conceptService = Context.getConceptService();
			Concept occConcept = conceptService.getConcept(1542);
			Concept spouseOccConcept = conceptService.getConcept(163208);
						
			while ((line = reader.readLine()) != null) {
				try {
					
					String[] split=line.split("\t");
				
					if (createPatient(split))
						newPatients++;
					else
						System.out.println("DID NOT CREATE PATIENT " + split[0]);
				} catch(Exception e) {
					System.out.println("ERROR ROW " + countLines + " " + e.getMessage());
				}
				countLines++;
			}
			
			
			
			reader.close();

		} catch (Exception x) {
			System.err.format("IOException: %s%n", x);
		}
		System.out.println("Found " + countLines + " lines");
		System.out.println("Inserted " + newPatients + " patients");
	}
	
	
	private PersonAttributeType getAttributeTypeForIndex(int fieldIndex) {
		int attrId = -1;
		
		switch(fieldIndex) {
	
		case 11:
			//medical id
			attrId=36;
			break;	
			
		case 12:	
			//Occupation
			attrId = 	9;
			break;
			
		case 13:	
			//Company Name
			attrId = 	32;
			break;
			
		case 14:	
			//Insurance type
			attrId = 	11;
			break;
			
		case 15:	
			//Second insurance type
			attrId = 	27;
			break;
			
		case 16:	
			//Private insurance
			attrId = 	17;
			break;
			
		case 17:	
			//Spouse Last Name
			attrId = 	14;
			break;
			
		case 18:	
			//Spouse First Name
			attrId = 	13;
			break;
			
		case 19:	
			//Spouse Birthdate
			attrId = 	34;
			break;
			
		case 20:	
			//reference
			attrId = 	12;
			break;
			
		case 21:	
			//is typet
			attrId = 	19;
			break;
			
		case 22:	
			//marital status
			attrId = 	5;
			break;
			
		case 23:	
			// email
			attrId = 	26;
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
		case 8:	
			//SSN
			attrValue = value;
			break;
		case 29:	
			//Direct insurance
			attrValue = getBooleanValue(value, true);
			break;
		case 10:	
			//Father name
			attrValue = value;
			break;
		case 9:	
			//Occupation
			
			attrValue = getConceptIdFromValue(value);
			break;
		case 2:	
			//Birthplace
			attrValue = value;
			break;
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
		case 32:	
			//Company Name
			attrValue = value;
			break;		
		case 14:	
			//Spouse Last Name
			attrValue = value;
			break;
		case 13:	
			//Spouse First Name
			attrValue = value;
			break;
		case 15:	
			
			//Spouse occupation
			attrValue = getConceptIdFromValue(value);
			break;
		case 35:	
			//Spouse birthplace
			attrValue = value;
			break;
		case 30:	
			
			//spouse insurance
			attrValue = getConceptIdFromValue(value);
			break;
		case 31:	
			
			//spouse private insurance
			attrValue = getConceptIdFromValue(value);
			break;
		case 33:	
			
			//spouse company name
			attrValue = value;
			break;	
		case 37:	
			//spouse email
			attrValue = value;
			break;
		case 26:	
			// email
			attrValue = value;
			break;
		case 12:	
			//reference
			attrValue = value;
			break;
		case 16:	
			//is doctor
			attrValue = getBooleanValue(value, false);
			break;
		case 19:	
			//is typet
			attrValue = getBooleanValue(value, false);
			break;
		case 5:	
			//marital status
			attrValue = getConceptIdFromValue(value);
			break;
		case 34:	
			//spouse birthdate
			attrValue = value;
			break;
		case 36:
		case 38:
		case 39:
		case 40:
			//medical id
			attrValue = value;
			break;
		default: 
			return null;
		}
		
		return attrValue;
	}
	
	private String getConceptIdFromValue(String value) {
		ConceptService conceptService = Context.getConceptService();
		Concept concept = conceptService.getConceptByIdOrName(value);
		if (concept == null) {			
			System.out.println("did not find concept for " + value);
			return null;
		}
		//System.out.println("found concept for " + value + " " + concept.getConceptId());
		return concept.getConceptId()+"";
	}
	
	private Date getDateFromStr(String dateStr) {
		try {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Athens"));
		String[] parts = dateStr.split("/");
		int d = Integer.parseInt(parts[0]);
		int m = Integer.parseInt(parts[1]);
		int y = Integer.parseInt(parts[2]);
		if (y> 15 && y < 100)
			y+=1900;
		
		cal.set(Calendar.DATE, d);
		cal.set(Calendar.YEAR, y);
		cal.set(Calendar.MONTH, m);
		
		return cal.getTime();
		} catch(Exception e) {
			
		}
		return null;
	}
	
}
