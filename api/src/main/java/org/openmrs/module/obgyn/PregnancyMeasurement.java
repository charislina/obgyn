package org.openmrs.module.obgyn;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.OrderBy;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.obgyn.Pregnancy;
import org.springframework.format.annotation.DateTimeFormat;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PregnancyMeasurement extends BaseOpenmrsObject implements Serializable, Comparable<PregnancyMeasurement> {

	private static final long serialVersionUID = 3L;
	
	@JsonIgnore
	private int encounterId;
	
	private Integer measurementId;
	private Timestamp timestamp;
	private short month, week, day;
	private Date date;
	private float weight;
	private int sp;
	private int dp;
	
	private float blood_ht;
	private float blood_hb;
	private float blood_gluc;
	private float blood_wcc;
	private float blood_plt;
	
	private int uri_leu;
	private int uri_gluc;
	private int uri_hb;
	private int uri_pyo;
	private String uri_bact;
	
	private float us_hc;
	private float us_ac;
	private float us_fl;
	private int us_afi;
	private float us_efw;
	private String us_plac;
	private String us_projection;
	private float us_cxLength;

	
	private Pregnancy pregnancy;
	
	public PregnancyMeasurement() {}

	@Override
	public Integer getId() {
		return getMeasurementId();
	}


	@Override
	public void setId(Integer arg0) {
		setMeasurementId(arg0);
	}
	
	public Integer getMeasurementId() {
		return measurementId;
	}



	public void setMeasurementId(Integer measurementId) {
		this.measurementId = measurementId;
	}



	public Timestamp getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}



	public short getMonth() {
		return month;
	}



	public void setMonth(short month) {
		this.month = month;
	}



	public short getWeek() {
		return week;
	}



	public void setWeek(short week) {
		this.week = week;
	}



	public float getWeight() {
		return weight;
	}



	public void setWeight(float weight) {
		this.weight = weight;
	}



	public int getUri_hb() {
		return uri_hb;
	}



	public void setUri_hb(int uri_hb) {
		this.uri_hb = uri_hb;
	}



	public Pregnancy getPregnancy() {
		return pregnancy;
	}



	public void setPregnancy(Pregnancy pregnancy) {
		this.pregnancy = pregnancy;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int compareTo(PregnancyMeasurement otherMeasurement) {
		if (otherMeasurement == null || otherMeasurement.getDate() == null)
			return 1;
		
		if (this.date == null)
			return -1;
		
		return this.date.compareTo(otherMeasurement.getDate());		
	}

	public float getBlood_ht() {
		return blood_ht;
	}

	public void setBlood_ht(float blood_ht) {
		this.blood_ht = blood_ht;
	}

	public float getBlood_hb() {
		return blood_hb;
	}

	public void setBlood_hb(float blood_hb) {
		this.blood_hb = blood_hb;
	}

	public float getBlood_gluc() {
		return blood_gluc;
	}

	public void setBlood_gluc(float blood_gluc) {
		this.blood_gluc = blood_gluc;
	}

	public float getBlood_wcc() {
		return blood_wcc;
	}

	public void setBlood_wcc(float blood_wcc) {
		this.blood_wcc = blood_wcc;
	}

	public float getBlood_plt() {
		return blood_plt;
	}

	public void setBlood_plt(float blood_plt) {
		this.blood_plt = blood_plt;
	}

	public int getUri_leu() {
		return uri_leu;
	}

	public void setUri_leu(int uri_leu) {
		this.uri_leu = uri_leu;
	}

	public String getStarsUri_leu() {
		return getStarsFromInt(uri_leu);
	}
	
	public String getStarsUri_hb() {
		return getStarsFromInt(uri_hb);
	}
	
	public String getStarsUri_gluc() {
		return getStarsFromInt(uri_gluc);
	}
	
	private String getStarsFromInt(int num)
	{
		String stars = "-";
		switch(num) {
			case 1: 
					stars = "+";
					break;
					
			case 2: stars= "++";
					break;
					
			case 3: stars = "+++";
					break;
					
			case 4: stars = "++++";
					break;
					
			default : stars = "-";
		}
		return stars;	
	}
	
	public int getUri_gluc() {
		return uri_gluc;
	}

	public void setUri_gluc(int uri_gluc) {
		this.uri_gluc = uri_gluc;
	}

	public float getUri_HB() {
		return uri_hb;
	}

	public void setUri_HB(int uri_HB) {
		this.uri_hb = uri_HB;
	}

	public int getUri_pyo() {
		return uri_pyo;
	}

	public void setUri_pyo(int uri_pyo) {
		this.uri_pyo = uri_pyo;
	}

	public String getUri_bact() {
		return uri_bact;
	}

	public void setUri_bact(String uri_bact) {
		this.uri_bact = uri_bact;
	}

	@JsonIgnore
	public String getUri_bactString() {
		
		if (uri_bact == null)
			return "";
		
		if (uri_bact.compareToIgnoreCase("NEGATIVE") == 0)
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.bacterial.negative");
		
		if (uri_bact.compareToIgnoreCase("MEDIUM") == 0)
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.bacterial.medium");
		
		if (uri_bact.compareToIgnoreCase("HIGH") == 0)
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.bacterial.high");
		
		return uri_bact;
	}
	
	@JsonIgnore
	public String getUsprojectionString() {
		
		if (us_projection == null)
			return "";
		
		if (us_projection.compareToIgnoreCase("kefaliko") == 0)
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.projection.kefaliko");
	
		if (us_projection.compareToIgnoreCase("isxiako") == 0)
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.projection.isxiako");

		if (us_projection.compareToIgnoreCase("egkarsio") == 0)
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.projection.egkarsio");
		
		if (us_projection.compareToIgnoreCase("loxo") == 0)
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.projection.loxo");
		
		if (us_projection.compareToIgnoreCase("free") == 0)
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.projection.free");
		
		return us_projection;
	}
	
	public float getUs_hc() {
		return us_hc;
	}

	public void setUs_hc(float us_hc) {
		this.us_hc = us_hc;
	}

	public float getUs_ac() {
		return us_ac;
	}

	public void setUs_ac(float us_ac) {
		this.us_ac = us_ac;
	}

	public float getUs_fl() {
		return us_fl;
	}

	public void setUs_fl(float us_fl) {
		this.us_fl = us_fl;
	}

	public int getUs_afi() {
		return us_afi;
	}

	public void setUs_afi(int us_afi) {
		this.us_afi = us_afi;
	}

	public float getUs_efw() {
		return us_efw;
	}

	public void setUs_efw(float us_efw) {
		this.us_efw = us_efw;
	}

	public String getUs_plac() {
		return us_plac;
	}

	@JsonIgnore
	public String getUsplacString() {
		
		if (us_plac == null)
			return "";
		
		if (us_plac.equalsIgnoreCase("ANTERIOR HIGH"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.placenta.frontal_high");
		
		if (us_plac.equalsIgnoreCase("ANTERIOR LOW"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.placenta.frontal_low");
		
		if (us_plac.equalsIgnoreCase("POSTERIOR HIGH"))
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.placenta.rear_high");
		
		if (us_plac.equalsIgnoreCase("POSTERIOR LOW"))			
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.placenta.rear_low");
		
		if (us_plac.equalsIgnoreCase("LATERAL HIGH"))						
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.placenta.lateral_high");
		
		if (us_plac.equalsIgnoreCase("LATERAL LOW"))						
			return Context.getMessageSourceService().getMessage("OBGyn7.Pregnancy.list.placenta.lateral_low");
	
		return us_plac;
	}
	
	public void setUs_plac(String us_plac) {
		this.us_plac = us_plac;
	}

	public String getUs_projection() {
		return us_projection;
	}

	public void setUs_projection(String us_projection) {
		this.us_projection = us_projection;
	}

	public float getUs_cxLength() {
		return us_cxLength;
	}

	public void setUs_cxLength(float us_cxLength) {
		this.us_cxLength = us_cxLength;
	}

	public int getDp() {
		return dp;
	}

	public void setDp(int dp) {
		this.dp = dp;
	}

	public int getSp() {
		return sp;
	}

	public void setSp(int sp) {
		this.sp = sp;
	}

	public short getDay() {
		return day;
	}

	public void setDay(short day) {
		this.day = day;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(int encounterId) {
		this.encounterId = encounterId;
	}
	
	
	
}
