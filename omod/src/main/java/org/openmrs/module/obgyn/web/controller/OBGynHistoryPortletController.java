package org.openmrs.module.obgyn.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.ConceptService;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.web.controller.PortletController;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
//import org.openmrs.module.OBGyn7.utils.ConceptImport;

public class OBGynHistoryPortletController extends PortletController {

	private static final int PAST_SURGERY_FORM_ID = 14;
	private static final int SURGERY_FORM_ID = 9;
	
	private static final int PAST_GYNAE_ISSUE_FORM_ID = 15;
	private static final int GYNAE_ISSUE_FORM_ID = 11; 
	
	private static final int PAST_GENERAL_ISSUE_FORM_ID = 16;
	private static final int GENERAL_ISSUE_FORM_ID = 17; 
		
	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model) {
		
		Patient patient = (Patient) model.get("patient");

		model.put("patientId", patient.getPatientId());

	}
	
	/*
	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model) {
		
		Patient patient = (Patient) model.get("patient");

		model.put("patientId", patient.getPatientId());

		DataManager dataManager = new DataManager();
		Encounter encounter = null;

		if (request.getParameter("encounterId") == null) {
			Form form = Context.getFormService().getForm(6);
			List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, false);

			//get last enc
			if (encs != null && !encs.isEmpty()) 
				encounter = encs.get(encs.size() - 1);
			
		} else {
			String encounterId = request.getParameter("encounterId");
			encounter = Context.getEncounterService().getEncounter(Integer.parseInt(encounterId));
		}

		
		if (encounter != null) {
			model.put("encounterId", encounter.getEncounterId());

			GynHistory history = populateHistory(patient, encounter);
			model.put("gynHistory", history);	
		}
		
		
		// load children
		ArrayList<Child> children = dataManager.getChildren(patient);		
		model.put("children", children);		
		
		// load surgeries
		ArrayList<Surgery> surgeries = populateSurgeries(patient);			
		model.put("surgeries", surgeries);	

		// load past surgeries
		surgeries = populatePastSurgeries(patient);
		model.put("pastSurgeries", surgeries);	

		// load gynae medical history		
		ArrayList<MedicalIssue> medicalHistory = populateMedicalHistory(patient,GYNAE_ISSUE_FORM_ID);
		model.put("gynaeHistory", medicalHistory);	

		// load past gynae medical history		
		medicalHistory = populateMedicalHistory(patient, PAST_GYNAE_ISSUE_FORM_ID);
		model.put("pastGynaeHistory", medicalHistory);	

		// load general medical history		
		medicalHistory = populateMedicalHistory(patient, GENERAL_ISSUE_FORM_ID);
		model.put("generalHistory", medicalHistory);	

		// load past general medical history		
		medicalHistory = populateMedicalHistory(patient, PAST_GENERAL_ISSUE_FORM_ID);
		model.put("pastGeneralHistory", medicalHistory);	

		ArrayList<VisitNote> visitNotes = populateVisits(patient);
		model.put("visitNotes", visitNotes);
				
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder wdb) {	
		//The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		//Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);

		//Register it as custom editor for the Date type
		wdb.registerCustomEditor(Date.class, editor);

	}

	private GynHistory populateHistory(Patient patient, Encounter encounter) {

		GynHistory gynHistory = new GynHistory();

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);

		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, null, null, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			if (isConcept(concept, 1427)) {
				gynHistory.setLastPeriodDate(obs.getValueDate());
				continue;
			}

			if (isConcept(concept, 162833)) {
				gynHistory.setMenstruationDuration(obs.getValueNumeric().intValue());
				continue;
			}

			if (isConcept(concept, 162832)) {
				gynHistory.setMenstruationCycle(obs.getValueNumeric().intValue()); 
				continue;
			}

			if (isConcept(concept, 5089)) {
				gynHistory.setWeight(obs.getValueNumeric().floatValue()); 
				continue;
			}

			if (isConcept(concept, 5090)) {
				gynHistory.setHeight(obs.getValueNumeric().floatValue()); 
				continue;
			}

			if (isConcept(concept, 162832)) {
				gynHistory.setMenstruationCycle(obs.getValueNumeric().intValue()); 
				continue;
			}

			if (isConcept(concept, 162874)) {
				gynHistory.setContraception(obs.getValueBoolean()); 
				continue;
			}
			
			if (isConcept(concept, 162969)) {
				gynHistory.setClaimsInfertility(obs.getValueBoolean()); 
				continue;
			}

			if (isConcept(concept, 162970)) {
				gynHistory.setClaimsInfertilityComment(obs.getValueText()); 
				continue;
			}
			
			if (isConcept(concept, 163173)) {
				gynHistory.setReasonForFirstVisit(obs.getValueText()); 
				continue;
			}
			
			
			
			if (isConcept(concept, 162875)) {
				gynHistory.setContraceptionComment(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162847)) {
				gynHistory.setPapTestResult(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162849)) {
				gynHistory.setPapTestComment(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162848)) {
				gynHistory.setPapTestDate(obs.getValueDate()); 
				continue;
			}

			if (isConcept(concept, 162851)) {
				gynHistory.setPreviousPapTestComment(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162852)) {
				gynHistory.setMammogramResult(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162853)) {
				gynHistory.setMammogramComment(obs.getValueText());
				continue;
			}

			if (isConcept(concept, 162854)) {
				gynHistory.setMammogramDate(obs.getValueDate());
				continue;
			}

			if (isConcept(concept, 162830)) {
				gynHistory.setPostCoitalBleeding(obs.getValueBoolean());
				continue;
			}

			if (isConcept(concept, 162840)) {
				gynHistory.setPostCoitalBleedingComment(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162831)) {
				gynHistory.setIntermenstrualBleeding(obs.getValueBoolean());
				continue;
			}

			if (isConcept(concept, 162842)) {
				gynHistory.setIntermenstrualBleedingComment(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162856)) {
				gynHistory.setMenopausalState(obs.getValueCoded().getName().toString()); 
				continue;
			}
			if (isConcept(concept, 162871)) {
				gynHistory.setHormoneReplacementTherapyType(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept,  162872)) {
				gynHistory.setHormoneReplacementTherapyDate(obs.getValueDate()); 
				continue;
			}

			if (isConcept(concept,  162836)) {
				gynHistory.setTops(obs.getValueNumeric().intValue()); 
				continue;
			}

			if (isConcept(concept, 162837)) {
				gynHistory.setMiscs(obs.getValueNumeric().intValue()); 
				continue;
			}

			if (isConcept(concept, 162838)) {
				gynHistory.setNormalDeliveries(obs.getValueNumeric().intValue()); 
				continue;
			}

			if (isConcept(concept, 162839)) {
				gynHistory.setCsections(obs.getValueNumeric().intValue()); 
				continue;
			}

			if (isConcept(concept, 1053)) {
				gynHistory.setNumberOfChildren(obs.getValueNumeric().intValue()); 
				continue;
			}

			if (isConcept(concept, 1546)) {
				gynHistory.setCigarettesPerDay(obs.getValueNumeric().intValue()); 
				continue;
			}

			
			if (isConcept(concept, 163204)) {
				gynHistory.setStoppedSmoking(obs.getValueBoolean().booleanValue()); 
				continue;
			}
			
			if (isConcept(concept, 162834)) {
				gynHistory.setStoppedSmokingDate(obs.getValueDate()); 
				continue;
			}

			if (isConcept(concept, 162835)) {
				gynHistory.setAlcoholUnitsPerWeek(obs.getValueNumeric().intValue()); 
				continue;
			}

			if (isConcept(concept, 162844)) {
				gynHistory.setOtherAddictions(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162845)) {
				gynHistory.setFamilyHistoryGynCancerComment(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162846)) {
				gynHistory.setFamilyHistoryBreastCancerComment(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept,  162870)) {
				gynHistory.setGynaeHistory(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162877)) {
				gynHistory.setGeneralHistory(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 162966)) {
				gynHistory.setSurgicalHistory(obs.getValueText()); 
				continue;
			}

			if (isConcept(concept, 163477)) {
				gynHistory.setMedicationHistory(obs.getValueText()); 
				continue;
			}
			
			if (isConcept(concept, 163478)) {
				gynHistory.setMiscText(obs.getValueText()); 
				continue;
			}
			
			if (isConcept(concept, 163479)) {
				gynHistory.setComment(obs.getValueText()); 
				continue;
			}
			
			if (isConcept(concept,  300)) {
				String value = obs.getValueCoded().getName().toString();
				if (value != null) {
					boolean rhesusPositive = ("+".equals(value.substring(value.length()-2, value.length()-1)));
					gynHistory.setRhesus(rhesusPositive);
					gynHistory.setBloodType(value.substring(0, value.length()-2));
				}
				continue;
			}

		//	log.warn(obs.getConcept().getConceptId() + " " + obs.getConcept().getName().toString() + " " + obs.getConcept().toString());
		}

		return gynHistory;
	}

	


	private ArrayList<Surgery> populateSurgeries(Patient patient) {

		ArrayList<Surgery> surgeriesList = new ArrayList<Surgery>();

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(SURGERY_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, false);

		ConceptService conceptService = Context.getConceptService();

		List<Concept> conceptList = new ArrayList<Concept>();
		//Surgery details
		conceptList.add(conceptService.getConcept(162866));


		List<Obs> someObsList = obsService.getObservations(personList, encs, conceptList, null, null, null, null, null, null, null, null, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Set<Obs> members = obs.getGroupMembers();
			Surgery surgery = new Surgery();

			surgery.setEncounterId(obs.getEncounter().getEncounterId().intValue());
			
			surgeriesList.add(surgery);

		//	log.warn("surgeru obs: " + obs.getId()  + " " + (members!=null?members.size():0) + " " + obs.toString());

			for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {
				Obs memberObs = it2.next();
				Concept concept = memberObs.getConcept();
			//	log.warn(concept.getConceptId() + " " + concept.getName().toString() + " " + concept.toString());

				if (memberObs != null && memberObs.getValueCoded() != null && (isConcept(concept, 162958) || isConcept(concept, 162953) || isConcept(concept, 162954) || isConcept(concept, 162955) || isConcept(concept, 162956))) {
					surgery.addType(memberObs.getValueCoded().getName().toString()); 
					continue;
				}


				if (isConcept(concept, 162863)) {
					surgery.setDate(memberObs.getValueDate());
					continue;
				}

				if (isConcept(concept, 162873)) {
					surgery.setResult(memberObs.getValueText());
					continue;
				}

				if (isConcept(concept, 162864)) {
					surgery.setComplaints(memberObs.getValueText());
					continue;
				}


				if (isConcept(concept, 162957)) {
					surgery.setNote(memberObs.getValueText());
					continue;
				}


				if (isConcept(concept, 162884)) {
					surgery.setPerformedByProvider(new Boolean(memberObs.getValueBoolean()).booleanValue());
					continue;
				}

				if (isConcept(concept, 162865)) {
					surgery.setPaidByPatient(new Boolean(memberObs.getValueBoolean()).booleanValue());
					continue;
				}

				if (isConcept(concept, 163164)) {
					surgery.setDocumentObsId(memberObs.getObsId().intValue());
					continue;
				}
				
			}

		}	

		if (!surgeriesList.isEmpty())
			Collections.sort(surgeriesList);
	
		return surgeriesList;
	}

	private ArrayList<Surgery> populatePastSurgeries(Patient patient) {
		
		ArrayList<Surgery> surgeriesList = new ArrayList<Surgery>();

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(PAST_SURGERY_FORM_ID);
		
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, false);
		ConceptService conceptService = Context.getConceptService();

		List<Concept> conceptList = new ArrayList<Concept>();
		//Surgery details
		conceptList.add(conceptService.getConcept(162869));
		
		List<Obs> someObsList = obsService.getObservations(personList, encs, conceptList, null, null, null, null, null, null, null, null, false);
		
		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Set<Obs> members = obs.getGroupMembers();
			
			Surgery surgery = new Surgery();
			surgery.setEncounterId(obs.getEncounter().getEncounterId().intValue());			
			surgeriesList.add(surgery);

		//	log.warn("past surgeru obs: " + obs.getId()  + " " + (members!=null?members.size():0) + " " + obs.toString());

			for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {
				Obs memberObs = it2.next();
				Concept concept = memberObs.getConcept();
		//		log.warn(concept.getConceptId() + " " + concept.getName().toString() + " " + concept.toString());

				if (isConcept(concept, 162958) || isConcept(concept, 162953) || isConcept(concept, 162954) || isConcept(concept, 162955) || isConcept(concept, 162956)) {
					if (memberObs.getValueCoded() != null) {
						surgery.addType(memberObs.getValueCoded().getName().toString());
					}
					continue;
				}


				if (isConcept(concept, 162863)) {
					surgery.setDate(memberObs.getValueDate());
					continue;
				}

				if (isConcept(concept, 162873)) {
					surgery.setResult(memberObs.getValueText());
					continue;
				}

				if (isConcept(concept, 162864)) {
					surgery.setComplaints(memberObs.getValueText());
					continue;
				}


				if (isConcept(concept, 162957)) {
					surgery.setNote(memberObs.getValueText());
					continue;
				}

				if (isConcept(concept, 163164)) {
					surgery.setDocumentObsId(memberObs.getObsId().intValue());
					continue;
				}
			}

		}	

		if (!surgeriesList.isEmpty())
			Collections.sort(surgeriesList);
		
		return surgeriesList;
	}

	private ArrayList<MedicalIssue> populateMedicalHistory(Patient patient, int formId) {

		ArrayList<MedicalIssue> issues = new ArrayList<MedicalIssue>();

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(formId);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, false);
		
		if (encs == null || encs.isEmpty())
			return issues;
		
		ConceptService conceptService = Context.getConceptService();

		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList.add(conceptService.getConcept(162881));

		List<Obs> someObsList = obsService.getObservations(personList, encs, conceptList, null, null, null, null, null, null, null, null, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Set<Obs> members = obs.getGroupMembers();
			
			MedicalIssue medicalIssue = new MedicalIssue();
			medicalIssue.setEncounterId(obs.getEncounter().getEncounterId().intValue());
			issues.add(medicalIssue);
			
		//	log.warn("medicalHistory for FORM : " + formId+  " obs: " + obs.getId()  + " " + (members!=null?members.size():0) + " " + obs.toString());

			for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {
				Obs memberObs = it2.next();
				Concept concept = memberObs.getConcept();
			//	log.warn("\t"+concept.getConceptId() + " " + concept.getName().toString() + " " + concept.toString());

				if (isConcept(concept, 162879)) {
					medicalIssue.setMedicalHistoryAdded(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (isConcept(concept, 159948)) {
					medicalIssue.setDiagnosisDate(memberObs.getValueDate());
					continue;
				}

				if (isConcept(concept, 1729)) {
					medicalIssue.setStillPresent(memberObs.getValueCoded().getName().toString());
					continue;
				}

				if (isConcept(concept, 162880)) {
					medicalIssue.setMedicalHistoryAddedText(memberObs.getValueText());
					continue;
				}

				if (isConcept(concept, 162969)) {
					medicalIssue.setType(memberObs.getValueCoded().getName().toString());
					continue;
				}
			}

		}	

		if (!issues.isEmpty())
			Collections.sort(issues);
		
		return issues;
	}

	private ArrayList<MedicalIssue> populatePastMedicalHistory(Patient patient, int formId) {

		ArrayList<MedicalIssue> issues = new ArrayList<MedicalIssue>();

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(formId);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, false);

		ConceptService conceptService = Context.getConceptService();

		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList.add(conceptService.getConcept(162881));

		List<Obs> someObsList = obsService.getObservations(personList, encs,conceptList, null, null, null, null, null, null, null, null, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Set<Obs> members = obs.getGroupMembers();

			MedicalIssue medicalIssue = new MedicalIssue();
			medicalIssue.setEncounterId(obs.getEncounter().getEncounterId().intValue());
			issues.add(medicalIssue);
			medicalIssue.setPast(true);

		//	log.warn("medicalHistory obs: " + obs.getId()  + " " + (members!=null?members.size():0) + " " + obs.toString());

			for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {
				Obs memberObs = it2.next();
				Concept concept = memberObs.getConcept();
			//	log.warn(concept.getConceptId() + " " + concept.getName().toString() + " " + concept.toString());

				if (isConcept(concept, 162879)) {
					medicalIssue.setMedicalHistoryAdded(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (isConcept(concept, 159948)) {
					medicalIssue.setDiagnosisDate(memberObs.getValueDate());
					continue;
				}

				if (isConcept(concept, 162880)) {
					medicalIssue.setMedicalHistoryAddedText(memberObs.getValueText());
					continue;
				}

				if (isConcept(concept, 1729)) {
					medicalIssue.setStillPresent(memberObs.getValueCoded().getName().toString());
					continue;
				}

				if (isConcept(concept, 162959)) {
					medicalIssue.setType(memberObs.getValueCoded().getName().toString());
					continue;
				}


			}

		}	

		if (!issues.isEmpty())
			Collections.sort(issues);
		
		return issues;
	}

	private ArrayList<VisitNote> populateVisits(Patient patient) {

		ArrayList<VisitNote> notes = new ArrayList<VisitNote>();

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(2);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, false);

		ConceptService conceptService = Context.getConceptService();

		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList.add(conceptService.getConcept(162169));

		List<Obs> someObsList = obsService.getObservations(personList, encs, conceptList, null, null, null, null, null, null, null, null, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
		//	log.warn("VISIT: " + obs.getObsDatetime() + " " + obs.getEncounter().getEncounterType().getName() + " " + obs.getEncounter().getEncounterType().getId());

			VisitNote note = new VisitNote();
			note.setEncounterId(obs.getEncounter().getEncounterId().intValue());
			note.setTimestamp(obs.getObsDatetime());
			note.setVisitType(obs.getEncounter().getEncounterType().getId());			
			notes.add(note);
			
			Concept concept = obs.getConcept();
			if (isConcept(concept, 162169)) {
				note.setNote(obs.getValueText()); 
				continue;
			}
		
		}
		
		if (!notes.isEmpty())
			Collections.sort(notes);
	
		return notes;
	}
	
	public static boolean isConcept(Concept concept, Integer targetConceptId) {
		if (concept == null)
			return false;

		if ( targetConceptId == null)
			return false;

		return concept.getConceptId().equals(targetConceptId) ;

	}
	
	*/
	
}