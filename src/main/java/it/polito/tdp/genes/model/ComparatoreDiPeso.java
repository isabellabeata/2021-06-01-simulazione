package it.polito.tdp.genes.model;

import java.util.Comparator;

public class ComparatoreDiPeso implements Comparator<Genes> {

	@Override
	public int compare(Genes o1, Genes o2) {
		// TODO Auto-generated method stub
		return -(int)(o1.getPesoPt1()-o2.getPesoPt1());
	}

}
