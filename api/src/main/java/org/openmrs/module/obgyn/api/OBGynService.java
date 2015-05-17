package org.openmrs.module.obgyn.api;

import java.util.List;
import java.util.Date;

import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(OBGynService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface OBGynService extends OpenmrsService {

	@Transactional(readOnly=true)
	List<Patient> getNewPatients(Date startDate);

	@Transactional(readOnly=true)
	List<Patient> getExistingPatients(Date startDate);

}