package it.polito.tdp.genes.model;

public class Event implements Comparable<Event>{
	//l'ingegnere sceglie che tipo di gene studiare al tempo t
	private int T; //tempo: da 0-36
	private int nIng; //numero dell'ingegnere
	public Event(int t, int nIng) {
		super();
		T = t;
		this.nIng = nIng;
	}
	public int getT() {
		return T;
	}
	public int getnIng() {
		return nIng;
	}
	@Override
	public int compareTo(Event o) {
		
		return this.T-o.T;
	}
	
	
	
	

}
