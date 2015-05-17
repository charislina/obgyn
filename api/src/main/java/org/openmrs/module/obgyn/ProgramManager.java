package org.openmrs.module.obgyn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.Program;
import org.openmrs.ProgramWorkflow;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.states.BiochemicalMiscarriage;
import org.openmrs.module.obgyn.states.ClinicalMiscarriage;
import org.openmrs.module.obgyn.states.ClinicalMonitoring;
import org.openmrs.module.obgyn.states.ClinicalPregnancy;
import org.openmrs.module.obgyn.states.DownRegulation;
import org.openmrs.module.obgyn.states.EggCollection;
import org.openmrs.module.obgyn.states.EggDonation;
import org.openmrs.module.obgyn.states.EmbryoTransfer;
import org.openmrs.module.obgyn.states.Fertilization;
import org.openmrs.module.obgyn.states.FrozenEmbryoTransfer;
import org.openmrs.module.obgyn.states.IVFState;
import org.openmrs.module.obgyn.states.InfertilityAssessment;
import org.openmrs.module.obgyn.states.BiochemicalMonitoring;
import org.openmrs.module.obgyn.states.SpermCollection;
import org.openmrs.module.obgyn.states.Stimulation;
import org.openmrs.module.obgyn.states.ProgramAbandon;
import org.openmrs.module.obgyn.states.FreezeAllEmbryos;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;

public class ProgramManager {

	public final static String IVF_PROGRAM = "IVF Program";

	public final static String OWN_EGG_WORKFLOW = "Own-egg IVF";
	public final static String FROZEN_EMBRYO_WORKFLOW = "Frozen Embryo IVF";
	public final static String DONOR_EGG_WORKFLOW = "Donor egg IVF";

	public static final String NO_PREGNANCY_CONCEPT = "IVF No Pregnancy";

	protected final Log log = LogFactory.getLog(getClass());

	public List<PatientProgram> getPatientPrograms(Patient patient) {

		ProgramWorkflowService programService = Context.getProgramWorkflowService();
		List<PatientProgram> programs = programService.getPatientPrograms(patient, programService.getProgramByName(IVF_PROGRAM), null, null, null, null, false);
		
		return programs;
	}

	public Set<ProgramWorkflow> getWorkflows() {
		ProgramWorkflowService programService = Context.getProgramWorkflowService();
		Program program = programService.getProgramByName(IVF_PROGRAM);

		return program.getWorkflows();
	}

	public ProgramWorkflow getWorkflowByName(String name) {
		ProgramWorkflowService programService = Context.getProgramWorkflowService();
		Program program = programService.getProgramByName(IVF_PROGRAM);
		return program.getWorkflowByName(name);
	}

	public List<PatientState> getStatesInWorkflow(PatientProgram p, ProgramWorkflow w) {
		return p.statesInWorkflow(w, false);
	}

	public List<ProgramWorkflowState> getPossibleNextStates(PatientProgram p, ProgramWorkflow w) {
		return w.getPossibleNextStates(p);
	}

	public Set<PatientState> getPatientStates(PatientProgram p) {
		Set<PatientState> set = p.getStates();
		return set;
	}

	public Set<PatientState> getCurrentPatientStates(PatientProgram p) {
		Set<PatientState> set = p.getCurrentStates();
		return set;
	}

	public PatientState getCurrentPatientStateForWorkflow(PatientProgram p, ProgramWorkflow w) {
		PatientState state = p.getCurrentState(w);
		return state;
	}

	public ArrayList<IVFProgram> getIVFProgramsList(Patient patient) {

		ArrayList<IVFProgram> programsList = new ArrayList<IVFProgram>();

		ProgramWorkflowService programService = Context.getProgramWorkflowService();
		List<PatientProgram> programs = programService.getPatientPrograms(patient, programService.getProgramByName(IVF_PROGRAM), null, null, null, null, false);

		for (Iterator<PatientProgram> it = programs.iterator(); it.hasNext();) {

			PatientProgram p = it.next();

			IVFProgram program = new IVFProgram();

			program.setName(p.getProgram().getName());
			program.setEnrollementDate(p.getDateEnrolled());
			program.setCompleted(!p.getActive());
			program.setCompletionDate(p.getDateCompleted());
			program.setProgramId(p.getId());

			programsList.add(program);
		}

		if (programsList.size() > 1)
			Collections.sort(programsList);

	//	log.warn("getIVFProgramsList: " + programsList.size() + " patient " + patient.getId());

		return programsList;
	}

