
package org.openmrs.module.obgyn.states;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.IVFProgram;
import org.openmrs.module.obgyn.IVFProgram.IVFType;
import org.springframework.format.annotation.DateTimeFormat;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class EggDonation extends IVFState implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String doctor;
		private String medicalFacility;
		private int age;
		private String nationality;
		private int previousDonations;
		private int numberOfPregnancies;
		private int numberOfChildren;
		private int numberOfRecipients;
		private int eggsDonated;
		
		public EggDonation() {
			super(IVFState.EGG_DONATION);
		}

		public String getName() {
			return Context.getMessageSourceService().getMessage("OBGyn7.IVF.list.column.egg_donation");
		}

	
		@Override
		public void collectSperm(IVFProgram.IVFType type) {
		}

		@Override
		public void abandonProgram(IVFType type) {
		}
		
		public String getDoctor() {
			return doctor;
		}

		public void setDoctor(String doctor) {
			this.doctor = doctor;
		}

		public String getMedicalFacility() {
			return medicalFacility;
		}

		public void setMedicalFacility(String medicalFacility) {
			this.medicalFacility = medicalFacility;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getNationality() {
			return nationality;
		}

		public void setNationality(String nationality) {
			this.nationality = nationality;
		}

		public int getPreviousDonations() {
			return previousDonations;
		}

		public void setPreviousDonations(int previousDonations) {
			this.previousDonations = previousDonations;
		}

		public int getNumberOfPregnancies() {
			return numberOfPregnancies;
		}

		public void setNumberOfPregnancies(int numberOfPregnancies) {
			this.numberOfPregnancies = numberOfPregnancies;
		}

		public int getNumberOfChildren() {
			return numberOfChildren;
		}

		public void setNumberOfChildren(int numberOfChildren) {
			this.numberOfChildren = numberOfChildren;
		}

		public int getNumberOfRecipients() {
			return numberOfRecipients;
		}

		public void setNumberOfRecipients(int numberOfRecipients) {
			this.numberOfRecipients = numberOfRecipients;
		}

		public int getEggsDonated() {
			return eggsDonated;
		}

		public void setEggsDonated(int eggsDonated) {
			this.eggsDonated = eggsDonated;
		}

	}