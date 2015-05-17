package org.openmrs.module.obgyn.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.ObsService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;

public class History1Import {

	private String filename;
	private String encoding;
	
	public History1Import(String filename, String encoding) {
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
						addHistory1(p, split, countLines);							
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
	
	public void addHistory1(Patient p, String[] split, int row) throws Exception {

		Encounter encounter = new Encounter();
		Form form = Context.getFormService().getForm(6);
		encounter.setForm(form);
		encounter.setLocation(Context.getLocationService().getLocation(1));
		encounter.setPatient(p);
		encounter.setEncounterDatetime(new Date());
		encounter.setEncounterType(new EncounterType(11));
		Context.getEncounterService().saveEncounter(encounter);
		ObsService obsService = Context.getObsService();
		
		
		//blood type
		String bloodType="";
		
		int colIndex = 1;
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("") ) {			
			bloodType = split[colIndex];
			if (bloodType != null)
				bloodType.trim();
		}
		/*
		colIndex = 4;
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("") ) { 
			bloodType = bloodType+split[colIndex];
			if (bloodType != null)
				bloodType.trim();
		}
		*/
		String bloodConceptId = getBloodConceptId(bloodType);			
		if (bloodConceptId != null) {
			Obs obs = new Obs(p, Context.getConceptService().getConceptByIdOrName("300"), new Date(), Context.getLocationService().getLocation(1));
			obs.setValueCoded(Context.getConceptService().getConceptByIdOrName(bloodConceptId));
			obs.setEncounter(encounter);
			obsService.saveObs(obs,"blood type");
		}
		
		colIndex = 2;
		//cigs per day
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("") ) { 
		
			int num = 0;
			try {
				num = Integer.parseInt(split[colIndex]);
			} catch(Exception e) {
				num = 0;
			}
			
			if (num > 0) {
				Obs obs = new Obs(p, Context.getConceptService().getConceptByIdOrName("1546"), new Date(), Context.getLocationService().getLocation(1));
				obs.setValueNumeric((double)num);
				obs.setEncounter(encounter);

				obsService.saveObs(obs,"cigs per day");
			}
			
		}
		
		colIndex = 9;
		//stopped smoking
		
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("") ) { 
			
			boolean hasStopped = false;
			try {
				hasStopped = Boolean.parseBoolean(split[colIndex]);
			} catch(Exception e) {
				hasStopped = false;
			}
			
			if (hasStopped) {
				Obs obs = new Obs(p, Context.getConceptService().getConceptByIdOrName("163204"), new Date(), Context.getLocationService().getLocation(1));
				obs.setValueBoolean(hasStopped);
				obs.setEncounter(encounter);

				obsService.saveObs(obs,"stopped smoking");
			}
			
		}
		
		colIndex= 10;
		// drinks per week
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("") ) { 
			
			int num = 0;
			try {
				num = Integer.parseInt(split[colIndex]);
			} catch(Exception e) {
				num = 0;
			}
			
			if (num > 0) {
				Obs obs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162835"), new Date(), Context.getLocationService().getLocation(1));
				obs.setValueNumeric((double)num);
				obs.setEncounter(encounter);

				obsService.saveObs(obs,"drinks per week");
			}
			
		}
	/*	
		colIndex = 14;
		//gen history
		//162877
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("") ) { 			
			Obs obs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162877"), new Date(), Context.getLocationService().getLocation(1));
			obs.setValueText(split[colIndex]);
			obsService.saveObs(obs,"histo1");
		}
		
		colIndex = 15;
		//surgical history
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("") ) { 			
			Obs obs = new Obs(p, Context.getConceptService().getConceptByIdOrName("162966"), new Date(), Context.getLocationService().getLocation(1));
			obs.setValueText(split[colIndex]);
			obsService.saveObs(obs,"histo1");
		}
		
		colIndex = 16;
		//drugs
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("") ) { 			
			Obs obs = new Obs(p, Context.getConceptService().getConceptByIdOrName("163477"), new Date(), Context.getLocationService().getLocation(1));
			obs.setValueText(split[colIndex]);
			obsService.saveObs(obs,"histo1");
		}
		
		colIndex = 17;
		//various
		if (split.length>colIndex && split[colIndex] != null && !split[colIndex].equals("") ) { 			
			Obs obs = new Obs(p, Context.getConceptService().getConceptByIdOrName("163478"), new Date(), Context.getLocationService().getLocation(1));
			obs.setValueText(split[colIndex]);
			obsService.saveObs(obs,"histo1");
		}
		*/
		
	}

	private String getBloodConceptId(String bloodTypeStr) {
		
		if (bloodTypeStr == null || bloodTypeStr.equals("")) 
			return null;			
		
		if (bloodTypeStr.equalsIgnoreCase("O+"))
			return "699";
		
		if (bloodTypeStr.equalsIgnoreCase("O-"))
			return "701";

		if (bloodTypeStr.equalsIgnoreCase("A+"))
			return "690";

		if (bloodTypeStr.equalsIgnoreCase("A-"))
			return "692";

		if (bloodTypeStr.equalsIgnoreCase("B+"))
			return "694";

		if (bloodTypeStr.equalsIgnoreCase("B-"))
			return "696";

		if (bloodTypeStr.equalsIgnoreCase("AB+"))
			return "1230";

		if (bloodTypeStr.equalsIgnoreCase("AB-"))
			return "1231";
		
		return null;		
					
	}
	
}
