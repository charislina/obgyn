/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.obgyn;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.PregnancyMeasurement;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */

public class Pregnancy extends BaseOpenmrsObject implements Serializable, Comparable<Pregnancy> {

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private int encounterId;

	private Integer pregnancyId;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date lastPeriodDate;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date lastPeriodLimitDate;
	
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date expectedDeliveryDate;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date expectedDeliveryLimitDate;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date ovulationDate;
	
	@XmlElement(name="circle")
	private int cycle;
	

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date ivfTransferDate;
	
	private String medication;
	private String comments;
	private String status;
	
	private String fatherFirstName;
	private String fatherLastName;
	
	private String fatherBloodType;
	private boolean fatherRhesus;
	private float fatherHB;
	private float fatherH2;
	private float fatherHBF;
	private float fatherTD;
	private boolean fatherCarrier;

	private String motherBloodType;
	private boolean motherRhesus;
	private float motherHB;
	private float motherH2;
	private float motherHBF;
	private float motherTD;
	private boolean motherCarrier;
    
	private String motherThalassemiaCarrier;
	private String fatherThalassemiaCarrier;
    
	private boolean redIGM;
	private boolean redIGG;
	private boolean toxIGM;
	private boolean toxIGG;
	
	
	private boolean cmvIGM;
	private boolean cmvIGG;
	private boolean listIGM;
	private boolean listIGG;
	private boolean hsvIGM;
	private boolean hsvIGG;
	
	@JsonIgnore
	private String redIGMStr;
	@JsonIgnore	
	private String redIGGStr;
	@JsonIgnore	
	private String toxIGMStr;
	@JsonIgnore	
	private String toxIGGStr;
	@JsonIgnore	
	private String cmvIGMStr;
	@JsonIgnore	
	private String cmvIGGStr;
	@JsonIgnore	
	private String listIGMStr;
	@JsonIgnore	
	private String listIGGStr;
	@JsonIgnore	
	private String hsvIGMStr;
	@JsonIgnore	
	private String hsvIGGStr;
	
	private boolean hiv;
	private boolean vdrl;
	private boolean myc;
	private boolean our;
	
	@JsonIgnore	
	private String hivStr;
	@JsonIgnore	
	private String vdrlStr;
	@JsonIgnore	
	private String mycStr;
	@JsonIgnore	
	private String ourStr;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date admissionDate;
	private String admissionReason;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date deliveryDate;
	private float deliveryWeek;
	private int deliveryDay;
	private float deliveryDuration;
	
	private String deliveryDurationUnit;
	
	private boolean cs;
	private boolean epidural;
	private boolean psych;
	private boolean generalAnaisthesia;
	private boolean localAnaisthesia;
	
	
	private String childSex;
	private String childPosition;
	private int childWeight;
	private String childStillAlive;
	
	private int apgar;
	private String windings;
	private boolean sykiou;
	private String hyster;
	
	private String midwife;
	private String assistant;
	private String anesthesist;
	private String breastfeeding;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date topDate;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date miscarriageDate;
	
	private String deliveryComment;
	private String terminationComment;
	private String miscarriageComment;
	
	private boolean closed;
	
	private boolean didAmniocentesis;
	private Date amniocentesisDate;

	private boolean didGlucoseToleranceTest;
	private Date glucoseToleranceDate;
	
	private boolean didLevel2UltraSound;
	private Date level2UltraSoundDate;
	
	private String deliveryMethod;
	private String deliveryLocation;
	
	private int myopiaDegrees;
	private String myopiaText;
	private Date myopiaDate;
	

	private Visit visit;

	private Patient patient;
	
	public Pregnancy() {}
	
	@JsonIgnore
	public String getChildStillAliveStr() {
		return getYesNoUnknown(childStillAlive);
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}


	@JsonIgnore	
	public String getRedIGMStr() {
		return redIGMStr;
	}

	public void setRedIGMStr(String redIGMStr) {
		this.redIGMStr = redIGMStr;
	}

	@JsonIgnore	
	public String getRedIGGStr() {
		return redIGGStr;
	}

	public void setRedIGGStr(String redIGGStr) {
		this.redIGGStr = redIGGStr;
	}

	@JsonIgnore	
	public String getToxIGMStr() {
		return toxIGMStr;
	}

	public void setToxIGMStr(String toxIGMStr) {
		this.toxIGMStr = toxIGMStr;
	}

