package model;

//represents a virus that will be released in the population to model spread
public class Virus { 
	
	//config parameters taken from config file
	public String virusType;
	public int infectionPeriod;
	public int incubationPeriod;
	public double chanceToGetSymptoms;
	public double lethality;
	public double infectivity;
		
	public Virus(){
		virusType=simulationConfig.virusType;
	    infectionPeriod=simulationConfig.infectionPeriod;
		incubationPeriod=simulationConfig.incubationPeriod;
		chanceToGetSymptoms=simulationConfig.chanceToGetSymptoms;
		lethality=simulationConfig.lethality;
		infectivity=simulationConfig.infectivity;
	}
	
}