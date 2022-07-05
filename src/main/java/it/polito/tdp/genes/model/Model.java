package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private List<Interaction> interactions;
	
	public Model() {
		this.dao= new GenesDao();
		
	}
	
	public List<Genes> popolaCmb(){
		return this.dao.getVertici();
	}
	
	public void creaGrafo() {
		this.grafo= new SimpleWeightedGraph<Genes, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.getVertici());
		this.interactions=new ArrayList<Interaction>(dao.getAllInteractions());
		for(Genes g1: this.grafo.vertexSet()) {
			for(Genes g2 : this.grafo.vertexSet()) {
				if(g1!=g2 && !(this.grafo.containsEdge(g1, g2))) {
					for(Interaction i: this.interactions) {
						if(i.getId1().compareTo(g1.getGeneId())==0 && i.getId2().compareTo(g2.getGeneId())==0
								|| i.getId1().compareTo(g2.getGeneId())==0 && i.getId2().compareTo(g1.getGeneId())==0) {
							if(g1.getChromosome()==g2.getChromosome()) {
								Arco a = new Arco(g1, g2, 4*(i.getCorr()));
								Graphs.addEdgeWithVertices(this.grafo, g1, g2, a.getCorr());
							}else {
								Arco a = new Arco(g1, g2, (i.getCorr()));
								Graphs.addEdgeWithVertices(this.grafo, g1, g2, a.getCorr());
							}
						}
					}
				}
			}
		}
		
	}
	

	public String nVertici() {
		return "Grafo creato!"+"\n"+"#verici: "+ this.grafo.vertexSet().size()+"\n";
	}
	
	public String nArchi() {
		return "#archi: "+ this.grafo.edgeSet().size()+"\n";
	}
	
	
	public String adiacenti(Genes g) {
		String s="";
		
		List<Genes> list= new ArrayList<Genes>(Graphs.neighborListOf(this.grafo, g));
		
		for(Genes gi: list) {
			DefaultWeightedEdge e= this.grafo.getEdge(gi, g);
			double peso= this.grafo.getEdgeWeight(e);
			gi.setPesoPt1(peso);
		}
		
		Collections.sort(list, new ComparatoreDiPeso());
		
		for(Genes gg: list) {
			s+= gg+" "+gg.getPesoPt1()+"\n";
		}
		return s;
	}
	
}
