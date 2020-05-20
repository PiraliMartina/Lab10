package it.polito.tdp.bar.model;

public class TestSimulator {

	public static void main(String[] args) {
		Simulator simulator = new Simulator();

//		for (int i = 0; i < 20; i++)
//			System.out.println(simulator.getIntervallo());
//
//		for (int i = 0; i < 20; i++)
//			System.out.println(simulator.getNumPersone());
//		
//		for (int i = 0; i < 20; i++)
//			System.out.println(simulator.getDurata());
//		
//		for(int i=0; i<20; i++)
//			System.out.println(simulator.getTolleranza());
		
		simulator.run();
		System.out.println(simulator.getNumeroTotaleClienti());
		System.out.println(simulator.getNumeroClientiSoddisfatti());
		System.out.println(simulator.getNumeroClientiInsoddisfatti());

	}

}
