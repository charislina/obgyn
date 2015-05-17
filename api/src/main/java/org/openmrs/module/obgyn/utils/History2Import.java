package org.openmrs.module.obgyn.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.ObsService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;

public class History2Import {

	private String filename;
	private String encoding;
	
	public History2Import(String filename, String encoding) {
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
			
			String patientLine = null;
			
			while ((line = reader.readLine()) != null) {
				try {
			
					  
                      if (line.startsWith("###")) {
                             if (patientLine != null) {
                            	 String[] array = patientLine.split(" ");
                            	 
                            	 List<Patient> patients = patientService.getPatients(null, array[0], list, true);
             					if (!patients.isEmpty()) {
             						p = patients.get(0);	
             						addHistory1(p, array, countLines);							
             					}
             					
             					System.out.println(countLines + " fiels: " + array.length);
             					
                              }
                              patientLine = line.substring(3);
                      } else {
                              patientLine = patientLine + line;
                      }
                
					   
					String[] split=line.split("\t");
					
					
					
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
		
		
		
	
		
	}

	
	
}
