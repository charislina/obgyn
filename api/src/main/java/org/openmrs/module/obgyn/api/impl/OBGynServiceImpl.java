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
package org.openmrs.module.obgyn.api.impl;

import java.util.Date;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.obgyn.api.OBGynService;
import org.openmrs.module.obgyn.api.db.OBGynDAO;

/**
 * It is a default implementation of {@link OBGynService}.
 */
public class OBGynServiceImpl extends BaseOpenmrsService implements OBGynService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private OBGynDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(OBGynDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public OBGynDAO getDao() {
	    return dao;
    }
    
    	
	public List<Patient> getNewPatients(Date startDate)  {
		return dao.getNewPatients(startDate);
	}
	
	public List<Patient> getExistingPatients(Date startDate) {
		return dao.getExistingPatients(startDate);		
	}
}