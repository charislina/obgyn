package org.openmrs.module.obgyn.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import java.util.Date;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openmrs.module.obgyn.*;
import org.openmrs.module.obgyn.Child;
import org.openmrs.module.obgyn.IVFProgram;
import org.openmrs.module.obgyn.Pregnancy;
import org.openmrs.module.obgyn.PregnancyMeasurement;
import org.openmrs.module.obgyn.states.*;
import org.openmrs.module.obgyn.api.OBGynService;

import org.openmrs.api.ObsService;
import org.openmrs.api.ConceptService;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.Form;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.ProgramWorkflow;
import org.openmrs.api.ProgramWorkflowService;

import org.openmrs.module.obgyn.ProgramManager;


public class DataManager {
	
	
	protected final Log log = LogFactory.getLog(getClass());
	public static final int INFERTILITY_ASSESSMENT_FORM_ID= 18;
	public static final int STIMULATION_FORM_ID = 22;
	public static final int STIMULATION_ASSESSMENT_FORM_ID = 19;

	public static final int DOWN_REGULATION_FORM_ID = 26;
	public static final int DOWN_REGULATION_ASSESSMENT_FORM_ID = 27;

	public static final int EGG_COLLECTION_FORM_ID = 23;
	public static final int SPERM_COLLECTION_FORM_ID = 28;
	public static final int EGG_DONATION_FORM_ID = 32;
	
	public static final int FERTILIZATION_FORM_ID = 29;
	public static final int DONOR_FERTILIZATION_FORM_ID = 33;

	public static final int EMBRYO_TRANSFER_FORM_ID = 20;
	public static final int FROZEN_EMBRYO_TRANSFER_FORM_ID = 21;
	public static final int EMBRYO_QUALITY_FORM_ID = 30;

