package it.polito.tdp.bar.model;

public class Model {

	private Simulator sim;

	public Model() {
		sim = new Simulator();
	}
	
	public void simula() {
		sim.run();
	}
	
	public int getSoddisfatti() {
		return sim.getNumeroClientiSoddisfatti();
	}
	
	public int getInsoddisfatti() {
		return sim.getNumeroClientiInsoddisfatti();
	}
	
	public int getClienti() {
		return sim.getNumeroTotaleClienti();
	}
	
	
	
	
}
