package org.openmrs.module.obgyn.extension.html;

import org.openmrs.api.context.Context;
import org.openmrs.module.Extension;
import org.openmrs.module.web.extension.PatientDashboardTabExt;

public class HistoryDashboardTabExt extends PatientDashboardTabExt {
	
	/**
	 * @see AdministrationSectionExt#getMediaType()
	 */
	public Extension.MEDIA_TYPE getMediaType() {
		return Extension.MEDIA_TYPE.html;
	}
	
	/**
	 * @see AdministrationSectionExt#getTitle()
	 */
	public String getTitle() {
		return "OBGyn7.title";
	}
	
	
	@Override
	public String getPortletUrl() {
		// TODO Auto-generated method stub
		return "patientHistory.portlet";
	}

	@Override
	public String getRequiredPrivilege() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTabId() {
		// TODO Auto-generated method stub
		return "History";
	}

	@Override
	public String getTabName() {
		return Context.getMessageSourceService().getMessage("OBGyn7.historyTab");

	}
}