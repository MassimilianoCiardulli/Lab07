package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.bean.PowerOutage;
import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	List<PowerOutage> listaPO ;
	List<PowerOutage> parziale ;
	int best ;

	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> getListaBlackout(Nerc n, int years, int h) {
		// TODO Auto-generated method stub
		
		listaPO = podao.getPowerOutagesForNerc(n);
		System.out.println(listaPO.size());
		parziale = new ArrayList<>();
		best = 0;

		
		recursive(parziale, years, h, 0);
		System.out.println(parziale);
		return parziale ;
	}
	
	private void recursive(List<PowerOutage> parziale, int years, int h, int livello) {
		
		for(PowerOutage po:listaPO) {
			if(isValid(po, years, h)) {
				parziale.add(po);
				
				System.out.println(po + " livello = " + livello);
				recursive(parziale, years, h, livello+1);
				po.setScartato(true);
				//parziale.remove(parziale.size()-1);
				
			}
			
		}
	}

	private boolean isValid(PowerOutage po, int years, int h) {
		
		if(parziale.contains(po))
			return false ;
		
		if(parziale.isEmpty())
			return true ;
		
		if(!po.isScartato()) {
			
			long anni = po.getInizio().until(po.getFine(), ChronoUnit.YEARS); 
			
			long ore = po.getInizio().until(po.getFine(), ChronoUnit.HOURS); 
			
			int customers = this.calcolaCustomers(parziale);
			
			for(PowerOutage p:parziale) 
				//if(p.getEventType().equals(po.getEventType())) 
				{ 
					
					anni += p.getInizio().until(p.getFine(), ChronoUnit.YEARS); 
					ore += p.getInizio().until(p.getFine(), ChronoUnit.HOURS);
					
				}
			
			if(anni <= years && ore <= h && customers > best)		
				return true ;
		}
		
		return false;  
	}
	
	public int calcolaCustomers(List<PowerOutage> parziale) {
		int persone = 0 ;
		for(PowerOutage po:parziale)
			persone += po.getCustomersAffected();
		return persone;
	}

	public int calcolaOre(List<PowerOutage> result) {
		int h = 0;
		for(PowerOutage po:result)
			h += po.getInizio().until(po.getFine(), ChronoUnit.HOURS);

		return h;
	}

}
