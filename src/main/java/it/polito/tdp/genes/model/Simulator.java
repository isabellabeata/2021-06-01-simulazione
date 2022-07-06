package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {
	
	//coda degli eventi 
	private PriorityQueue<Event> queue;
	//modello del mondo
	//1. Dato un ingegnere (numero da 0 a n-1) dimmi su quale gene lavora
	private List<Genes> geneStudiato; //geneStudiato.get(nIng)
	//2. Dato un gene dimmi quanti ing ci lavorano
	//Map<Genes, Integer> numIngegneri;
	
	
	//parametri input
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private Genes startGene;
	private Integer nTotIng;
	
	private Integer TMAX=36; //nMesi simulazione
	private double probMantenereGene= 0.3;
	
	
	//parametri calcolati in out
	//Geni che al mese 36 sono studiati almeno da un ingegnere: fotografia dello stato del sistema al mese 36
	//ricavabile facilmente dallo stato del mondo al mese 36, li prendiamo dalla List<>geneStudiato
	
	
	public Simulator(Genes startG, Integer nTot, Graph<Genes, DefaultWeightedEdge> grafo) {
		this.startGene=startG;
		this.nTotIng=nTot;
		this.grafo= grafo;
		
		if(this.grafo.degreeOf(startG)==0) {
			throw new IllegalArgumentException("Il vertice di partenza è isolato");
		}
		//inizializzo la coda
		this.queue= new PriorityQueue<>();
		for(int nIng=0; nIng<this.nTotIng; nIng++) {
			this.queue.add(new Event(0, nIng));
		}
		//inizializzo il modello del mondo, creando u array con totIng valori pari a startGene
		this.geneStudiato= new ArrayList<Genes>();
		for(int nIng=0; nIng<this.nTotIng; nIng++) {
			this.geneStudiato.add(this.startGene);
			}
		}
		
	public void run() {
		
		while(!this.queue.isEmpty()) {
			Event ev= queue.poll();
			
			int T=ev.getT();
			int nIng= ev.getnIng();
			Genes g= this.geneStudiato.get(nIng);
			
			if(T<this.TMAX) {//cosa studierà l'nIng al mese T+1?
				if(Math.random()<this.probMantenereGene) {
					//mantieniGene
					this.queue.add(new Event(T+1, nIng));
				}else {
					//cambia gene
					
					
					// calcola la somma dei pesi adiacenti S
					double S=0;
					for(DefaultWeightedEdge edge: this.grafo.edgesOf(g)) {
						S+= this.grafo.getEdgeWeight(edge);
					}
					//estrai numero casuale tra 0 e S
					double R= Math.random()*S;
					
					//confronta R con le somme parziali dei pesi
					Genes nuovo= null;
					double somma=0;
					for(DefaultWeightedEdge edge: this.grafo.edgesOf(g)) {
						somma+= this.grafo.getEdgeWeight(edge);
						if(somma>R) {
							nuovo=Graphs.getOppositeVertex(this.grafo, edge, g);
							break;
						}
					}
				this.geneStudiato.set(nIng, nuovo);
				this.queue.add(new Event(T+1, nIng));
					}
				}
			}
		}
	
	public Map<Genes, Integer> getGeniStudiati(){
		Map<Genes, Integer> studiati= new HashMap<>();
		
		for(int nIng=0; nIng<this.nTotIng; nIng++) {
			Genes g= this.geneStudiato.get(nIng);
			if(studiati.containsKey(g)) {
				studiati.put(g, studiati.get(g)+1);
			}else {
				studiati.put(g, 1);
			}
		}
		return studiati;
	}
	}
		