	public ArrayList<IVFWorkflow> getIVFWorkflowList() {
		ArrayList<IVFWorkflow> workflowsList = new ArrayList<IVFWorkflow>();

		Set<ProgramWorkflow> list = getWorkflows();
		for (Iterator<ProgramWorkflow> it = list.iterator(); it.hasNext();) {
			ProgramWorkflow w = it.next();
			IVFWorkflow workflow = new IVFWorkflow();
			workflow.setWorkflowId(w.getId());
			workflow.setWorkflowName(w.getConcept().getName().toString());
			workflowsList.add(workflow);

			Set<ProgramWorkflowState> set = w.getStates();
			ArrayList<IVFState> statesList = new ArrayList<IVFState>();
			for (Iterator<ProgramWorkflowState> it2 = set.iterator(); it2.hasNext();) {
				ProgramWorkflowState s = it2.next();
				String stateName = s.getConcept().getName().toString();

				IVFState state = getStateFromConceptName(stateName);
				if (state != null) {
					state.setStateId(s.getProgramWorkflowStateId().intValue());
					state.setWorkflowName(s.getProgramWorkflow().getConcept().getName().toString());
					statesList.add(state);
				}

			}// state

			if (!statesList.isEmpty()) {
				Collections.sort(statesList);
			}
			workflow.setStatesList(statesList);
		}

		return workflowsList;
	}

	public ArrayList<IVFState> getIVFStatesListForProgram(IVFProgram ivfProgram) {

		ProgramWorkflowService programService = Context.getProgramWorkflowService();

		PatientProgram p = programService.getPatientProgram(ivfProgram.getProgramId());
		if (p == null) {
			//log.warn("getIVFStatesListForProgram :  no program found " + ivfProgram.getProgramId());
			return null;
		}

		Set<PatientState> set = p.getStates();
		ArrayList<IVFState> statesList = new ArrayList<IVFState>();

		IVFWorkflow ivfWorkflow = null;

		for (Iterator<PatientState> it2 = set.iterator(); it2.hasNext();) {
			PatientState s = it2.next();
			String stateName = s.getState().getConcept().getName().toString();

			IVFState state = getStateFromConceptName(stateName);
			if (state == null) {
			//	log.warn("..no state for " + state.getConceptName());
				continue;
			}
			state.setStartDate(s.getStartDate());
			state.setEndDate(s.getEndDate());
			state.setStateId(s.getPatientStateId());
			state.setActive(s.getActive());
			state.setWorkflowName(s.getState().getProgramWorkflow().getConcept().getName().toString());
			state.setName(stateName);

			if (ivfWorkflow == null) {
				ProgramWorkflow programWorkflow = s.getState().getProgramWorkflow();
				ivfWorkflow = getIVFWorkflow(programWorkflow);
				ivfProgram.setWorkflowName(state.getWorkflowName());
			}

			if (state.isActive()) {
				ivfWorkflow.setCurrentState(state);
				if (ivfWorkflow.getNextStates() != null) {
					state.setNextStates(ivfWorkflow.getNextStates());
				}
			}

			statesList.add(state);

		}// state

		if (!statesList.isEmpty()) {
			Collections.sort(statesList);
		}

	//	log.warn("getIVFStatesListForProgram : " + statesList.size());

		return statesList;
	}

	public IVFState getIVFState(PatientState s) {
		
		String stateName = s.getState().getConcept().getName().toString();

		IVFState state = getStateFromConceptName(stateName);
		state.setStartDate(s.getStartDate());
		state.setEndDate(s.getEndDate());
		state.setStateId(s.getPatientStateId());
		state.setActive(s.getActive());
		return state;
	}

	public ArrayList<IVFState> getIVFStatesListForWorkflow(IVFWorkflow ivfWorkflow) {

		ProgramWorkflowService programService = Context.getProgramWorkflowService();

		ProgramWorkflow w = programService.getProgramByName(IVF_PROGRAM).getWorkflow(ivfWorkflow.getWorkflowId());
		if (w == null)
			return null;

		Set<ProgramWorkflowState> set = w.getStates();
		ArrayList<IVFState> statesList = new ArrayList<IVFState>();
		for (Iterator<ProgramWorkflowState> it2 = set.iterator(); it2.hasNext();) {
			ProgramWorkflowState s = it2.next();
			String stateName = s.getConcept().getName().toString();
			IVFState state = getStateFromConceptName(stateName);
			state.setStateId(s.getProgramWorkflowStateId().intValue());
			statesList.add(state);

		}// state

		if (!statesList.isEmpty()) {
			Collections.sort(statesList);
		}

		return statesList;
	}

	public IVFWorkflow getIVFWorkflow(ProgramWorkflow programWorkflow) {
		IVFWorkflow workflow = new IVFWorkflow();

		workflow.setWorkflowId(programWorkflow.getId());
		workflow.setWorkflowName(programWorkflow.getConcept().getName().toString());

		return workflow;
	}