	@JsonIgnore	
	public String getToxIGGStr() {
		return toxIGGStr;
	}

	@JsonIgnore	
	public void setToxIGGStr(String toxIGGStr) {
		this.toxIGGStr = toxIGGStr;
	}

	public String getCmvIGMStr() {
		return cmvIGMStr;
	}

	public void setCmvIGMStr(String cmvIGMStr) {
		this.cmvIGMStr = cmvIGMStr;
	}

	public String getCmvIGGStr() {
		return cmvIGGStr;
	}

	public void setCmvIGGStr(String cmvIGGStr) {
		this.cmvIGGStr = cmvIGGStr;
	}

	public String getListIGMStr() {
		return listIGMStr;
	}

	public void setListIGMStr(String listIGMStr) {
		this.listIGMStr = listIGMStr;
	}

	public String getListIGGStr() {
		return listIGGStr;
	}

	public void setListIGGStr(String listIGGStr) {
		this.listIGGStr = listIGGStr;
	}

	public String getHsvIGMStr() {
		return hsvIGMStr;
	}

	public void setHsvIGMStr(String hsvIGMStr) {
		this.hsvIGMStr = hsvIGMStr;
	}

	public String getHsvIGGStr() {
		return hsvIGGStr;
	}

	public void setHsvIGGStr(String hsvIGGStr) {
		this.hsvIGGStr = hsvIGGStr;
	}


	
	public String getDeliveryDurationUnit() {
		return deliveryDurationUnit;
	}

	public void setDeliveryDurationUnit(String deliveryDurationUnit) {
		this.deliveryDurationUnit = deliveryDurationUnit;
	}

	public String getHivStr() {
		return hivStr;
	}

	public void setHivStr(String hivStr) {
		this.hivStr = hivStr;
	}

	public String getVdrlStr() {
		return vdrlStr;
	}

	public void setVdrlStr(String vdrlStr) {
		this.vdrlStr = vdrlStr;
	}

	public String getMycStr() {
		return mycStr;
	}

	public void setMycStr(String mycStr) {
		this.mycStr = mycStr;
	}

	public String getOurStr() {
		return ourStr;
	}

	public void setOurStr(String ourStr) {
		this.ourStr = ourStr;
	}
	
	
	public boolean getDidAmniocentesis() {
		return didAmniocentesis;
	}

	public void setDidAmniocentesis(boolean didAmniocentesis) {
		this.didAmniocentesis = didAmniocentesis;
	}

	public Date getAmniocentesisDate() {
		return amniocentesisDate;
	}

	public void setAmniocentesisDate(Date amniocentesisDate) {
		this.amniocentesisDate = amniocentesisDate;
	}

	public boolean getDidGlucoseToleranceTest() {
		return didGlucoseToleranceTest;
	}

	public void setDidGlucoseToleranceTest(boolean didGlucoseToleranceTest) {
		this.didGlucoseToleranceTest = didGlucoseToleranceTest;
	}

	public Date getGlucoseToleranceDate() {
		return glucoseToleranceDate;
	}

	public void setGlucoseToleranceDate(Date glucoseToleranceDate) {
		this.glucoseToleranceDate = glucoseToleranceDate;
	}

	public boolean getDidLevel2UltraSound() {
		return didLevel2UltraSound;
	}

	public void setDidLevel2UltraSound(boolean didLevel2UltraSound) {
		this.didLevel2UltraSound = didLevel2UltraSound;
	}

	public Date getLevel2UltraSoundDate() {
		return level2UltraSoundDate;
	}

	public void setLevel2UltraSoundDate(Date level2UltraSoundDate) {
		this.level2UltraSoundDate = level2UltraSoundDate;
	}
	
	
	public String getPlusMinusMotherRhesus() {
		return motherRhesus ? "+" : "-";
	}
	
	public String getPlusMinusMotherCarrier() {
		return getPlusMinusUndefinedStr(motherThalassemiaCarrier);
	}
	
	public String getPlusMinusFatherRhesus() {
		return fatherRhesus ? "+" : "-";
	}
	
	public String getPlusMinusFatherCarrier() {
		return getPlusMinusUndefinedStr(fatherThalassemiaCarrier);
	}
	
