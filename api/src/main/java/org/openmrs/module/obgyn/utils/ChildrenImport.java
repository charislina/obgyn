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
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;

public class ChildrenImport {

	private String filename;
	private String encoding;
	
	public ChildrenImport(String filename, String encoding) {
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
						addChild(p, split);							
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
	
	
	public void addChild(Patient p, String[] split) throws Exception {

		Encounter encounter = new Encounter();
		Form form = Context.getFormService().getForm(7);
		encounter.setForm(form);
		encounter.setLocation(Context.getLocationService().getLocation(1));
		encounter.setPatient(p);
		encounter.setEncounterDatetime(new Date());
		encounter.setEncounterType(new EncounterType(11));
		Context.getEncounterService().saveEncounter(encounter);
		
		Concept parentConcept = Context.getConceptService().getConceptByIdOrName("162861");
		if (parentConcept == null)
			System.out.println("162861 is null");
		
		Obs obs = new Obs(p, parentConcept, new Date(), Context.getLocationService().getLocation(1));
		obs.setEncounter(encounter);
		
		//Child Name
		if (split[3] != null && !split[3].equals("")) {
			Obs nameObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("1586"), new Date(), Context.getLocationService().getLocation(1));
			nameObs.setValueText(split[3]);
			nameObs.setEncounter(encounter);			
			obs.addGroupMember(nameObs);
		}

		
		//Child Birth Date (162857)
		Obs dateObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162857"), new Date(), Context.getLocationService().getLocation(1));
		dateObs.setValueDate(getDateFromStr(split[5]));
		dateObs.setEncounter(encounter);
		obs.addGroupMember(dateObs);
		
		
		//CHILD GENDER (1587)
		if (split[4] != null && !split[4].equals("")) {
			Obs genderObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("1587"), new Date(), Context.getLocationService().getLocation(1));
			if ("M".equals(split[4]))
				genderObs.setValueCoded(Context.getConceptService().getConceptByIdOrName("1534"));
			else
				genderObs.setValueCoded(Context.getConceptService().getConceptByIdOrName("1535"));									
			genderObs.setEncounter(encounter);
			obs.addGroupMember(genderObs);								
		}
		
		// CHILD STILL ALIVE
		Obs stillAliveObs = new Obs(p, Context.getConceptService().getConceptByIdOrName("1571"), new Date(), Context.getLocationService().getLocation(1));
		stillAliveObs.setValueCoded(Context.getConceptService().getConceptByIdOrName("1065"));
		stillAliveObs.setEncounter(encounter);
		obs.addGroupMember(stillAliveObs);		
		
		Context.getObsService().saveObs(obs, "new entry");
		
		
	}
	private PersonAttributeType getAttributeTypeForRow(int row) {
		int attrId = -1;
		
		switch(row) {
		
			case 0:
				attrId =  20;
				break;
			case 1:
				attrId =  21;
				break;

			case 2:
				attrId =  22;
				break;

			case 3:
				attrId =  23;
				break;

			case 4:
				attrId =  42;
				break;

			case 5:
				attrId =  25;
				break;
						
			default: 
				return null;
		}
	
		return new PersonAttributeType(new Integer(attrId));
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
}
