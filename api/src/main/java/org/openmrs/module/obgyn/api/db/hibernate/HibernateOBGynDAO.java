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
package org.openmrs.module.obgyn.api.db.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.module.obgyn.api.db.OBGynDAO;

/**
 * It is a default implementation of  {@link OBGynDAO}.
 */
public class HibernateOBGynDAO implements OBGynDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }
    
    public List<Patient> getNewPatients(Date startDate) {
		 List<Patient> list = sessionFactory.getCurrentSession().createCriteria(Patient.class)
				 .add(Restrictions.gt("dateCreated", startDate))
	 		      .list();
	 	  
	 	  if (list.isEmpty())
	 		  return null;
	 	  
	 	  return list;
	 }
	 
	public List<Patient> getExistingPatients(Date startDate) {
		List<Patient> list = sessionFactory.getCurrentSession().createCriteria(Patient.class)
				 .add(Restrictions.le("dateCreated", startDate))
	 		      .list();
	 	  
	 	  if (list.isEmpty())
	 		  return null;
	 	  
	 	  return list;
	}
}