	public String getPlusMinusRedIGM() {
		return getPlusMinusUndefinedStr(redIGMStr);
	}
	public String getPlusMinusRedIGG() {
		return getPlusMinusUndefinedStr(redIGGStr);
	}
	public String getPlusMinusToxIGM() {
		return getPlusMinusUndefinedStr(toxIGMStr);
	}
	public String getPlusMinusToxIGG() {
		return getPlusMinusUndefinedStr(toxIGGStr);
	}
	public String getPlusMinusCmvIGG() {
		return getPlusMinusUndefinedStr(cmvIGMStr);
	}
	public String getPlusMinusCmvIGM() {
		return getPlusMinusUndefinedStr(cmvIGMStr);
	}
	public String getPlusMinusListIGM() {
		return getPlusMinusUndefinedStr(listIGMStr);
	}
	public String getPlusMinusListIGG() {
		return getPlusMinusUndefinedStr(listIGGStr);
	}
	public String getPlusMinusHsvIGM() {
		return getPlusMinusUndefinedStr(hsvIGMStr);
	}
	public String getPlusMinusHsvIGG() {
		return getPlusMinusUndefinedStr(hsvIGGStr);
	}
	public String getPlusMinusHiv() {
		return getPlusMinusUndefinedStr(hivStr);
	}
	
	public String getPlusMinusVdrl() {
		return getPlusMinusUndefinedStr(vdrlStr);
	}
	public String getPlusMinusMyc() {
		return getPlusMinusUndefinedStr(mycStr);
	}
	public String getPlusMinusOur() {
		return getPlusMinusUndefinedStr(ourStr);
	}
	
	public String getPlusMinusUndefinedStr(String value) {
		if (value == null || value.equalsIgnoreCase("NEGATIVE") || value.equalsIgnoreCase("NON-REACTIVE"))
			return "-";
		return ( value.equalsIgnoreCase("POSITIVE") || value.equalsIgnoreCase("REACTIVE")? "+" : "?");
	}

