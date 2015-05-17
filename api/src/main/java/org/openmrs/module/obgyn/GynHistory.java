package org.openmrs.module.obgyn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinTable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.springframework.format.annotation.DateTimeFormat;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class GynHistory extends BaseOpenmrsObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private int encounterId;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date lastPeriodDate;
	
	private Integer gynHistoryId;
	
	private Patient patient;
	
	private int menstruationDuration;
	private int menstruationCycle;
	
	private boolean intermenstrualBleeding;
	private String intermenstrualBleedingComment;
	
	
	private boolean postCoitalBleeding;
	private String postCoitalBleedingComment;

	private boolean postMenopausalBleeding;
	private String postMenopausalBleedingComment;
	
	private boolean hormoneReplacementTherapy;
	private Date hormoneReplacementTherapyDate;	
	private int hormoneReplacementTherapyDuration;	
	private String hormoneReplacementTherapyType;
	private String menopausalState;
	
	private boolean possiblePregnancy;
	private boolean claimsInfertility;
	private String claimsInfertilityComment;
	
	private boolean contraception;
	private String contraceptionComment;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date papTestDate;
	private String papTestComment;
	private String papTestResult;
	
	private String previousPapTestComment;

	private String gynaeHistory;
	private String generalHistory;
	private String surgicalHistory;
	
	private int cigarettesPerDay;
	private boolean stoppedSmoking;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date stoppedSmokingDate;
	private String otherAddictions;
	
	private int parity;
	private int gravida;
	
	private float weight;
	private float height;
	
	private HashMap<String, Surgery> surgeries;
	private ArrayList<MedicalIssue> medicalHistory;
	
	private String reasonForFirstVisit;
	private String medicationHistory;
	
	public String getPapTestResult() {
		return papTestResult;
	}

	public void setPapTestResult(String papTestResult) {
		this.papTestResult = papTestResult;
	}

	public Date getMammogramDate() {
		return mammogramDate;
	}

	public void setMammogramDate(Date mammogramDate) {
		this.mammogramDate = mammogramDate;
	}

	public String getMammogramComment() {
		return mammogramComment;
	}

	public void setMammogramComment(String mammogramComment) {
		this.mammogramComment = mammogramComment;
	}

	public String getMammogramResult() {
		return mammogramResult;
	}

	public void setMammogramResult(String mammogramResult) {
		this.mammogramResult = mammogramResult;
	}

	private int alcoholUnitsPerWeek;
	
	private String drugs;
	private String hepatitis;
	private String herpes;
	
	private boolean familyHistoryBreastCancer;
	private String familyHistoryBreastCancerComment;
	
	private boolean familyHistoryGynCancer;
	private String familyHistoryGynCancerComment;
	
	private String comment;
	private String bloodType;
	private boolean rhesus;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date mammogramDate;
	private String mammogramComment;
	private String mammogramResult;

	private int normalDeliveries;
	private int csections;
	private int tops;
	private int miscs;
	
	private int numberOfChildren;

	private String miscText;
	private String patientHistoryText;
	
	private Collection<Child> listOfChildren = new ArrayList<Child>();
	
	public Collection<Child> getListOfChildren() {
		return listOfChildren;
	}

	public void setListOfChildren(Collection<Child> listOfChildren) {
		this.listOfChildren = listOfChildren;
	}

	@Override
	public Integer getId() {		
		return gynHistoryId;
	}

	@Override
	public void setId(Integer arg0) {
		this.gynHistoryId = arg0;
		
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public Date getLastPeriodDate() {
		return lastPeriodDate;
	}

	public void setLastPeriodDate(Date lastPeriodDate) {
		this.lastPeriodDate = lastPeriodDate;
	}

	public int getMenstruationDuration() {
		return menstruationDuration;
	}

	public void setMenstruationDuration(int menstrualDuration) {
		this.menstruationDuration = menstrualDuration;
	}

	public int getMenstruationCycle() {
		return menstruationCycle;
	}

	public void setMenstruationCycle(int menstrualCycle) {
		this.menstruationCycle = menstrualCycle;
	}

	public boolean isIntermenstrualBleeding() {
		return intermenstrualBleeding;
	}

	public void setIntermenstrualBleeding(boolean intermenstrualBlood) {
		this.intermenstrualBleeding = intermenstrualBlood;
	}

	public String getYesNoIntermenstrualBleeding() {
		return getYesNo(this.intermenstrualBleeding);
	}
	
	public String getYesNoClaimsInfertility() {
		return getYesNo(this.claimsInfertility);
	}
	
	public String getIntermenstrualBleedingComment() {
		return intermenstrualBleedingComment;
	}

	public void setIntermenstrualBleedingComment(String intermenstrualBloodComment) {
		this.intermenstrualBleedingComment = intermenstrualBloodComment;
	}

	public boolean isPostCoitalBleeding() {
		return postCoitalBleeding;
	}

	public void setPostCoitalBleeding(boolean postCoitalBlood) {
		this.postCoitalBleeding = postCoitalBlood;
	}

	public String getYesNoPostCoitalBleeding() {
		return getYesNo(this.postCoitalBleeding);
	}

	
	public String getPostCoitalBleedingComment() {
		return postCoitalBleedingComment;
	}

	public void setPostCoitalBleedingComment(String postCoitalBloodComment) {
		this.postCoitalBleedingComment = postCoitalBloodComment;
	}

	public boolean isPostMenopausalBleeding() {
		return postMenopausalBleeding;
	}

	public void setPostMenopausalBleeding(boolean postMenopausalBlood) {
		this.postMenopausalBleeding = postMenopausalBlood;
	}

	public String getYesNoPostMenopausalBleeding() {
		return getYesNo(this.postMenopausalBleeding);
	}

	
	public String getPostMenopausalBleedingComment() {
		return postMenopausalBleedingComment;
	}

	public void setPostMenopausalBleedingComment(String postMenopausalBloodComment) {
		this.postMenopausalBleedingComment = postMenopausalBloodComment;
	}

	public boolean isHormoneReplacementTherapy() {
		return hormoneReplacementTherapy;
	}

	public void setHormoneReplacementTherapy(boolean hormoneReplacementTherapy) {
		this.hormoneReplacementTherapy = hormoneReplacementTherapy;
	}

	public String getYesNoHormoneReplacementTherapy() {
		return getYesNo(this.hormoneReplacementTherapy);
	}
	
	public int getHormoneReplacementTherapyDuration() {
		return hormoneReplacementTherapyDuration;
	}

	public void setHormoneReplacementTherapyDuration(int hormoneReplacementTherapyDuration) {
		this.hormoneReplacementTherapyDuration = hormoneReplacementTherapyDuration;
	}

	public String getHormoneReplacementTherapyType() {
		return hormoneReplacementTherapyType;
	}

	public void setHormoneReplacementTherapyType(String hormoneReplacementTherapyType) {
		this.hormoneReplacementTherapyType = hormoneReplacementTherapyType;
	}

	public boolean isPossiblePregnancy() {
		return possiblePregnancy;
	}

	public void setPossiblePregnancy(boolean possiblePregnancy) {
		this.possiblePregnancy = possiblePregnancy;
	}

	public String getYesNoPossiblePregnancy() {
		return getYesNo(this.possiblePregnancy);
	}

	
	public boolean isContraception() {
		return contraception;
	}

	public void setContraception(boolean contraception) {
		this.contraception = contraception;
	}

	public String getYesNoContraception() {
		return getYesNo(this.contraception);
	}

	
	public String getContraceptionComment() {
		return contraceptionComment;
	}

	public void setContraceptionComment(String contraceptionComment) {
		this.contraceptionComment = contraceptionComment;
	}

	public Date getPapTestDate() {
		return papTestDate;
	}

	public void setPapTestDate(Date lastPapTest) {
		this.papTestDate = lastPapTest;
	}

	public String getPapTestComment() {
		return papTestComment;
	}

	public void setPapTestComment(String lastPapTestComment) {
		this.papTestComment = lastPapTestComment;
	}

	public String getPreviousPapTestComment() {
		return previousPapTestComment;
	}

	public void setPreviousPapTestComment(String previousPapTestComment) {
		this.previousPapTestComment = previousPapTestComment;
	}

	public String getGynaeHistory() {
		return gynaeHistory;
	}

	public void setGynaeHistory(String gynaeHistory) {
		this.gynaeHistory = gynaeHistory;
	}

	public String getGeneralHistory() {
		return generalHistory;
	}

	public void setGeneralHistory(String generalHistory) {
		this.generalHistory = generalHistory;
	}

	public String getSurgicalHistory() {
		return surgicalHistory;
	}

	public void setSurgicalHistory(String surgicalHistory) {
		this.surgicalHistory = surgicalHistory;
	}

	public int getCigarettesPerDay() {
		return cigarettesPerDay;
	}

	public void setCigarettesPerDay(int cigarettesPerDay) {
		this.cigarettesPerDay = cigarettesPerDay;
	}

	public boolean isStoppedSmoking() {
		return stoppedSmoking;
	}

	public void setStoppedSmoking(boolean stoppedSmoking) {
		this.stoppedSmoking = stoppedSmoking;
	}
	
	public String getYesNoStoppedSmoking() {
		return getYesNo(this.stoppedSmoking);
	}


	public Date getStoppedSmokingDate() {
		return stoppedSmokingDate;
	}

	public void setStoppedSmokingDate(Date stoppedSmokingDate) {
		this.stoppedSmokingDate = stoppedSmokingDate;
	}

	public boolean isFamilyHistoryBreastCancer() {
		return familyHistoryBreastCancer;
	}

	public void setFamilyHistoryBreastCancer(boolean familyHistoryBreastCancer) {
		this.familyHistoryBreastCancer = familyHistoryBreastCancer;
	}

	public String getYesNoFamilyHistoryBreastCancer() {
		return getYesNo(this.familyHistoryBreastCancer);
	}

	public String getFamilyHistoryBreastCancerComment() {
		return familyHistoryBreastCancerComment;
	}

	public void setFamilyHistoryBreastCancerComment(String familyHistoryBreastCancerComment) {
		this.familyHistoryBreastCancerComment = familyHistoryBreastCancerComment;
	}

	public boolean isFamilyHistoryGynCancer() {
		return familyHistoryGynCancer;
	}

	public void setFamilyHistoryGynCancer(boolean familyHistoryGynCancer) {
		this.familyHistoryGynCancer = familyHistoryGynCancer;
	}

	public String getFamilyHistoryGynCancerComment() {
		return familyHistoryGynCancerComment;
	}

	public String getYesNoFamilyHistoryGynCancer() {
		return getYesNo(this.familyHistoryGynCancer);
	}

	
	public void setFamilyHistoryGynCancerComment(String familyHistoryGynCancerComment) {
		this.familyHistoryGynCancerComment = familyHistoryGynCancerComment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getMammographyDate() {
		return mammogramDate;
	}

	public void setMammographyDate(Date lastMammographyDate) {
		this.mammogramDate = lastMammographyDate;
	}

	public String getMammographyComment() {
		return mammogramComment;
	}

	public void setMammographyComment(String lastMammographyComment) {
		this.mammogramComment = lastMammographyComment;
	}

	public int getNormalDeliveries() {
		return normalDeliveries;
	}

	public void setNormalDeliveries(int normalDeliveries) {
		this.normalDeliveries = normalDeliveries;
	}

	public int getCsections() {
		return csections;
	}

	public void setCsections(int cSections) {
		this.csections = cSections;
	}

	public int getTops() {
		return tops;
	}

	public void setTops(int tops) {
		this.tops = tops;
	}

	public int getMiscs() {
		return miscs;
	}

	public void setMiscs(int miscs) {
		this.miscs = miscs;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}


	public Integer getGynHistoryId() {
		return gynHistoryId;
	}

	public void setGynHistoryId(Integer gynHistoryId) {
		this.gynHistoryId = gynHistoryId;
	}

	public int getAlcoholUnitsPerWeek() {
		return alcoholUnitsPerWeek;
	}

	public void setAlcoholUnitsPerWeek(int alcoholUnitsPerWeek) {
		this.alcoholUnitsPerWeek = alcoholUnitsPerWeek;
	}

	public String getDrugs() {
		return drugs;
	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	public String getHepatitis() {
		return hepatitis;
	}

	public void setHepatitis(String hepatitis) {
		this.hepatitis = hepatitis;
	}

	public String getHerpes() {
		return herpes;
	}

	public void setHerpes(String herpes) {
		this.herpes = herpes;
	}

	public String getYesNo(boolean b) {
		return b?  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.yes") :  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.no");
	}
	
	public void addChild(Child c) {
		c.setHistory(this);
		this.getListOfChildren().add(c);
	}

	public int getNumOfChildren() {
		return listOfChildren.size();
	}
	
	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String motherBloodType) {
		this.bloodType = motherBloodType;
	}

	public boolean isRhesus() {
		return rhesus;
	}

	public void setRhesus(boolean rhesus) {
		this.rhesus = rhesus;
	}

	public String getPlusMinusRhesus() {
		return rhesus ? "+" : "-";
	}

	public String getMenopausalState() {
		return menopausalState;
	}

	public void setMenopausalState(String menopausalState) {
		this.menopausalState = menopausalState;
	}

	public String getOtherAddictions() {
		return otherAddictions;
	}

	public void setOtherAddictions(String otherAddictions) {
		this.otherAddictions = otherAddictions;
	}

	public Date getHormoneReplacementTherapyDate() {
		return hormoneReplacementTherapyDate;
	}

	public void setHormoneReplacementTherapyDate(Date hormoneReplacementTherapyDate) {
		this.hormoneReplacementTherapyDate = hormoneReplacementTherapyDate;
	}

	public int getParity() {
		return parity;
	}

	public void setParity(int parity) {
		this.parity = parity;
	}

	public int getGravida() {
		return gravida;
	}

	public void setGravida(int gravida) {
		this.gravida = gravida;
	}
	public String getMenopausalStateString() {
		if (menopausalState == null)
			return "";
		
		if ("No menopause".equalsIgnoreCase(menopausalState))
			return Context.getMessageSourceService().getMessage("OBGyn7.History.list.column.menopausal_no");
		if ("Postsurgical menopause".equalsIgnoreCase(menopausalState))
			return Context.getMessageSourceService().getMessage("OBGyn7.History.list.column.menopausal_postsurgical");
		if ("Premature menopause".equalsIgnoreCase(menopausalState))
			return Context.getMessageSourceService().getMessage("OBGyn7.History.list.column.menopausal_premature");
		if ("Menopause".equalsIgnoreCase(menopausalState))
			return Context.getMessageSourceService().getMessage("OBGyn7.History.list.column.menopausal_normal");
		
		return "";
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public HashMap<String, Surgery> getSurgeries() {
		return surgeries;
	}

	public void setSurgeries(HashMap<String, Surgery> surgeries) {
		this.surgeries = surgeries;
	}

	public void addSurgery(String encounterId, Surgery surgery) {
		if (this.surgeries == null)
			this.surgeries = new HashMap<String, Surgery>();
		surgeries.put(encounterId,  surgery);
	}
	public ArrayList<MedicalIssue> getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(ArrayList<MedicalIssue> medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public boolean isClaimsInfertility() {
		return claimsInfertility;
	}

	public void setClaimsInfertility(boolean claimsInfertility) {
		this.claimsInfertility = claimsInfertility;
	}

	public String getClaimsInfertilityComment() {
		return claimsInfertilityComment;
	}

	public void setClaimsInfertilityComment(String claimsInfertilityComment) {
		this.claimsInfertilityComment = claimsInfertilityComment;
	}

	public int getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(int encounterId) {
		this.encounterId = encounterId;
	}

	public String getReasonForFirstVisit() {
		return reasonForFirstVisit;
	}

	public void setReasonForFirstVisit(String reasonForFirstVisit) {
		this.reasonForFirstVisit = reasonForFirstVisit;
	}

	public String getMedicationHistory() {
		return medicationHistory;
	}

	public void setMedicationHistory(String medicationHistory) {
		this.medicationHistory = medicationHistory;
	}

	public String getMiscText() {
		return miscText;
	}

	public void setMiscText(String miscText) {
		this.miscText = miscText;
	}

	public String getPatientHistoryText() {
		return patientHistoryText;
	}

	public void setPatientHistoryText(String patientHistoryText) {
		this.patientHistoryText = patientHistoryText;
	}
}
