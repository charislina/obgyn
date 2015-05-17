package org.openmrs.module.obgyn.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;

public class ConceptImport {

	private String filename;
	private String encoding;
	private String encoding2;
	private String conceptId;
	private String conceptName;
	private String elConceptName;
	private Locale enLocale;
	private Locale elLocale;
	
	public ConceptImport(String filename, String encoding, String encoding2) {
		this.filename = filename;
		this.encoding = encoding;
		this.encoding2 = encoding2;
		this.elLocale = new Locale("el");
		this.enLocale = new Locale("en");
	}
	
	private boolean createConcept(String conceptName, String elConceptName) {
		boolean success = false;
		try {
			
			ConceptService conceptService = Context.getConceptService();
			Concept concept = new Concept();
			//11 is misc
			//4 is N/A
			concept.setConceptClass(Context.getConceptService().getConceptClass(11));
			concept.setDatatype(Context.getConceptService().getConceptDatatype(4));
			Concept insuraceConcept = conceptService.getConcept(5555);
			if (elConceptName != null) {		
				
				try {
					if (conceptName != null) {
						ConceptName enConceptName = new ConceptName(conceptName, enLocale);
						concept.addName(enConceptName);
						concept.setPreferredName(enConceptName);	
						concept.addDescription(new ConceptDescription(conceptName, enLocale));
					}
					ConceptName elConcept = new ConceptName(elConceptName, elLocale);
					concept.setFullySpecifiedName(elConcept);
					concept.addName(elConcept);
					
					concept.addDescription(new ConceptDescription(elConceptName, elLocale));
					conceptService.saveConcept(concept);
			   		success = true;
			   		ConceptAnswer answer = new ConceptAnswer(concept);
					insuraceConcept.addAnswer(answer);
					
				} 
				catch (org.openmrs.api.DuplicateConceptNameException e) {
					System.out.println(e.getMessage());
					
					
				}
				
			}
			
			conceptService.saveConcept(insuraceConcept);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	public void readFile() {
		int countLines = 0;
		int newConcepts = 0;
		
		try {
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding));
			String line = null;
			String prevLine = "";
			ConceptService conceptService = Context.getConceptService();
			Concept occConcept = conceptService.getConcept(1542);
			Concept spouseOccConcept = conceptService.getConcept(163208);
						
			while ((line = reader.readLine()) != null) {
				String[] split=line.split("\t");
				if (split.length == 2) {
					String enConceptName = split[0];
					
					String elConceptName = split[1];
					if (createConcept(enConceptName, elConceptName))
						newConcepts++;					
				} else {
					String elConceptName = split[0];
					if (createConcept(null, elConceptName))
						newConcepts++;	
				}
				/*
				try {
					
					
					Concept concept = conceptService.getConceptByIdOrName(split[0]);
					occConcept.addAnswer(new ConceptAnswer(concept));
					spouseOccConcept.addAnswer(new ConceptAnswer(concept));
					
					conceptService.saveConcept(occConcept);
					conceptService.saveConcept(spouseOccConcept);
					
				} catch(Exception e) {
					e.printStackTrace();
				}
				*/
				countLines++;
			}
			
			
			
			reader.close();

		} catch (Exception x) {
			System.err.format("IOException: %s%n", x);
		}
		System.out.println("Found " + countLines + " lines");
		System.out.println("Inserted " + newConcepts + " concepts");
	}
}