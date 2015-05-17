package org.openmrs.module.obgyn.states;

import org.openmrs.module.obgyn.IVFProgram;
import org.openmrs.module.obgyn.IVFProgram.IVFType;


public interface State {

	public void stimulate(IVFProgram.IVFType type) ;
	public void downregulate(IVFProgram.IVFType type) ;
	public void receiveEggDonation(IVFProgram.IVFType type) ;
	public void collectEggs(IVFProgram.IVFType type) ;
	public void collectSperm(IVFProgram.IVFType type) ;
	public void receiveDonatedEggs(IVFProgram.IVFType type) ;
	public void fertilize(IVFProgram.IVFType type) ;
	public void transferEmbryos(IVFProgram.IVFType type) ;
	public void defrozeEmbryos(IVFProgram.IVFType type);
	public void transferFrozenEmbryos(IVFProgram.IVFType type) ;
	public void monitorBiochemically(IVFProgram.IVFType type) ;
	public void monitorClinically(IVFProgram.IVFType type) ;	
	public void assertBiochemicalPregnancy(IVFProgram.IVFType type) ;
	public void assertClinicalPregnancy(IVFProgram.IVFType type) ;
	public void assertNoPregnancy(IVFProgram.IVFType type) ;
	public void assertBiochemicalMiscarriage(IVFProgram.IVFType type) ;
	public void assertClinicalMiscarriage(IVFProgram.IVFType type) ;
	public void fertilizeWithDonor(IVFType type);
	public void abandonProgram(IVFType type);
	public void freezeAllEmbryos(IVFType type);
}