	public boolean transitionToState(int patientProgramId, String workflowName, String newStateName, Date onDate) {

		ProgramWorkflowService programService = Context.getProgramWorkflowService();

		try {

			PatientProgram patientProgram = programService.getPatientProgram(patientProgramId);

			if (patientProgram.getActive()) {

				ProgramWorkflow programWorkflow = patientProgram.getProgram().getWorkflowByName(workflowName);
				IVFWorkflow ivfWorkflow = getIVFWorkflow(programWorkflow);

				PatientState lastState = patientProgram.getCurrentState(programWorkflow);
				if (lastState == null)
					log.warn("current state not found for w " + workflowName);

				ProgramWorkflowState programWorkflowState = programWorkflow.getStateByName(newStateName);
				if (programWorkflowState == null)
					log.warn("Not found state " + newStateName);

				IVFState lastIVFState = getIVFState(lastState);
				
				ivfWorkflow.setCurrentState(lastIVFState);
				int newStateType = getStateFromConceptName(newStateName).getType();
				if (!ivfWorkflow.isTransitionToStateAllowed(newStateType)) {
					log.warn("Transition not allowed");
					return false;
				}

				Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Athens"));
				Date d = cal.getTime();

				transitionToState(patientProgram, programWorkflowState, d);

				return true;

			} 

		} catch (IllegalArgumentException ex) {
			log.warn(ex);

		}

		return false;

	}

	public void transitionToState(PatientProgram patientProgram, ProgramWorkflowState s, Date onDate) {
		ProgramWorkflowService programService = Context.getProgramWorkflowService();

		try {

			if (patientProgram.getActive()) {

				Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Athens"));
				Date d = cal.getTime();

				try {

					patientProgram.transitionToState(s, d);

					programService.savePatientProgram(patientProgram);

				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
					;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void undoTransitionToState(PatientProgram p, ProgramWorkflowState s, Date onDate) {
		p.transitionToState(s, onDate);
	}

	public void enrollPatientInProgram(Patient patient, Integer workflowId, String initialStateName) {
		ProgramWorkflowService programService = Context.getProgramWorkflowService();

		PatientProgram patientProgram = new PatientProgram();
		patientProgram.setProgram(programService.getProgramByName(IVF_PROGRAM));
		patientProgram.setPatient(patient);
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Athens"));
		Date d = cal.getTime();
		patientProgram.setDateEnrolled(d);

		ProgramWorkflow programWorkflow = patientProgram.getProgram().getWorkflow(workflowId);

		ProgramWorkflowState programWorkflowState = programWorkflow.getStateByName(initialStateName);

		transitionToState(patientProgram, programWorkflowState, d);

		programService.savePatientProgram(patientProgram);

	}

	public static IVFState getStateFromConceptName(String name) {
		
		if (name == null)
			return null;

		if (name.equalsIgnoreCase(IVFState.INFERTILITY_ASESSMENT_CONCEPT))
			return new InfertilityAssessment();

		if (name.equalsIgnoreCase(IVFState.STIMULATION_CONCEPT))
			return new Stimulation();

		if (name.equalsIgnoreCase(IVFState.DOWN_REGULATION_CONCEPT))
			return new DownRegulation();

		if (name.equalsIgnoreCase(IVFState.EGG_COLLECTION_CONCEPT))
			return new EggCollection();

		if (name.equalsIgnoreCase(IVFState.EGG_DONATION_CONCEPT))
			return new EggDonation();

		if (name.equalsIgnoreCase(IVFState.SPERM_COLLECTION_CONCEPT))
			return new SpermCollection();

		if (name.equalsIgnoreCase(IVFState.FERTILIZATION_CONCEPT))
			return new Fertilization();

		if (name.equalsIgnoreCase(IVFState.EMBRYO_TRANSFER_CONCEPT))
			return new EmbryoTransfer();

		if (name.equalsIgnoreCase(IVFState.FROZEN_EMBRYO_TRANSFER_CONCEPT))
			return new FrozenEmbryoTransfer();

		if (name.equalsIgnoreCase(IVFState.BIOCHEMICAL_MONITORING_CONCEPT))
			return new BiochemicalMonitoring();

		if (name.equalsIgnoreCase(IVFState.CLINICAL_MONITORING_CONCEPT))
			return new ClinicalMonitoring();

		if (name.equalsIgnoreCase(IVFState.BIOCHEMICAL_MISCARRIAGE_CONCEPT))
			return new BiochemicalMiscarriage();

		if (name.equalsIgnoreCase(IVFState.CLINICAL_MISCARRIAGE_CONCEPT))
			return new ClinicalMiscarriage();

		if (name.equalsIgnoreCase(IVFState.CLINICAL_PREGNANCY_CONCEPT))
			return new ClinicalPregnancy();

		if (name.equalsIgnoreCase(IVFState.ABANDON_PROGRAM_CONCEPT))
			return new ProgramAbandon();
		
		if (name.equalsIgnoreCase(IVFState.FREEZE_ALL_EMBRYOS_CONCEPT))
			return new FreezeAllEmbryos();
		
		return null;

	}

}
