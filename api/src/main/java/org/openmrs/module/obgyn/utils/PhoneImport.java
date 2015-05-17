package org.openmrs.module.obgyn.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;

public class PhoneImport {

	private String filename;
	private String encoding;
	
	public PhoneImport(String filename, String encoding) {
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
				
					currId = Integer.parseInt(split[0]);
					if (currId != prevId) {
						count = 0;
						prevId = currId;	
						if (p != null) {
						//	patientService.savePatient(p);
							updatePatients++;
							System.out.println("\n");
						}
						List<Patient> patients = patientService.getPatients(null, split[0], list, true);
						if (!patients.isEmpty()) {
							p = patients.get(0);
							p.addAttribute(new PersonAttribute(getAttributeTypeForRow(count), split[5]));
							int attrId = getAttributeTypeForRow(count).getPersonAttributeTypeId();
							System.out.print("patient " + p.getId() + " " + attrId + " = " +split[5] );
						} else
							System.out.println("PERSON " + split[0] + " NOT FOUND");
					} else {
						int attrId = getAttributeTypeForRow(count).getPersonAttributeTypeId();
						p.addAttribute(new PersonAttribute(getAttributeTypeForRow(count), split[5]));
						
						System.out.print(" " + attrId + " = " +split[5] );
					}
					count++;
					
				} catch(Exception e) {
					System.out.println("ERROR ROW " + countLines + " " + e.getMessage());
				}
				countLines++;
			}
			if (p != null) {
				updatePatients++;
				patientService.savePatient(p);
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.format("IOException: %s%n", e);
		}
		System.out.println("Found " + countLines + " lines");
		System.out.println("Updated " + updatePatients + " patients");
	}
	
	
	private PersonAttributeType getAttributeTypeForRow(int row) {
		int attrId = -1;
		
		switch(row) {
		
			case 0: //Personal Fixed Phone Number	
				attrId =  20;
				break;
			case 1: //Personal Mobile Phone Number
				attrId =  21;
				break;

			case 2: //Work Fixed Phone Number
				attrId =  22;
				break;

			case 3: //Work Mobile Phone Number
				attrId =  23;
				break;

			case 4: //Spouse Work Fixed Phone Number
				attrId =  42;
				break;

			case 5: //Spouse Work Mobile Phone Number	
				attrId =  25;
				break;
			//24	Spouse Personal Mobile Phone Number
			default: 
				return null;
		}
	
		return new PersonAttributeType(new Integer(attrId));
	}
	
	/*
	 * person_attribute_type_id	name	description	format	foreign_key	searchable	creator	date_created	changed_by	date_changed	retired	retired_by	date_retired	retire_reason	edit_privilege	sort_weight	uuid	
20	Personal Fixed Phone Number	Personal Phone Number	java.lang.String	<NULL>	true	1	2015-03-28 12:50:09.0	1	2015-04-14 10:55:22.0	false	<NULL>	<NULL>	<NULL>	<NULL>	20	90623541-1899-4910-92ac-b807b5ed2bb3	
21	Personal Mobile Phone Number	Personal Mobile Phone Number	java.lang.String	<NULL>	true	1	2015-03-28 12:50:23.0	1	2015-04-14 10:55:17.0	false	<NULL>	<NULL>	<NULL>	<NULL>	21	6ccd00d0-af2a-43de-a75d-4b577be2a311	
22	Work Fixed Phone Number	Work Fixed Phone Number	java.lang.String	<NULL>	true	1	2015-03-28 12:50:48.0	1	2015-04-14 10:52:23.0	false	<NULL>	<NULL>	<NULL>	<NULL>	24	542f88cb-896c-42fb-b86f-114c0aff2bd1	
23	Work Mobile Phone Number	Work Mobile Phone Number	java.lang.String	<NULL>	true	1	2015-03-28 12:50:58.0	1	2015-04-14 10:52:19.0	false	<NULL>	<NULL>	<NULL>	<NULL>	25	cefc09a2-9458-424a-ae66-0f0729f36f22	
24	Spouse Personal Mobile Phone Number	Spouse Personal Mobile Phone Number	java.lang.String	<NULL>	true	1	2015-03-28 12:51:20.0	1	2015-04-14 10:52:14.0	false	<NULL>	<NULL>	<NULL>	<NULL>	26	a7bdfa90-c03e-486a-a0e4-914922d164bf	
25	Spouse Work Mobile Phone Number	Spouse Work Mobile Phone Number	java.lang.String	<NULL>	true	1	2015-03-28 12:51:37.0	1	2015-04-14 10:52:08.0	false	<NULL>	<NULL>	<NULL>	<NULL>	27	b35d604c-457d-46fa-bfc4-9be93517e2c8	
42	Spouse Work Fixed Phone Number	Spouse Work Fixed Phone Number	java.lang.String	<NULL>	true	1	2015-04-18 16:17:46.0	<NULL>	<NULL>	false	<NULL>	<NULL>	<NULL>	<NULL>	41	06448f41-0f6a-4a7c-8564-76666d0cf4ec	

*/
	
}