	public String getYesNoUnknown(String answer) {
		
		if (answer == null || answer.equalsIgnoreCase("NO"))
			return  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.no");
		
		return (answer.equalsIgnoreCase("YES")?  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.yes") :  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.unknown"));
	}
	
	
	public String getYesNoCs() {
		return cs?  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.yes") :  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.no");
	}
	
	public String getYesNoEpidural() {
		return epidural?  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.yes") :  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.no");
	}
	
	public String getYesNoPsych() {
		return psych ?  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.yes") :  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.no");
	}
	
	@JsonIgnore	
	public String getYesNoGeneralAnaisthesia() {
		return generalAnaisthesia ? Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.yes") :  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.no");
	}

	@JsonIgnore	
	public String getYesNoLocalAnaisthesia() {
		return localAnaisthesia ? Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.yes") :  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.no");
	}
	
	@JsonIgnore	
	public String getYesNoSykiou() {
		return sykiou ?  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.yes") :  Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.no");
	}
	
	public boolean isRedIGM() {
		return redIGM;
	}

	public void setRedIGM(boolean redIGM) {
		this.redIGM = redIGM;
	}

	public boolean isRedIGG() {
		return redIGG;
	}

	public void setRedIGG(boolean redIGG) {
		this.redIGG = redIGG;
	}

	public boolean isToxIGM() {
		return toxIGM;
	}

	public void setToxIGM(boolean toxIGM) {
		this.toxIGM = toxIGM;
	}

	public boolean isToxIGG() {
		return toxIGG;
	}

	public void setToxIGG(boolean toxIGG) {
		this.toxIGG = toxIGG;
	}

	public boolean isCmvIGM() {
		return cmvIGM;
	}

	public void setCmvIGM(boolean cmvIGM) {
		this.cmvIGM = cmvIGM;
	}

	public boolean isCmvIGG() {
		return cmvIGG;
	}

	public void setCmvIGG(boolean cmvIGG) {
		this.cmvIGG = cmvIGG;
	}

	public boolean isListIGM() {
		return listIGM;
	}

	public void setListIGM(boolean listIGM) {
		this.listIGM = listIGM;
	}

	public boolean isListIGG() {
		return listIGG;
	}

	public void setListIGG(boolean listIGG) {
		this.listIGG = listIGG;
	}

	public boolean isHsvIGM() {
		return hsvIGM;
	}

	public void setHsvIGM(boolean hsvIGM) {
		this.hsvIGM = hsvIGM;
	}

	public boolean isHsvIGG() {
		return hsvIGG;
	}

	public void setHsvIGG(boolean hsvIGG) {
		this.hsvIGG = hsvIGG;
	}


	@OrderBy("timestamp DESC")
	private List<PregnancyMeasurement> measurements = new ArrayList<PregnancyMeasurement>();

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}


	public Integer getId() {
		return getPregnancyId();
	}

	public void setId(Integer id) {
		setPregnancyId(id);
	}
	
	public Date getLastPeriodDate() {
		return lastPeriodDate;
	}

	public void setLastPeriodDate(Date lastPeriodDate) {
		this.lastPeriodDate = lastPeriodDate;
	}

	public Date getLastPeriodLimitDate() {
		return lastPeriodLimitDate;
	}

	public void setLastPeriodLimitDate(Date lastPeriodLimitDate) {
		this.lastPeriodLimitDate = lastPeriodLimitDate;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public Date getExpectedDeliveryLimitDate() {
		return expectedDeliveryLimitDate;
	}

	public void setExpectedDeliveryLimitDate(Date expectedDeliveryLimitDate) {
		this.expectedDeliveryLimitDate = expectedDeliveryLimitDate;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusString() {
		
		if (status == null)
			return "";
		
		if (status.equalsIgnoreCase("Active Status"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.column.active");
		
		if (status.equalsIgnoreCase("TOP Status"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.column.terminated");
		
		if (status.equalsIgnoreCase("Delivery Status"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.column.delivery");

		if (status.equalsIgnoreCase("Miscarriage Status"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.column.miscarriage");
		
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getFatherFirstName() {
		return fatherFirstName;
	}

	public void setFatherFirstName(String fatherFirstName) {
		this.fatherFirstName = fatherFirstName;
	}

	public String getFatherLastName() {
		return fatherLastName;
	}

	public void setFatherLastName(String fatherLastName) {
		this.fatherLastName = fatherLastName;
	}

	public String getFatherBloodType() {
		return fatherBloodType;
	}

	public void setFatherBloodType(String fatherBloodType) {
		this.fatherBloodType = fatherBloodType;
	}

	public boolean getFatherRhesus() {
		return fatherRhesus;
	}

	public void setFatherRhesus(boolean fatherRhesus) {
		this.fatherRhesus = fatherRhesus;
	}

	public float getFatherHB() {
		return fatherHB;
	}

	public void setFatherHB(float fatherHB) {
		this.fatherHB = fatherHB;
	}

	public float getFatherH2() {
		return fatherH2;
	}

	public void setFatherH2(float fatherH2) {
		this.fatherH2 = fatherH2;
	}

	public float getFatherHBF() {
		return fatherHBF;
	}

	public void setFatherHBF(float fatherHBF) {
		this.fatherHBF = fatherHBF;
	}

	public float getFatherTD() {
		return fatherTD;
	}

	public void setFatherTD(float fatherTD) {
		this.fatherTD = fatherTD;
	}

	public boolean getFatherCarrier() {
		return fatherCarrier;
	}

	public void setFatherCarrier(boolean fatherCarrier) {
		this.fatherCarrier = fatherCarrier;
	}

	public List<PregnancyMeasurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<PregnancyMeasurement> measurements) {
		this.measurements = measurements;
	}

	public Integer getPregnancyId() {
		return pregnancyId;
	}

	public void setPregnancyId(Integer pregnancyId) {
		this.pregnancyId = pregnancyId;
	}
	
	public void addMeasurement(PregnancyMeasurement m) {
		m.setPregnancy(this);
		this.getMeasurements().add(m);
	}
	
	@JsonIgnore
	private String getDateString(Date date, String format) {
		if (date == null)
			return "";
		
		SimpleDateFormat d = new SimpleDateFormat(format);
		return d.format(date);
	}

	@JsonIgnore
	public String getAmniocentesisDateString() {
		return getDateString(amniocentesisDate, "dd/MM/yyyy");
	}

	@JsonIgnore
	public String getLevel2UltraSoundDateString() {
		return getDateString(level2UltraSoundDate, "dd/MM/yyyy");
	}

	@JsonIgnore
	public String getGlucoseToleranceDateString() {
		return getDateString(glucoseToleranceDate,"dd/MM/yyyy");
	}
	
	@JsonIgnore
	public String getExpectedDeliveryDateString() {
		return getDateString(expectedDeliveryDate,"yyyy-MM-dd");
	}
	
	@JsonIgnore
	public String getLastPeriodDateString() {
		return getDateString(lastPeriodDate,"yyyy-MM-dd");
	}

	@JsonIgnore
	public String getLastPeriodLimitDateString() {
		return getDateString(lastPeriodLimitDate,"yyyy-MM-dd");
	}
	
	public String getMotherBloodType() {
		return motherBloodType;
	}
	public void setMotherBloodType(String motherBloodType) {
		this.motherBloodType = motherBloodType;
	}
	public boolean getMotherRhesus() {
		return motherRhesus;
	}
	public void setMotherRhesus(boolean motherRhesus) {
		this.motherRhesus = motherRhesus;
	}
	public float getMotherHB() {
		return motherHB;
	}
	public void setMotherHB(float motherHB) {
		this.motherHB = motherHB;
	}
	public float getMotherH2() {
		return motherH2;
	}
	public void setMotherH2(float motherH2) {
		this.motherH2 = motherH2;
	}
	public float getMotherHBF() {
		return motherHBF;
	}
	public void setMotherHBF(float motherHBF) {
		this.motherHBF = motherHBF;
	}
	public float getMotherTD() {
		return motherTD;
	}
	public void setMotherTD(float motherTD) {
		this.motherTD = motherTD;
	}
	public boolean getMotherCarrier() {
		return motherCarrier;
	}
	public void setMotherCarrier(boolean motherCarrier) {
		this.motherCarrier = motherCarrier;
	}

	public Date getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	@JsonIgnore
	public String getDeliveryDateString() {
		return getDateString(deliveryDate, "yyyy-MM-dd");
	}
	
	public float getDeliveryWeek() {
		return deliveryWeek;
	}
	public void setDeliveryWeek(float deliveryWeek) {
		this.deliveryWeek = deliveryWeek;
	}
	public float getDeliveryDuration() {
		return deliveryDuration;
	}
	public void setDeliveryDuration(float deliveryDuration) {
		this.deliveryDuration = deliveryDuration;
	}
	public boolean isCs() {
		return cs;
	}
	public void setCs(boolean cS) {
		this.cs = cS;
	}
	public boolean isEpidural() {
		return epidural;
	}
	public void setEpidural(boolean epidural) {
		this.epidural = epidural;
	}
	public boolean isPsych() {
		return psych;
	}
	public void setPsych(boolean psych) {
		this.psych = psych;
	}
	public boolean isGeneralAnaisthesia() {
		return generalAnaisthesia;
	}
	public void setGeneralAnaisthesia(boolean generalAnaisthesia) {
		this.generalAnaisthesia = generalAnaisthesia;
	}

	public String getChildSex() {
		return childSex;
	}
	
	@JsonIgnore
	public String getChildSexString() {
		if (childSex == null)
			return "";
		
		if (childSex.equalsIgnoreCase("FEMALE GENDER"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.female");

		if (childSex.equalsIgnoreCase("MALE GENDER"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.male");

		return childSex;
	}
	
	public void setChildSex(String childSex) {
		this.childSex = childSex;
	}
	public String getChildPosition() {
		return childPosition;
	}
	public void setChildPosition(String childPosition) {
		this.childPosition = childPosition;
	}
	public int getChildWeight() {
		return childWeight;
	}
	public void setChildWeight(int childWeight) {
		this.childWeight = childWeight;
	}

	public boolean isSykiou() {
		return sykiou;
	}
	public void setSykiou(boolean sykiou) {
		this.sykiou = sykiou;
	}
	public String getDeliveryComment() {
		return deliveryComment;
	}
	public void setDeliveryComment(String deliveryComment) {
		this.deliveryComment = deliveryComment;
	}
	public String getAdmissionReason() {
		return admissionReason;
	}
	public void setAdmissionReason(String admissionReason) {
		this.admissionReason = admissionReason;
	}

	public String getHyster() {
		return hyster;
	}

	public void setHyster(String hyster) {
		this.hyster = hyster;
	}

	public int getDeliveryDay() {
		return deliveryDay;
	}

	public void setDeliveryDay(int deliveryDay) {
		this.deliveryDay = deliveryDay;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getTerminationComment() {
		return terminationComment;
	}

	public void setTerminationComment(String terminationComment) {
		this.terminationComment = terminationComment;
	}

	public boolean isHiv() {
		return hiv;
	}

	public void setHiv(boolean hiv) {
		this.hiv = hiv;
	}

	public boolean isVdrl() {
		return vdrl;
	}

	public void setVdrl(boolean vdrl) {
		this.vdrl = vdrl;
	}

	public boolean isMyc() {
		return myc;
	}

	public void setMyc(boolean myc) {
		this.myc = myc;
	}

	public boolean isOur() {
		return our;
	}

	public void setOur(boolean our) {
		this.our = our;
	}
	
	public boolean isLocalAnaisthesia() {
		return localAnaisthesia;
	}

	public void setLocalAnaisthesia(boolean localAnaisthesia) {
		this.localAnaisthesia = localAnaisthesia;
	}

	public int getApgar() {
		return apgar;
	}

	public void setApgar(int apgar) {
		this.apgar = apgar;
	}

	public String getWindings() {
		return windings;
	}

	public void setWindings(String windings) {
		this.windings = windings;
	}

	public String getMidwife() {
		return midwife;
	}

	public void setMidwife(String midwife) {
		this.midwife = midwife;
	}

	public String getAssistant() {
		return assistant;
	}

	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}

	public String getAnesthesist() {
		return anesthesist;
	}

	public void setAnesthesist(String anesthesist) {
		this.anesthesist = anesthesist;
	}

	public String getBreastfeeding() {
		return breastfeeding;
	}

	@JsonIgnore
	public String getBreastfeedingString() {
		
			if (breastfeeding == null)
				return "";
			
			if (breastfeeding.equalsIgnoreCase("complete"))
				return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.column.complete");

			if (breastfeeding.equalsIgnoreCase("partial"))
				return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.column.partial");

			if (breastfeeding.equalsIgnoreCase("none"))
				return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.column.none");

			return breastfeeding;
		
	}
	public void setBreastfeeding(String breastfeeding) {
		this.breastfeeding = breastfeeding;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public Date getOvulationDate() {
		return ovulationDate;
	}

	public void setOvulationDate(Date ovulationDate) {
		this.ovulationDate = ovulationDate;
	}

	public Date getIvfTransferDate() {
		return ivfTransferDate;
	}

	public void setIvfTransferDate(Date ivfTransferDate) {
		this.ivfTransferDate = ivfTransferDate;
	}

	public String getMotherThalassemiaCarrier() {
		return motherThalassemiaCarrier;
	}

	public void setMotherThalassemiaCarrier(String motherThalassemiaCarrier) {
		this.motherThalassemiaCarrier = motherThalassemiaCarrier;
	}

	public String getFatherThalassemiaCarrier() {
		return fatherThalassemiaCarrier;
	}

	public void setFatherThalassemiaCarrier(String fatherThalassemiaCarrier) {
		this.fatherThalassemiaCarrier = fatherThalassemiaCarrier;
	}

	public String getMiscarriageComment() {
		return miscarriageComment;
	}

	public void setMiscarriageComment(String miscarriageComment) {
		this.miscarriageComment = miscarriageComment;
	}

	public String getDeliveryLocation() {
		return deliveryLocation;
	}

	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}

	public String getChildStillAlive() {
		return childStillAlive;
	}

	public void setChildStillAlive(String childStillAlive) {
		this.childStillAlive = childStillAlive;
	}
	public int getMyopiaDegrees() {
		return myopiaDegrees;
	}
	public void setMyopiaDegrees(int myopiaDegrees) {
		this.myopiaDegrees = myopiaDegrees;
	}
	public String getMyopiaText() {
		return myopiaText;
	}
	public void setMyopiaText(String myopiaText) {
		this.myopiaText = myopiaText;
	}
	public Date getMyopiaDate() {
		return myopiaDate;
	}
	public void setMyopiaDate(Date myopiaDate) {
		this.myopiaDate = myopiaDate;
	}
	public int getEncounterId() {
		return encounterId;
	}
	public void setEncounterId(int encounterId) {
		this.encounterId = encounterId;
	}
	public Date getTopDate() {
		return topDate;
	}
	public void setTopDate(Date topDate) {
		this.topDate = topDate;
	}
	public Date getMiscarriageDate() {
		return miscarriageDate;
	}
	public void setMiscarriageDate(Date miscarriageDate) {
		this.miscarriageDate = miscarriageDate;
	}

	public int compareTo(Pregnancy otherPregnancy) {
		if (otherPregnancy == null)
			return 1;
		return this.lastPeriodLimitDate.compareTo(otherPregnancy.getLastPeriodLimitDate());
	}
	
}