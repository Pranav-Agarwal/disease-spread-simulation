package model;


public class Virus { 
	
	//config parameters
	
	String VirusType;
	int infectionPeriod;
	int quarantinePeriod;
	int incubationPeriod;
	double lethality;
	
	//to be calculated
	int k;
	int r;
	
	
	
	public Virus()
	{
		this.VirusType= simulationConfig.VirusType;
		this.infectionPeriod=simulationConfig.infectionPeriod;
		this.quarantinePeriod=simulationConfig.quarantinePeriod;
		this.incubationPeriod=simulationConfig.incubationPeriod;
		this.lethality=simulationConfig.lethality;
	}
	

	public void k()
	{}
	
	
	public void r()
	{}
	
	
	
}