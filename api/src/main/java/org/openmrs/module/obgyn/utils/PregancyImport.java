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
import org.openmrs.PersonAttributeType;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;

public class PregancyImport {
	private String filename;
	private String encoding;
	
	public PregancyImport(String filename, String encoding) {
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
			String prevLine = "";
			
			int newId = -1;
			int prevId = -1;
			int currId = -1;
			int count = 0;
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
						addPregnancy(p, split, countLines);							
					}
					System.out.println(countLines + " fiels: " + split.length);
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
	
	
	public void addPregnancy(Patient p, String[] split, int row) throws Exception {

		Encounter encounter = new Encounter();
		Form form = Context.getFormService().getForm(13);
		encounter.setForm(form);
		encounter.setLocation(Context.getLocationService().getLocation(1));
		encounter.setPatient(p);
		encounter.setEncounterDatetime(new Date());
		encounter.setEncounterType(new EncounterType(2));
		Context.getEncounterService().saveEncounter(encounter);
		
		Concept parentConcept = Context.getConceptService().getConceptByIdOrName("163217");
		if (parentConcept == null)
			System.out.println("163217 is null");
		
		Obs obs = new Obs(p, parentConcept, new Date(), Context.getLocationService().getLocation(1));
		obs.setEncounter(encounter);
		
		
		//LMP
		
		int colIndex = 1;
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs dateObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("1427"), new Date(), Context.getLocationService().getLocation(1));
			dateObs.setValueDate(getDateFromStr(split[colIndex]));
			obs.addGroupMember(dateObs);
		}
		
		colIndex = 3;
		//Corr LMP
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs dateObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162938"), new Date(), Context.getLocationService().getLocation(1));
			Date corrLMP = getDateFromStr(split[colIndex]);
			if (corrLMP != null) {			
				dateObs.setValueDate(corrLMP);
				obs.addGroupMember(dateObs);
				Date laborDate = getDateFromStr(split[colIndex], 270);
				// ACTIVE STATUS
				if (laborDate.after(new Date())) {					
					Obs statusObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162937"), new Date(), Context.getLocationService().getLocation(1));
					statusObs.setValueCoded(Context.getConceptService().getConceptByIdOrName("161636"));
					statusObs.setEncounter(encounter);
					obs.addGroupMember(statusObs);
				}
			}
			
		}
		
		colIndex = 5;
		//admission date
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs dateObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("1640"), new Date(), Context.getLocationService().getLocation(1));
			dateObs.setValueDate(getDateFromStr(split[colIndex]));
			obs.addGroupMember(dateObs);
		}
		
		colIndex = 6;
		//admission reason
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs strObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("1655"), new Date(), Context.getLocationService().getLocation(1));
			strObs.setValueText(split[colIndex]);
			obs.addGroupMember(strObs);
		}
		
		// pregnancy comment		
	/*	colIndex = 18;
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs strObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162916"), new Date(), Context.getLocationService().getLocation(1));
			strObs.setValueText(split[colIndex]);
			obs.addGroupMember(strObs);
		}
		*/	
				
		//LABOR GROUP
		
			
		boolean delivered = false;
		colIndex = 9;
	
		//FT
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs deliveryObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("5630"), new Date(), Context.getLocationService().getLocation(1));
			if (getBooleanValue(split[colIndex], false) == Boolean.TRUE) {
				deliveryObs.setValueCoded(Context.getConceptService().getConceptByIdOrName("1170"));
				deliveryObs.setEncounter(encounter);				
				obs.addGroupMember(deliveryObs);
				delivered = true;
			}
		}
		
		colIndex = 10;
		//KT
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs deliveryObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("5630"), new Date(), Context.getLocationService().getLocation(1));
			if (getBooleanValue(split[colIndex], false) == Boolean.TRUE) {
				deliveryObs.setValueCoded(Context.getConceptService().getConceptByIdOrName("1171"));
				deliveryObs.setEncounter(encounter);								
				obs.addGroupMember(deliveryObs);				
				delivered = true;
			}
		}
		
		colIndex = 12;
		//epidural
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs boolObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162927"), new Date(), Context.getLocationService().getLocation(1));
			boolObs.setValueBoolean(getBooleanValue(split[colIndex], false));
			obs.addGroupMember(boolObs);
			delivered = true;
		}
		
			
		colIndex = 13;
		// general anesthesia		
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs boolObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162925"), new Date(), Context.getLocationService().getLocation(1));
			boolObs.setValueBoolean(getBooleanValue(split[colIndex], false));
			obs.addGroupMember(boolObs);
		}

		colIndex = 14;
		// labor date
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs dateObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162917"), new Date(), Context.getLocationService().getLocation(1));
			dateObs.setValueDate(getDateFromStr(split[colIndex]));
			obs.addGroupMember(dateObs);
			delivered = true;

		}		
		colIndex = 15;
		// labor week
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs numObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162918"), new Date(), Context.getLocationService().getLocation(1));
			numObs.setValueNumeric(Double.parseDouble(split[colIndex]));
			obs.addGroupMember(numObs);								
		}
			
		
		colIndex = 7;
		//NEW BORN GROUP
				
		// new born gender
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs genderObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("1587"), new Date(), Context.getLocationService().getLocation(1));
			if ("M".equals(split[colIndex]))
				genderObs.setValueCoded(Context.getConceptService().getConceptByIdOrName("1534"));
			else
				genderObs.setValueCoded(Context.getConceptService().getConceptByIdOrName("1535"));									
			obs.addGroupMember(genderObs);	
			delivered = true;

		}
		
		colIndex = 8;
		// new born weight
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			Obs numObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("163474"), new Date(), Context.getLocationService().getLocation(1));
			numObs.setValueNumeric(Double.parseDouble(split[colIndex]));
			obs.addGroupMember(numObs);	
			delivered = true;

		}
		
		// new born STILL ALIVE
		Obs stillAliveObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("1571"), new Date(), Context.getLocationService().getLocation(1));
		stillAliveObs.setValueCoded(Context.getConceptService().getConceptByIdOrName("1065"));
		//stillAliveObs.setEncounter(encounter);
		obs.addGroupMember(stillAliveObs);		
						
		if (delivered) {
			Obs statusObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162937"), new Date(), Context.getLocationService().getLocation(1));
			statusObs.setValueCoded(Context.getConceptService().getConceptByIdOrName("162934"));
			statusObs.setEncounter(encounter);
			obs.addGroupMember(statusObs);
		}
	
		/*
		colIndex = 19;
		// labor comment		
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("")) {
			
		
			Concept c = Context.getConceptService().getConcept(163476);
			Obs strObs = new Obs(p, c, new Date(), Context.getLocationService().getLocation(1));
				strObs.setValueText(split[colIndex]);
				strObs.setEncounter(encounter);
			obs.addGroupMember(strObs);
			
		}
		*/
		Context.getObsService().saveObs(obs, "new entry" );
		
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
		else if (y < 15)
			y+=2000;
		
		cal.set(Calendar.DATE, d);
		cal.set(Calendar.YEAR, y);
		cal.set(Calendar.MONTH, m-1);
		
		return cal.getTime();
		} catch(Exception e) {
			
		}
		return null;
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
	
	private Boolean getBooleanValue(String value, boolean defaultValue) {
		
		value = (value!=null?value.toLowerCase():value);
		
		if (value == "yes" || value == "true")
			return Boolean.TRUE;
	
		if (value == "false" || value == "no")
			return Boolean.FALSE;
		
		return (defaultValue?Boolean.TRUE:Boolean.FALSE);
		
	}
	
}
