package it.polito.tdp.poweroutages.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutage {
	private int id ;
	private EventType eventType ;
	private Tag tag ;
	private Nerc nerc ;
	private LocalDateTime inizio ;
	private LocalDateTime fine ;
	private int customersAffected ;
	
	public PowerOutage() {
		
	}

	public PowerOutage(int id, Nerc nerc, LocalDateTime inizio, LocalDateTime fine, int customersAffected) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.inizio = inizio;
		this.fine = fine;
		this.customersAffected = customersAffected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PowerOutage [id=");
		builder.append(id);
		builder.append(", nerc=");
		builder.append(nerc);
		builder.append(", inizio=");
		builder.append(inizio);
		builder.append(", fine=");
		builder.append(fine);
		builder.append(", customersAffected=");
		builder.append(customersAffected);
		builder.append("]");
		return builder.toString();
	}
	
	
}