	public static final int BIOCHEMICAL_MONITORING_FORM_ID = 25;
	public static final int CLINICAL_MONITORING_FORM_ID = 31;
	public static final int ULTRASOUND_ASSESSMENT_FORM_ID = 34;

	
	public HashMap<String,Pregnancy> getPregnancies(Patient patient, Date fromDate) {
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(13);
		List<Encounter> encounterList = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), null, null, null, null, false);

		ConceptService conceptService = Context.getConceptService();
		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList.add(conceptService.getConcept(163217));

		List<Obs> someObsList = obsService.getObservations(personList, encounterList,conceptList, null, null, null, null, null, null, null, null, false);
		HashMap<String,Pregnancy> pregnancyMap = new HashMap<String,Pregnancy>();
		
		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {

			Obs obs = it.next();
			
			if (fromDate != null && obs.getDateChanged().before(fromDate)) {
				log.debug("Ignoring obs ");
				continue;
			}
			
			Set<Obs> members = obs.getGroupMembers();	
			Pregnancy pregnancy = new Pregnancy();
			pregnancy.setEncounterId(obs.getEncounter().getEncounterId().intValue());		
			pregnancyMap.put(obs.getEncounter().getEncounterId().toString(), pregnancy);
			
			Date pregnancyStartDate = obs.getEncounter().getEncounterDatetime();
			Date pregnancyEndDate = null;
			
			for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {
				Obs memberObs = it2.next();
				Concept concept = memberObs.getConcept();

				if (Utils.isConcept(concept, 162937)) {
					pregnancy.setStatus(memberObs.getValueCoded().getName().toString());
					pregnancyEndDate = memberObs.getDateChanged();
					continue;
				}

				if (Utils.isConcept(concept, 162916)) {
					pregnancy.setComments(memberObs.getValueText());
					continue;
				}

				if (Utils.isConcept(concept, 1427)) {
					pregnancy.setLastPeriodDate(memberObs.getValueDate());
					continue;
				}

				if (Utils.isConcept(concept, 162938)) {
					pregnancy.setLastPeriodLimitDate(memberObs.getValueDate());
					continue;
				}

				if (Utils.isConcept(concept, 162945)) {
					pregnancy.setIvfTransferDate(memberObs.getValueDate());
					continue;
				}

				if (Utils.isConcept(concept, 162946)) {
					pregnancy.setOvulationDate(memberObs.getValueDate());
					continue;
				}

				if (Utils.isConcept(concept, 162943)) {
					pregnancy.setAmniocentesisDate(memberObs.getValueDate());
					continue;
				}

				if (Utils.isConcept(concept, 162944)) {
					pregnancy.setDidAmniocentesis(memberObs.getValueBoolean().booleanValue());
					continue;
				}

				if (Utils.isConcept(concept, 162949)) {
					pregnancy.setDidGlucoseToleranceTest(memberObs.getValueBoolean().booleanValue());
					continue;
				}

				if (Utils.isConcept(concept, 162950)) {
					pregnancy.setGlucoseToleranceDate(memberObs.getValueDate());
					continue;
				}

				if (Utils.isConcept(concept, 162951)) {
					pregnancy.setDidLevel2UltraSound(memberObs.getValueBoolean().booleanValue());
					continue;
				}

				if (Utils.isConcept(concept, 162952)) {
					pregnancy.setLevel2UltraSoundDate(memberObs.getValueDate());
					continue;
				}

				if (Utils.isConcept(concept, 300)) {
					String value = memberObs.getValueCoded().getName().toString();
					if (value != null) {
						boolean rhesusPositive = ("+".equals(value.substring(value.length()-2, value.length()-1)));
						pregnancy.setMotherRhesus(rhesusPositive);
						pregnancy.setMotherBloodType(value.substring(0, value.length()-2));
					}
					continue;
				}

				if (Utils.isConcept(concept, 162913)) {
					String value = memberObs.getValueCoded().getName().toString();
					if (value != null) {
						boolean rhesusPositive = ("+".equals(value.substring(value.length()-2, value.length()-1)));
						pregnancy.setFatherRhesus(rhesusPositive);
						pregnancy.setFatherBloodType(value.substring(0, value.length()-2));
					}
					continue;
				}


				if (Utils.isConcept(concept, 162914)) {
					pregnancy.setMotherThalassemiaCarrier(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept,  162915)) {
					pregnancy.setFatherThalassemiaCarrier(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 162906)) {
					pregnancy.setRedIGMStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 162907)) {
					pregnancy.setRedIGGStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 161523)) {
					pregnancy.setToxIGMStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 161522)) {
					pregnancy.setToxIGGStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 161527)) {
					pregnancy.setCmvIGMStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 161526)) {
					pregnancy.setCmvIGGStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 162908)) {
					pregnancy.setListIGMStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 162909)) {
					pregnancy.setListIGGStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 161528)) {
					pregnancy.setHsvIGMStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 161529)) {
					pregnancy.setHsvIGGStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 1169)) {
					pregnancy.setHivStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 162911)) {
					pregnancy.setMycStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 162912)) {
					pregnancy.setOurStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 299)) {
					pregnancy.setVdrlStr(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 162943)) {
					pregnancy.setAmniocentesisDate(memberObs.getValueDate());
					continue;
				}


				// top
				
				if (Utils.isConcept(concept, 163214)) {
					pregnancy.setTopDate(memberObs.getValueDate());
					continue;
				}

				if (Utils.isConcept(concept, 162947)) {
					pregnancy.setTerminationComment(memberObs.getValueText());
					continue;
				}

				// miscarriage

				if (Utils.isConcept(concept, 163213)) {
					pregnancy.setMiscarriageDate(memberObs.getValueDate());
					continue;
				}

				if (Utils.isConcept(concept, 162948)) {
						pregnancy.setMiscarriageComment(memberObs.getValueText());
						continue;
				}
				

				// delivery
				if (Utils.isConcept(concept, 1640)) {
					pregnancy.setAdmissionDate(memberObs.getValueDate());				
				}

				if (Utils.isConcept(concept, 1655)) {
					pregnancy.setAdmissionReason(memberObs.getValueCoded().getName().toString());				
				}

				if (Utils.isConcept(concept, 162917)) {
					pregnancy.setDeliveryDate(memberObs.getValueDate());				
				}
				if (Utils.isConcept(concept, 162918)) {
					pregnancy.setDeliveryWeek(memberObs.getValueNumeric().floatValue());				
				}
				
				if (Utils.isConcept(concept, 162919)) {
					pregnancy.setDeliveryDay(memberObs.getValueNumeric().intValue());				
				}
				

				// duration group
				if (Utils.isConcept(concept, 162922)) {
					Set<Obs> members2 = memberObs.getGroupMembers();

					for (Iterator<Obs> it3 = members2.iterator();it3.hasNext();)  {
						Obs memberObs2 = it3.next();
						Concept memberConcept = memberObs2.getConcept();

						if (Utils.isConcept(memberConcept, 1789)) {
							pregnancy.setDeliveryDuration(new Double(memberObs2.getValueNumeric()).floatValue());				
						}

						if (Utils.isConcept(memberConcept, 1732)) {
							pregnancy.setDeliveryDurationUnit(memberObs2.getValueCoded().getName().toString());				
						}
					}

				}


				if (Utils.isConcept(concept, 162953)) {
					pregnancy.setDeliveryLocation(memberObs.getValueCoded().getName().toString());				
				}

				if (Utils.isConcept(concept, 5630)) {
					pregnancy.setDeliveryMethod(memberObs.getValueCoded().getName().toString());				
				}

				if (Utils.isConcept(concept, 162962)) {
					pregnancy.setSykiou(memberObs.getValueBoolean());				
				}

				if (Utils.isConcept(concept, 162968)) {

					Set<Obs> members2 = memberObs.getGroupMembers();

					for (Iterator<Obs> it3 = members2.iterator();it3.hasNext();)  {
						Obs memberObs2 = it3.next();
						Concept memberConcept = memberObs2.getConcept();

						if (Utils.isConcept(memberConcept, 162967)) {
							pregnancy.setMyopiaDegrees(new Double(memberObs2.getValueNumeric()).intValue());						
						}

						if (Utils.isConcept(memberConcept, 162966)) {
							pregnancy.setMyopiaText(memberObs2.getValueText());				
						}
						if (Utils.isConcept(memberConcept, 162965)) {
							pregnancy.setMyopiaDate(memberObs2.getValueDate());				
						}
					}
				}

				
				
				if (Utils.isConcept(concept, 162927)) {
					pregnancy.setEpidural(memberObs.getValueBoolean());				
				}

				if (Utils.isConcept(concept, 162926)) {
					pregnancy.setLocalAnaisthesia(memberObs.getValueBoolean());				
				}

				if (Utils.isConcept(concept, 162925)) {
					pregnancy.setGeneralAnaisthesia(memberObs.getValueBoolean());				
				}

				if (Utils.isConcept(concept, 162924)) {
					pregnancy.setWindings(memberObs.getValueText()); 
					continue;
				}

				if (Utils.isConcept(concept, 1504)) {
					pregnancy.setApgar(new Double(memberObs.getValueNumeric()).intValue()); 
					continue;
				}


				// team group
				if (Utils.isConcept(concept, 162932)) {
					Set<Obs> members2 = memberObs.getGroupMembers();

					for (Iterator<Obs> it3 = members2.iterator();it3.hasNext();)  {
						Obs memberObs2 = it3.next();
						Concept memberConcept = memberObs2.getConcept();

						if (Utils.isConcept(memberConcept, 162929)) {
							pregnancy.setMidwife(memberObs2.getValueText()); 
							continue;
						}

						if (Utils.isConcept(memberConcept, 162930)) {
							pregnancy.setAssistant(memberObs2.getValueText()); 
							continue;
						}

						if (Utils.isConcept(memberConcept, 162931)) {
							pregnancy.setAnesthesist(memberObs2.getValueText()); 
							continue;
						}
					}
				}


				if (Utils.isConcept(concept, 162933)) {
					pregnancy.setDeliveryComment(memberObs.getValueText());
					continue;
				}

				// newborn group
				
						if (Utils.isConcept(concept, 163474)) {
							pregnancy.setChildWeight((new Double(memberObs.getValueNumeric())).intValue()); 
							continue;
						}

						if (Utils.isConcept(concept, 1587)) {
							pregnancy.setChildSex(memberObs.getValueText()); 
							continue;
						}

						if (Utils.isConcept(concept, 1571)) {
							pregnancy.setChildStillAlive(memberObs.getValueText()); 
							continue;
						}
					

			}

			// load measurements
			ArrayList<PregnancyMeasurement> measurements = getMeasurements(patient, fromDate, pregnancyStartDate, pregnancyEndDate);
			pregnancy.setMeasurements(measurements);		
			
		}

		return pregnancyMap;
	}

	public ArrayList<PregnancyMeasurement> getMeasurements(Patient patient, Date fromDate, Date startDate, Date endDate) {
		
		ArrayList<PregnancyMeasurement> list = new ArrayList<PregnancyMeasurement>();

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(12);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, startDate, endDate, Collections.singleton(form), null, null, null, null, false);

		if (encs == null || encs.isEmpty()) {
			return list;
		}

		ConceptService conceptService = Context.getConceptService();

		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList.add(conceptService.getConcept(163165));

		List<Obs> someObsList = obsService.getObservations(personList, encs, conceptList, null, null, null, null, null, null, fromDate, endDate, false);

		if (someObsList == null || someObsList.isEmpty()) {
			return list;
		}

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Set<Obs> members = obs.getGroupMembers();

			PregnancyMeasurement measurement = new PregnancyMeasurement();
			measurement.setDate(obs.getEncounter().getEncounterDatetime());
			measurement.setEncounterId(obs.getEncounter().getEncounterId().intValue());
			list.add(measurement);

			for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {

				Obs memberObs = it2.next();
				Concept concept = memberObs.getConcept();

				if (Utils.isConcept(concept, 162886)) {
					measurement.setWeek(new Double(memberObs.getValueNumeric()).shortValue());
				}

				if (Utils.isConcept(concept, 162887)) {
					measurement.setDay(new Double(memberObs.getValueNumeric()).shortValue());
				}

				if (Utils.isConcept(concept, 5089)) {
					measurement.setWeight(new Double(memberObs.getValueNumeric()).floatValue());
				}

				if (Utils.isConcept(concept, 5085)) {
					measurement.setSp(new Double(memberObs.getValueNumeric()).intValue());
				}

				if (Utils.isConcept(concept, 5086)) {
					measurement.setDp(new Double(memberObs.getValueNumeric()).intValue());
				}

				if (Utils.isConcept(concept, 1015)) {
					measurement.setBlood_ht(new Double(memberObs.getValueNumeric()).floatValue());
				}

				if (Utils.isConcept(concept, 21)) {
					measurement.setBlood_hb(new Double(memberObs.getValueNumeric()).floatValue());
				}

				if (Utils.isConcept(concept, 887)) {
					measurement.setBlood_gluc(new Double(memberObs.getValueNumeric()).floatValue());
				}

				if (Utils.isConcept(concept, 678)) {
					measurement.setBlood_wcc(new Double(memberObs.getValueNumeric()).floatValue());
				}

				if (Utils.isConcept(concept, 729)) {
					measurement.setBlood_plt(new Double(memberObs.getValueNumeric()).floatValue());
				}

				if (Utils.isConcept(concept, 162888)) {
					measurement.setUri_leu(new Double(memberObs.getValueNumeric()).intValue());
				}

				if (Utils.isConcept(concept, 162889)) {
					measurement.setUri_gluc(new Double(memberObs.getValueNumeric()).intValue());
				}

				if (Utils.isConcept(concept, 162890)) {
					measurement.setUri_hb(new Double(memberObs.getValueNumeric()).intValue());
				}

				if (Utils.isConcept(concept, 162891)) {
					measurement.setUri_pyo(new Double(memberObs.getValueNumeric()).intValue());
				}

				if (Utils.isConcept(concept, 162892)) {
					measurement.setUri_bact(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 162822)) {
					measurement.setUs_hc(new Double(memberObs.getValueNumeric()).floatValue());
					continue;
				}

				if (Utils.isConcept(concept, 162823)) {
					measurement.setUs_ac(new Double(memberObs.getValueNumeric()).floatValue());
					continue;
				}

				if (Utils.isConcept(concept, 162824)) {
					measurement.setUs_fl(new Double(memberObs.getValueNumeric()).floatValue());
					continue;
				}

				if (Utils.isConcept(concept, 162825)) {
					measurement.setUs_afi(new Double(memberObs.getValueNumeric()).intValue());
					continue;
				}

				if (Utils.isConcept(concept, 162826)) {
					measurement.setUs_efw(new Double(memberObs.getValueNumeric()).floatValue());
					continue;
				}

				if (Utils.isConcept(concept, 162899)) {
					measurement.setUs_plac(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 162905)) {
					measurement.setUs_projection(memberObs.getValueCoded().getName().toString()); 
					continue;
				}

				if (Utils.isConcept(concept, 162827)) {
					measurement.setUs_cxLength(new Double(memberObs.getValueNumeric()).floatValue());
					continue;
				}

			}
		}

		if (!list.isEmpty())
			Collections.sort(list);

		return list;
	}
	
	public List<IVFProgram> getIVFPrograms(Patient patient) {
		
		ProgramManager programManager = new ProgramManager();		
		List<IVFProgram> programs = programManager.getIVFProgramsList(patient);
		
		int count = 0;
		
		for (Iterator<IVFProgram> it = programs.iterator();it.hasNext();) {	
			
			IVFProgram p = it.next();
			ArrayList<IVFState> statesList = programManager.getIVFStatesListForProgram(p);
			p.setStates(statesList);
			
			Date completionDate = p.getCompletionDate();
			
			for (Iterator<IVFState> it2 = statesList.iterator();it2.hasNext();) {

					IVFState state = it2.next();
					switch(state.getType()) {

					case IVFState.INFERTILITY_ASSESSMENT:
						getInfertilityAssessment(patient, (InfertilityAssessment)state, completionDate);
						break;

					case IVFState.DOWN_REGULATION:
						getDownRegulation(patient, (DownRegulation)state, completionDate);
						break;

					case IVFState.STIMULATION:
						getStimulation(patient, (Stimulation)state, completionDate);						
						break;

					case IVFState.EGG_COLLECTION:
						getEggCollection(patient, (EggCollection) state, completionDate);
						break;

					case IVFState.EGG_DONATION:
						getEggDonation(patient, (EggDonation) state, completionDate);
						break;
						
					case IVFState.SPERM_COLLECTION:
						getSpermCollection(patient, (SpermCollection)state, completionDate);
						break;

					case IVFState.FERTILIZATION:
						getFertilization(patient, (Fertilization)state, completionDate);
						break;

					case IVFState.FERTILIZATION_AFTER_DONATION:
						getDonorFertilization(patient, (Fertilization)state, completionDate);
						break;
						
					case IVFState.EMBRYO_TRANSFER:
						getEmbryoTransfer(patient, (EmbryoTransfer)state, completionDate);					
						break;

					case IVFState.FROZEN_EMBRYO_TRANSFER:
						getFrozenEmbryoTransfer(patient, (FrozenEmbryoTransfer)state, completionDate);
						break;
						
					case IVFState.BIOCHEMICAL_MONITORING:
						getBiochemicalMonitoring(patient, (BiochemicalMonitoring)state, completionDate);
						break;

					case IVFState.CLINICAL_MONITORING:
						getClinicalMonitoring(patient, (ClinicalMonitoring)state, completionDate);
						break;

					default:
				}	
					
				count++;
				
			}//for

		}// for
		
		return programs;
	}

	private InfertilityAssessment getInfertilityAssessment(Patient patient, InfertilityAssessment assessment, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(INFERTILITY_ASSESSMENT_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, assessment.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);

		if (encounter == null)
			return null;

		assessment.setEncounterId(encounter.getEncounterId());
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);


		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null,  assessment.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			if (Utils.isConcept(concept, 162972)) {
				assessment.setInfertilityAssessment(obs.getValueCoded().getName().toString());
				continue;
			}

			if (Utils.isConcept(concept, 162973)) {
				assessment.setInfertilityAssessmentOtherText(obs.getValueText());
				continue;
			}

			if (Utils.isConcept(concept, 162977)) {
				assessment.setCervicalAssessment(obs.getValueCoded().getName().toString());
				continue;
			}

			if (Utils.isConcept(concept, 162978)) {
				assessment.setCervicalDilatationDate(obs.getValueDate());
				continue;
			}

			if (Utils.isConcept(concept, 162979)) {
				assessment.setCervicalDilatationText(obs.getValueText());
				continue;
			}

			if (Utils.isConcept(concept, 162980)) {
				assessment.setEmbryoTransferNote(obs.getValueText());
				continue;
			}

			if (Utils.isConcept(concept, 162989)) {
				assessment.setSpermFactor(obs.getValueCoded().getName().toString());
				continue;
			}

			if (Utils.isConcept(concept, 162991)) {
				assessment.setOvarianAssessmentDate(obs.getValueDate());
				continue;
			}

			if (Utils.isConcept(concept, 162992)) {
				assessment.setLeftOvaryAssessment(obs.getValueText());
				continue;
			}

			if (Utils.isConcept(concept, 162993)) {
				assessment.setRightOvaryAssessment(obs.getValueText());
				continue;
			}


		}

		return assessment;
	}


	private DownRegulation getDownRegulation(Patient patient, DownRegulation downRegulation, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(DOWN_REGULATION_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, downRegulation.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);


		if (encounter == null)
			return null;

		downRegulation.setEncounterId(encounter.getEncounterId());
		
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);


		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, downRegulation.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			if (Utils.isConcept(concept, 163144)) {
				downRegulation.setLastMenstrualPeriodBeforeTherapy(obs.getValueDate());
				continue;
			}

			if (Utils.isConcept(concept, 163093)) {
				downRegulation.setDownRegulationStartDate(obs.getValueDate());
				continue;
			}

			if (Utils.isConcept(concept, 163097)) {
				downRegulation.setDownRegulationMethod(obs.getValueCoded().getName().toString());
				continue;
			}

			if (Utils.isConcept(concept, 163010)) {
				downRegulation.setDrug(obs.getValueCoded().getName().toString());
				downRegulation.setAgonist(true);
				continue;
			}

			if (Utils.isConcept(concept, 163103)) {
				downRegulation.setDrug(obs.getValueCoded().getName().toString());
				downRegulation.setAgonist(false);
				continue;
			}		


			if (Utils.isConcept(concept, 163104)) {
				downRegulation.setLastMenstrualPeriodAfterTherapy(obs.getValueDate());
				continue;
			}

		}

		HashMap<String, DownregulationAssessment> map = getDownregulationAssessments(patient, downRegulation.getStartDate(), completionDate);
		downRegulation.setDownregulationAssessments(map);

		
		return downRegulation;
	}

	private Stimulation getStimulation(Patient patient, Stimulation stimulation, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(STIMULATION_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, stimulation.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);


		if (encounter == null)
			return null;

		stimulation.setEncounterId(encounter.getEncounterId());
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);


		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, stimulation.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			if (Utils.isConcept(concept, 163144)) {
				stimulation.setLastMenstrualPeriodBeforeTherapy(obs.getValueDate());
				continue;
			}
		
			if (Utils.isConcept(concept, 163070)) {
				stimulation.setStimulationTherapyStartDate(obs.getValueDate());
				continue;
			}

			if (Utils.isConcept(concept, 162997)) {
				stimulation.setStimulationProtocol(obs.getValueCoded().getName().toString());
				continue;
			}

			if (Utils.isConcept(concept, 163011)) {
				stimulation.setStimulationDrug(obs.getValueCoded().getName().toString());
				continue;
			}	

			if (Utils.isConcept(concept, 163071)) {
				stimulation.setStimulationTherapyNotes(obs.getValueText());
				continue;
			}	

			if (Utils.isConcept(concept, 163093)) {
				stimulation.setDownregulationStartDate(obs.getValueDate());
				continue;
			}
			
			if (Utils.isConcept(concept, 163167)) {
				stimulation.setAntagonistStartDate(obs.getValueDate());
				continue;
			}

			if (Utils.isConcept(concept, 163097)) {
				stimulation.setDownregulationMethod(obs.getValueCoded().getName().toString());
				continue;
			}

			if (Utils.isConcept(concept, 163010)) {
				stimulation.setDownRegulationdrug(obs.getValueCoded().getName().toString());
				stimulation.setAgonist(true);
				continue;
			}

			if (Utils.isConcept(concept, 163103)) {
				stimulation.setDownRegulationdrug(obs.getValueCoded().getName().toString());
				stimulation.setAgonist(false);
				continue;
			}		


			if (Utils.isConcept(concept, 163104)) {
				stimulation.setLastMenstrualPeriodDateAfterDRStart(obs.getValueDate());
				continue;
			}
		}

		String stimulationProtocol = stimulation.getStimulationProtocol();
		if (stimulationProtocol != null && stimulationProtocol.equals("Long Stimulation Protocol")) {
			HashMap<String, DownregulationAssessment> map = getDownregulationAssessments(patient, stimulation.getStartDate(), completionDate);
			stimulation.setDownregulationAssessments(map);
		}
		
		HashMap<String, StimulationAssessment> map = getStimulationAssessments(patient, stimulation, completionDate);
		stimulation.setStimulationAssessments(map);
		
		return stimulation;
	}

	private EggCollection getEggCollection(Patient patient, EggCollection eggCollection, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(EGG_COLLECTION_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, eggCollection.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);


		if (encounter == null)
			return null;

		eggCollection.setEncounterId(encounter.getEncounterId());
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);


		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, eggCollection.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			if (Utils.isConcept(concept, 163048)) {
				eggCollection.setCollectionDate(obs.getValueDate());
				continue;
			}

			if (Utils.isConcept(concept, 163169)) {
				eggCollection.setMedicalFacility(obs.getValueText());
				continue;
			}
			
			if (Utils.isConcept(concept, 163024)) {
				eggCollection.setCollectedEggs(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163110)) {
				eggCollection.setDifficultyIndex(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163113)) {
				eggCollection.setHasOHSS(obs.getValueBoolean().booleanValue());
				continue;
			}

			if (Utils.isConcept(concept, 163106)) {
				eggCollection.setCollectedEggsFromLeftOvary(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163106)) {
				eggCollection.setCollectedEggsFromLeftOvary(new Double(obs.getValueNumeric()).intValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163108)) {
				eggCollection.setCollectedEggsFromLeftOvaryText(obs.getValueText());
				continue;
			}

			if (Utils.isConcept(concept, 163109)) {
				eggCollection.setCollectedEggsFromRightOvaryText(obs.getValueText());
				continue;
			}
			
			if (Utils.isConcept(concept, 163112)) {
				eggCollection.setEggCollectionNote(obs.getValueText());
				continue;
			}	

			if (Utils.isConcept(concept, 163114)) {
				eggCollection.setFuturePlanningNote(obs.getValueText());
				continue;
			}	

			if (Utils.isConcept(concept, 163159)) {
				eggCollection.setEmbryologist(obs.getValueText());
				continue;
			}	
			
			if (Utils.isConcept(concept, 163160)) {
				eggCollection.setMidwife(obs.getValueText());
				continue;
			}	
			
			if (Utils.isConcept(concept, 163161)) {
				eggCollection.setAnesthetist(obs.getValueText());
				continue;
			}	
		}

		return eggCollection;
	}

	private EggDonation getEggDonation(Patient patient, EggDonation eggDonation, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(EGG_DONATION_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, eggDonation.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);


		if (encounter == null)
			return null;

		eggDonation.setEncounterId(encounter.getEncounterId());
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);


		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, eggDonation.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			if (Utils.isConcept(concept, 163151)) {
				eggDonation.setDoctor(obs.getValueText());
				continue;
			}
			
			if (Utils.isConcept(concept, 163158	)) {
				eggDonation.setMedicalFacility(obs.getValueText());
				continue;
			}

			if (Utils.isConcept(concept, 163150)) {
				eggDonation.setAge(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163153)) {
				eggDonation.setNationality(obs.getValueText());
				continue;
			}
			
			if (Utils.isConcept(concept, 163154)) {
				eggDonation.setPreviousDonations(new Double(obs.getValueNumeric()).intValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163155)) {
				eggDonation.setNumberOfPregnancies(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163156)) {
				eggDonation.setNumberOfChildren(new Double(obs.getValueNumeric()).intValue());
				continue;
			}	

			if (Utils.isConcept(concept, 163157)) {
				eggDonation.setNumberOfRecipients(new Double(obs.getValueNumeric()).intValue());
				continue;
			}	
			
			if (Utils.isConcept(concept, 163162)) {
				eggDonation.setEggsDonated(new Double(obs.getValueNumeric()).intValue());
				continue;
			}
			
			
		}

		return eggDonation;
	}
	
	private SpermCollection getSpermCollection(Patient patient, SpermCollection spermCollection, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(SPERM_COLLECTION_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, spermCollection.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);

		if (encounter == null)
			return null;

		spermCollection.setEncounterId(encounter.getEncounterId());
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);

		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, null, null, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			if (Utils.isConcept(concept, 163118)) {
				spermCollection.setSpermOrigin(obs.getValueCoded().getName().toString());
				continue;
			}	

			if (Utils.isConcept(concept, 163017)) {
				spermCollection.setSpermVolume(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163018)) {
				spermCollection.setSpermCount(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163019)) {
				spermCollection.setSpermMotility(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163020)) {
				spermCollection.setGradeAPercentage(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163021)) {
				spermCollection.setSpermMorphology(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163022)) {
				spermCollection.setSpermQualityText(obs.getValueText());
				continue;
			}			



		}

		return spermCollection;

	}


	private Fertilization getFertilization(Patient patient, Fertilization fertilization, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(FERTILIZATION_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, fertilization.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);

		if (encounter == null)
			return null;

		fertilization.setEncounterId(encounter.getEncounterId());
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);

		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, fertilization.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			//	log.warn("embryo: " + obs.getConcept().getConceptId() + " " + obs.getConcept().getName().toString() + " " + obs.getConcept().toString());


			if (Utils.isConcept(concept, 163024)) {
				fertilization.setCollectedEggs(new Double(obs.getValueNumeric()).intValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163050)) {
				fertilization.setEggsForICSI(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163170)) {
				fertilization.setMedicalFacility(obs.getValueText());
				continue;
			}

			
			if (Utils.isConcept(concept, 163025)) {
				fertilization.setEggsForIMSI(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163026)) {
				fertilization.setFertilizedEggs(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163030)) {
				fertilization.setFertilizationMethod(obs.getValueCoded().getName().toString());
				continue;
			}		
			
			if (Utils.isConcept(concept, 163183)) {
				fertilization.setUsedAssistedHatching(obs.getValueBoolean().booleanValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163184)) {
				fertilization.setUsedTimeLapse(obs.getValueBoolean().booleanValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163185)) {
				fertilization.setUsedBlastocyst(obs.getValueBoolean().booleanValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163033)) {
				fertilization.setPgdPgs(obs.getValueBoolean().booleanValue());
				continue;
			}

			if (Utils.isConcept(concept, 163127)) {
				fertilization.setEmbryosSuitableForBiopsy(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163129)) {
				fertilization.setHealthyEmbryosAfterScreening(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163128)) {
				fertilization.setScreeningReason(obs.getValueCoded().getName().toString());
				continue;
			}

		}

		return fertilization;

	}

	private Fertilization getDonorFertilization(Patient patient, Fertilization fertilization, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(DONOR_FERTILIZATION_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, fertilization.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);

		if (encounter == null)
			return null;

		fertilization.setEncounterId(encounter.getEncounterId());
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);

		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, fertilization.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			//	log.warn("embryo: " + obs.getConcept().getConceptId() + " " + obs.getConcept().getName().toString() + " " + obs.getConcept().toString());


			if (Utils.isConcept(concept, 163050)) {
				fertilization.setEggsForICSI(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163170)) {
				fertilization.setMedicalFacility(obs.getValueText());
				continue;
			}

			
			if (Utils.isConcept(concept, 163025)) {
				fertilization.setEggsForIMSI(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163026)) {
				fertilization.setFertilizedEggs(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163030)) {
				fertilization.setFertilizationMethod(obs.getValueCoded().getName().toString());
				continue;
			}		

			if (Utils.isConcept(concept, 163183)) {
				fertilization.setUsedAssistedHatching(obs.getValueBoolean().booleanValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163184)) {
				fertilization.setUsedTimeLapse(obs.getValueBoolean().booleanValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163185)) {
				fertilization.setUsedBlastocyst(obs.getValueBoolean().booleanValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163033)) {
				fertilization.setPgdPgs(obs.getValueBoolean().booleanValue());
				continue;
			}

			if (Utils.isConcept(concept, 163127)) {
				fertilization.setEmbryosSuitableForBiopsy(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163129)) {
				fertilization.setHealthyEmbryosAfterScreening(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163128)) {
				fertilization.setScreeningReason(obs.getValueCoded().getName().toString());
				continue;
			}

		}

		return fertilization;

	}
	
	private EmbryoTransfer getEmbryoTransfer(Patient patient, EmbryoTransfer embryoTransfer, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(EMBRYO_TRANSFER_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, embryoTransfer.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);

		if (encounter == null)
			return null;

		embryoTransfer.setEncounterId(encounter.getEncounterId());		
		embryoTransfer.setEmbryoFreezing(false);

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);

		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, embryoTransfer.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			//	log.warn("embryo: " + obs.getConcept().getConceptId() + " " + obs.getConcept().getName().toString() + " " + obs.getConcept().toString());

			if (Utils.isConcept(concept, 163049)) {
				embryoTransfer.setEmbryoTransferDate(obs.getValueDate());
				continue;
			}

			if (Utils.isConcept(concept, 163171)) {
				embryoTransfer.setMedicalFacility(obs.getValueText());
				continue;
			}
			
			if (Utils.isConcept(concept,163038)) {
				String dayStr = obs.getValueCoded().getName().toString();
				embryoTransfer.setEmbryoTransferDay(Utils.getDayFromDayStr(dayStr));
				continue;
			}	

			if (Utils.isConcept(concept,163039)) {
				String dayStr = obs.getValueCoded().getName().toString();
				embryoTransfer.setBlastocyteDay(Utils.getDayFromDayStr(dayStr));
				continue;
			}


			if (Utils.isConcept(concept, 163051)) {
				embryoTransfer.setTransferredEmbryos(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163052)) {
				embryoTransfer.setFrozenEmbryos(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept,163040)) {
				String dayStr = obs.getValueCoded().getName().toString();
				embryoTransfer.setEmbryoFreezingDay(Utils.getDayFromDayStr(dayStr));
				continue;
			}

			if (Utils.isConcept(concept, 163041)) {
				embryoTransfer.setCervicalOrientation(obs.getValueText());
				continue;
			}	

			if (Utils.isConcept(concept, 163125)) {
				embryoTransfer.setCatheter(obs.getValueCoded().getName().toString());
				continue;
			}	

			if (Utils.isConcept(concept, 163124)) {
				embryoTransfer.setCatheterOtherText(obs.getValueText());
				continue;
			}
			
			if (Utils.isConcept(concept, 163042)) {
				embryoTransfer.setUsedTweezers(obs.getValueBoolean().booleanValue());
				continue;
			}	

			if (Utils.isConcept(concept, 163043)) {
				embryoTransfer.setUsedUltrasound(obs.getValueBoolean().booleanValue());
				continue;
			}	

			if (Utils.isConcept(concept, 163053)) {
				embryoTransfer.setUsedAnesthesia(obs.getValueBoolean().booleanValue());
				continue;
			}	


			if (Utils.isConcept(concept, 163058)) {
				embryoTransfer.setEmbryoTransferDifficulty(obs.getValueText());
				continue;
			}	

			if (Utils.isConcept(concept, 163068)) {
				embryoTransfer.setEmbryoTransferNotes(obs.getValueText());
				continue;
			}

		}

		HashMap<Integer, BlastomereQuality> list = getEmbryoQuality(patient, encounter.getDateCreated());
		embryoTransfer.setEmbryoList(list);

		return embryoTransfer;

	}

	private FrozenEmbryoTransfer getFrozenEmbryoTransfer(Patient patient, FrozenEmbryoTransfer embryoTransfer, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(FROZEN_EMBRYO_TRANSFER_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, embryoTransfer.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);

		if (encounter == null)
			return null;

		embryoTransfer.setEncounterId(encounter.getEncounterId());		
		embryoTransfer.setEmbryoFreezing(false);

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);

		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, embryoTransfer.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

	
			if (Utils.isConcept(concept, 163049)) {
				embryoTransfer.setEmbryoTransferDate(obs.getValueDate());
				continue;
			}
			
			if (Utils.isConcept(concept, 163172)) {
				embryoTransfer.setMedicalFacility(obs.getValueText());
				continue;
			}
			
			if (Utils.isConcept(concept, 163190)) {
				embryoTransfer.setFrozenEmbryosBeforeThawing(new Double(obs.getValueNumeric()).intValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163188)) {
				embryoTransfer.setThawedEmbryos(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			if (Utils.isConcept(concept, 163189)) {
				embryoTransfer.setSuitableForTransfer(new Double(obs.getValueNumeric()).intValue());
				continue;
			}

			
			if (Utils.isConcept(concept, 163052)) {
				embryoTransfer.setRemainingFrozenEmbryos(new Double(obs.getValueNumeric()).intValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163051)) {
				embryoTransfer.setTransferredEmbryos(new Double(obs.getValueNumeric()).intValue());
				continue;
			}
		
			if (Utils.isConcept(concept, 163041)) {
				embryoTransfer.setCervicalOrientation(obs.getValueText());
				continue;
			}	

			if (Utils.isConcept(concept, 163125)) {
				embryoTransfer.setCatheter(obs.getValueCoded().getName().toString());
				continue;
			}	

			if (Utils.isConcept(concept, 163124)) {
				embryoTransfer.setCatheterOtherText(obs.getValueText());
				continue;
			}
			
			if (Utils.isConcept(concept, 163042)) {
				embryoTransfer.setUsedTweezers(obs.getValueBoolean().booleanValue());
				continue;
			}	

			if (Utils.isConcept(concept, 163043)) {
				embryoTransfer.setUsedUltrasound(obs.getValueBoolean().booleanValue());
				continue;
			}	

			if (Utils.isConcept(concept, 163053)) {
				embryoTransfer.setUsedAnesthesia(obs.getValueBoolean().booleanValue());
				continue;
			}	


			if (Utils.isConcept(concept, 163058)) {
				embryoTransfer.setEmbryoTransferDifficulty(obs.getValueText());
				continue;
			}	

			if (Utils.isConcept(concept, 163068)) {
				embryoTransfer.setEmbryoTransferNotes(obs.getValueText());
				continue;
			}

		}

		Form form2 = Context.getFormService().getForm(EMBRYO_TRANSFER_FORM_ID);
		ConceptService conceptService = Context.getConceptService();
		
		ArrayList<Concept> conceptList = new ArrayList<Concept>();
		conceptList.add(conceptService.getConcept(163040));
		
		List<Encounter> encs2 = Context.getEncounterService().getEncounters(patient, null, null, completionDate, Collections.singleton(form2), null, null, false);

		//get last enc
		if (encs2 != null && !encs2.isEmpty()) 
			encounter = encs2.get(encs.size() - 1);
		
		encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);
		
		someObsList = obsService.getObservations(personList, encounterList,conceptList, null, null, null, null, null, null, null, completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();
	
		

			if (Utils.isConcept(concept,163040)) {
				String dayStr = obs.getValueCoded().getName().toString();
				embryoTransfer.setEmbryoFreezingDay(Utils.getDayFromDayStr(dayStr));
				continue;
			}

		}
		
		HashMap<Integer, BlastomereQuality> list = getEmbryoQuality(patient, encounter.getDateCreated());
		embryoTransfer.setEmbryoList(list);

		return embryoTransfer;

	}
	
	private BiochemicalMonitoring getBiochemicalMonitoring(Patient patient, BiochemicalMonitoring postTransferMonitoring, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(BIOCHEMICAL_MONITORING_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, postTransferMonitoring.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);


		if (encounter == null)
			return null;

		postTransferMonitoring.setEncounterId(encounter.getEncounterId());
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);

		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, postTransferMonitoring.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			//	log.warn("embryo: " + obs.getConcept().getConceptId() + " " + obs.getConcept().getName().toString() + " " + obs.getConcept().toString());


			if (Utils.isConcept(concept, 163060)) {
				postTransferMonitoring.setbHCG1(new Double(obs.getValueNumeric()).intValue());
				continue;
			}	

			if (Utils.isConcept(concept, 163061)) {
				postTransferMonitoring.setbHCG2(new Double(obs.getValueNumeric()).intValue());
				continue;
			}	
			if (Utils.isConcept(concept, 163176)) {
				postTransferMonitoring.setbHCG3(new Double(obs.getValueNumeric()).intValue());
				continue;
			}
			
			if (Utils.isConcept(concept, 163147)) {
				postTransferMonitoring.setbHCG1Text(obs.getValueText());
				continue;
			}

			if (Utils.isConcept(concept, 163148)) {
				postTransferMonitoring.setbHCG2Text(obs.getValueText());
				continue;
			}
			
			if (Utils.isConcept(concept, 163177)) {
				postTransferMonitoring.setbHCG3Text(obs.getValueText());
				continue;
			}
			
			if (Utils.isConcept(concept, 163149)) {
				postTransferMonitoring.setNote(obs.getValueText());
				continue;
			}
			
			
			if (Utils.isConcept(concept, 163132)) {
				postTransferMonitoring.setbHCG1Date(obs.getValueDate());
				continue;
			}	

			if (Utils.isConcept(concept, 163133)) {
				postTransferMonitoring.setbHCG2Date(obs.getValueDate());
				continue;
			}	

			if (Utils.isConcept(concept, 163178)) {
				postTransferMonitoring.setbHCG3Date(obs.getValueDate());
				continue;
			}	

		}

		return postTransferMonitoring;

	}

	private ClinicalMonitoring getClinicalMonitoring(Patient patient, ClinicalMonitoring postTransferMonitoring, Date completionDate) {

		Encounter encounter = null;

		Form form = Context.getFormService().getForm(CLINICAL_MONITORING_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, postTransferMonitoring.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		if (encs != null && !encs.isEmpty()) 
			encounter = encs.get(encs.size() - 1);


		if (encounter == null)
			return null;

		postTransferMonitoring.setEncounterId(encounter.getEncounterId());
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		ArrayList<Encounter> encounterList = new ArrayList<Encounter>();
		encounterList.add(encounter);

		List<Obs> someObsList = obsService.getObservations(personList, encounterList,null, null, null, null, null, null, null, postTransferMonitoring.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();
			Concept concept = obs.getConcept();

			//	log.warn("embryo: " + obs.getConcept().getConceptId() + " " + obs.getConcept().getName().toString() + " " + obs.getConcept().toString());

			if (Utils.isConcept(concept, 163068)) {
				postTransferMonitoring.setNote(obs.getValueText());
				continue;
			}
						

		}

		HashMap<String, UltraSound> map = getUltrasoundAssessments(patient, postTransferMonitoring.getStartDate(), completionDate);
		postTransferMonitoring.setUltraSounds(map);
		
		return postTransferMonitoring;

	}

	
	private HashMap<String, UltraSound> getUltrasoundAssessments(Patient patient, Date startDate, Date completionDate) {
		HashMap<String, UltraSound> map = new HashMap<String, UltraSound>();

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(ULTRASOUND_ASSESSMENT_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, startDate, completionDate, Collections.singleton(form), null, null, null, null, false);

		ConceptService conceptService = Context.getConceptService();

		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList.add(conceptService.getConcept(163195));

		List<Obs> someObsList = obsService.getObservations(personList, encs,conceptList, null, null, null, null, null, null, startDate, completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();

			UltraSound assessment = new UltraSound();
			map.put(obs.getEncounter().getEncounterId()+"", assessment);

			Set<Obs> members = obs.getGroupMembers();
			for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {

				Obs memberObs = it2.next();
				Concept concept = memberObs.getConcept();
				//		log.warn("\t"+concept.getConceptId() + " " + concept.getName().toString() + " " + concept.toString());


				if (Utils.isConcept(concept, 163064)) {
					assessment.setDate(memberObs.getValueDate());
					continue;
				}

				if (Utils.isConcept(concept, 163066)) {
					assessment.setNumOfSacs(new Double(memberObs.getValueNumeric()).intValue());
					continue;
				}

				if (Utils.isConcept(concept, 163194)) {
					assessment.addHeartRatePerSac(new Double(memberObs.getValueNumeric()).intValue());
					continue;
				}

				if (Utils.isConcept(concept, 163062)) {
					assessment.setNote(memberObs.getValueText());
					continue;
				}				

			}

		}

		return map;
	}
		
	
	private HashMap<String, StimulationAssessment> getStimulationAssessments(Patient patient, Stimulation stimulation, Date completionDate) {
		HashMap<String, StimulationAssessment> map = new HashMap<String, StimulationAssessment>();

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(STIMULATION_ASSESSMENT_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, stimulation.getStartDate(), completionDate, Collections.singleton(form), null, null, null, null, false);

		ConceptService conceptService = Context.getConceptService();

		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList.add(conceptService.getConcept(163016));

		List<Obs> someObsList = obsService.getObservations(personList, encs,conceptList, null, null, null, null, null, null, stimulation.getStartDate(), completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();

			StimulationAssessment assessment = new StimulationAssessment();
			assessment.setAssessmentDate(obs.getEncounter().getEncounterDatetime());
			map.put(obs.getEncounter().getEncounterId()+"", assessment);

			Set<Obs> members = obs.getGroupMembers();
			for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {

				Obs memberObs = it2.next();
				Concept concept = memberObs.getConcept();
				//		log.warn("\t"+concept.getConceptId() + " " + concept.getName().toString() + " " + concept.toString());


				if (Utils.isConcept(concept, 163012)) {
					assessment.setStimulationDay(new Double(memberObs.getValueNumeric()).intValue());
					continue;
				}

				if (Utils.isConcept(concept, 163013)) {
					assessment.setEstradiolE2(new Double(memberObs.getValueNumeric()).intValue());
					continue;
				}

				if (Utils.isConcept(concept, 163168)) {
					assessment.setDrug(memberObs.getValueCoded().getName().toString());
					continue;
				}
				
				if (Utils.isConcept(concept, 163014)) {
					assessment.setDrugUnits(new Double(memberObs.getValueNumeric()).floatValue());
					continue;
				}

				if (Utils.isConcept(concept, 163015)) {
					assessment.setEndometriumLength(new Double(memberObs.getValueNumeric()).floatValue());
					continue;
				}

				if (Utils.isConcept(concept, 162992)) {
					assessment.setLeftOvaryAssessment(memberObs.getValueText());
					continue;
				}

				if (Utils.isConcept(concept, 162993)) {
					assessment.setRightOvaryAssessment(memberObs.getValueText());
					continue;
				}

			}

		}

		return map;
	}

	private HashMap<String, DownregulationAssessment> getDownregulationAssessments(Patient patient, Date startDate, Date completionDate) {
		HashMap<String, DownregulationAssessment> map = new HashMap<String, DownregulationAssessment>();

		log.warn("DOWN REG ASS: from " + startDate + " to " + completionDate);
		
		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(DOWN_REGULATION_ASSESSMENT_FORM_ID);
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, startDate, completionDate, Collections.singleton(form), null, null, null, null, false);

		ConceptService conceptService = Context.getConceptService();

		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList.add(conceptService.getConcept(163145));

		List<Obs> someObsList = obsService.getObservations(personList, encs,conceptList, null, null, null, null, null, null, startDate, completionDate, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();

			DownregulationAssessment assessment = new DownregulationAssessment();
			assessment.setAssessmentDate(obs.getEncounter().getEncounterDatetime());
			map.put(obs.getEncounter().getEncounterId()+"", assessment);

			Set<Obs> members = obs.getGroupMembers();
			for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {

				Obs memberObs = it2.next();
				Concept concept = memberObs.getConcept();
				//		log.warn("\t"+concept.getConceptId() + " " + concept.getName().toString() + " " + concept.toString());


				if (Utils.isConcept(concept, 163012)) {
					assessment.setDownregulationDay(new Double(memberObs.getValueNumeric()).intValue());
					continue;
				}

				if (Utils.isConcept(concept, 163013)) {
					assessment.setEstradiolE2(new Double(memberObs.getValueNumeric()).intValue());
					continue;
				}

				if (Utils.isConcept(concept, 163105)) {
					assessment.setEstrogenUnits(new Double(memberObs.getValueNumeric()).floatValue());
					continue;
				}

				if (Utils.isConcept(concept, 163015)) {
					assessment.setEndometriumLength(new Double(memberObs.getValueNumeric()).floatValue());
					continue;
				}

				if (Utils.isConcept(concept, 162992)) {
					assessment.setLeftOvaryAssessment(memberObs.getValueText());
					continue;
				}

				if (Utils.isConcept(concept, 162993)) {
					assessment.setRightOvaryAssessment(memberObs.getValueText());
					continue;
				}

			}

		}

		return map;
	}
			
	private HashMap<Integer, BlastomereQuality> getEmbryoQuality(Patient patient, Date fromDate) {
		HashMap<Integer, BlastomereQuality> map = new HashMap<Integer, BlastomereQuality>();

		ObsService obsService = Context.getObsService();
		ArrayList<Person> personList = new ArrayList<Person>();
		personList.add(patient);

		Form form = Context.getFormService().getForm(EMBRYO_QUALITY_FORM_ID);
		
		List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, fromDate, null, Collections.singleton(form), null, null, null, null, false);

		ConceptService conceptService = Context.getConceptService();

		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList.add(conceptService.getConcept(163054));

		List<Obs> someObsList = obsService.getObservations(personList, encs,conceptList, null, null, null, null, null, null, fromDate, null, false);

		for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
			Obs obs = it.next();

			BlastomereQuality q = new BlastomereQuality();
			map.put(obs.getEncounter().getEncounterId(), q);

			Set<Obs> members = obs.getGroupMembers();
			for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {				
				Obs memberObs = it2.next();
				Concept memberConcept = memberObs.getConcept();
				if (Utils.isConcept(memberConcept, 163045)) {
					q.setCellCount(new Double(memberObs.getValueNumeric()).intValue());
					continue;
				}			
				if (Utils.isConcept(memberConcept, 163046)) {
					q.setCellSizeDegree(new Double(memberObs.getValueNumeric()).floatValue());
					continue;
				}	
				if (Utils.isConcept(memberConcept, 163131)) {
					q.setQualityIndex(new Double(memberObs.getValueNumeric()).intValue());
					continue;
				}
			}

		}

		return map;
	}

	private HashMap<Integer, IVFProgram> getPatientPrograms(Patient patient, String programName) {

		HashMap<Integer, IVFProgram> programsMap = new HashMap<Integer, IVFProgram>();		

		ProgramWorkflowService programService = Context.getProgramWorkflowService();	
		List<PatientProgram> programs = programService.getPatientPrograms(patient, programService.getProgramByName(programName), null, null, null, null, false);
		ProgramManager programManager = new ProgramManager();
		
		for (Iterator<PatientProgram> it = programs.iterator(); it.hasNext();) {

			PatientProgram p = it.next();

			IVFProgram program = new IVFProgram();
			programsMap.put(p.getId(), program);
			program.setName("IVF Program");
			program.setEnrollementDate(p.getDateEnrolled());
			program.setCompleted(!p.getActive());
			program.setCompletionDate(p.getDateCompleted());
			
			program.setProgramId(p.getId());
			
			ArrayList<IVFState> statesList = new ArrayList<IVFState>();
			
			Set<PatientState> set = p.getStates();
			IVFWorkflow ivfWorkflow = null;
			for (Iterator<PatientState> it2 = set.iterator();it2.hasNext();) {
				PatientState s = it2.next();
				
				if (program.getWorkflowName() == null)  {
					ProgramWorkflow programWorkflow = s.getState().getProgramWorkflow();					
					ivfWorkflow = programManager.getIVFWorkflow(programWorkflow); 
					program.setWorkflowName(ivfWorkflow.getWorkflowName());				
				
				}
				String stateName = s.getState().getConcept().getName().toString();

				IVFState state = ProgramManager.getStateFromConceptName(stateName);
				state.setStartDate(s.getStartDate());
				state.setEndDate(s.getEndDate());				
				state.setStateId(s.getPatientStateId());
				
				state.setActive(s.getActive());
				
				ivfWorkflow.setCurrentState(state);
				if (s.getActive()) {
					log.warn("...adding next states ");
					state.setNextStates(ivfWorkflow.getNextStates());
				}
				
				statesList.add(state);

			//	log.warn("\tpatient state : " + " id: " + s.getPatientStateId() + " state: " +  s.getState().getConcept().getName().toString() + " active: " + s.getActive() + " : " + s.toString());

			}//state

			if (!statesList.isEmpty()) {
				Collections.sort(statesList);
				program.setStates(statesList);
			}
			
			
		}//program

		return programsMap;
	}

	
	public ArrayList<Child> getChildren(Patient patient) {
			ArrayList<Child> children = new ArrayList<Child>();

			ObsService obsService = Context.getObsService();
			ArrayList<Person> personList = new ArrayList<Person>();
			personList.add(patient);

			Form form = Context.getFormService().getForm(7);
			List<Encounter> encs = Context.getEncounterService().getEncounters(patient, null, null, null,  Collections.singleton(form), null, null, null, null, false);

			ConceptService conceptService = Context.getConceptService();

			List<Concept> conceptList = new ArrayList<Concept>();
			conceptList.add(conceptService.getConcept(162861));

			List<Obs> someObsList = obsService.getObservations(personList, encs, conceptList, null, null, null, null, null, null, null, null, false);
			
			for (Iterator<Obs> it = someObsList.iterator();it.hasNext();) {
				Obs obs = it.next();
				Set<Obs> members = obs.getGroupMembers();	
				
				Child child = new Child();
				child.setEncounterId(obs.getEncounter().getEncounterId().intValue());
				children.add(child);

				for (Iterator<Obs> it2 = members.iterator();it2.hasNext();)  {
					Obs memberObs = it2.next();
					Concept concept2 = memberObs.getConcept();

					if (Utils.isConcept(concept2, 1586)) {
						child.setName(memberObs.getValueText()); 
						continue;
					}

					if (Utils.isConcept(concept2, 1587)) {
						String str = memberObs.getValueCoded().getName().toString();
						child.setSex(str);
						continue;
					}

					if (Utils.isConcept(concept2, 162858)) {
						child.setFatherName(memberObs.getValueText()); 
						continue;
					}

					if (Utils.isConcept(concept2,  162859)) {
						child.setObstetrician(memberObs.getValueText()); 
						continue;
					}

					if (Utils.isConcept(concept2, 162857)) {
						child.setBirthDate(memberObs.getValueDate()); 
						continue;
					}

					if (Utils.isConcept(concept2, 1571)) {
						String str = memberObs.getValueCoded().getName().toString();
						if (str != null && str.equalsIgnoreCase("Yes"))
							child.setStillAlive(true);
						else
							child.setStillAlive(false);
							
						continue;
					}

				}
				
			}

			if (!children.isEmpty())
				Collections.sort(children);
			return children;

	}
		
	public List<Patient> getNewPatients(Date startDate) {
		OBGynService obgynService = Context.getService(OBGynService.class);
		
		return obgynService.getNewPatients(startDate);
	}
	
	public List<Patient> getExistingPatients(Date startDate) {
		OBGynService obgynService = Context.getService(OBGynService.class);
		
		return obgynService.getExistingPatients(startDate);
	}
	
}