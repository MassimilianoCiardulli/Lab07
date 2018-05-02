package it.polito.tdp.poweroutages.model;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.bean.PowerOutage;
import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	List<PowerOutage> listaPO ;
	List<PowerOutage> parziale ;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> getListaBlackout(Nerc n, int years, int h) {
		// TODO Auto-generated method stub
		
		listaPO = podao.getPowerOutagesForNerc(n);
		parziale = new ArrayList<>();
		
		recursive(parziale, years, h);
		
		return parziale ;
	}

	private void recursive(List<PowerOutage> parziale, int years, int h) {
		
		for(PowerOutage po:listaPO) {
			if(isValid(po, years, h)) {
				parziale.add(po);
				recursive(parziale, years, h);
				parziale.remove(parziale.size()-1);
			}
		
		}
	}

	private boolean isValid(PowerOutage po, int years, int h) {
		
		if(parziale.contains(po))
			return false ;
		
		if(parziale.isEmpty())
			return true ;
		
		long anni = po.getInizio().until(po.getFine(), ChronoUnit.YEARS);
		long ore = po.getInizio().until(po.getFine(), ChronoUnit.HOURS);
		int personeCoinvolte = po.getCustomersAffected();
		
		for(PowerOutage p:parziale) {
			if(p.getEventType().equals(po.getEventType())) {
				
				anni += p.getInizio().until(p.getFine(), ChronoUnit.YEARS);
				ore += p.getInizio().until(p.getFine(), ChronoUnit.HOURS);
				personeCoinvolte += p.getCustomersAffected();
			}
		
		if(anni <= years && ore <= h)		
			return true ;
		}
		return false;  
	}

}
