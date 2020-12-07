package model;


public class Virus { 
	
	//config parameters
	
	String virusType;
	int infectionPeriod;
	int quarantinePeriod;
	int incubationPeriod;
	double chanceToGetSymptoms;
	double lethality;
	double infectivity;
	//to be calculated
	int k;
	int r;
	
	
	
	public Virus(String virusType){
			this.virusType=simulationConfig.virusType;
		    this.infectionPeriod=simulationConfig.infectionPeriod;
			this.quarantinePeriod=simulationConfig.quarantinePeriod;
			this.incubationPeriod=simulationConfig.incubationPeriod;
			this.chanceToGetSymptoms=simulationConfig.chanceToGetSymptoms;
			this.lethality=simulationConfig.lethality;
			this.infectivity=simulationConfig.infectivity;
			}
		

	public void k()
	{}
	
	
	public void r()
	{}
	
	
	
}