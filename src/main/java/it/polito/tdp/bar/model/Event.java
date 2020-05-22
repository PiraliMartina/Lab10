package it.polito.tdp.bar.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event> {

	public enum EventType {
		NEW_CLIENT, CLIENT_SERVED
	}

	private LocalDateTime time;
	private EventType type;
	private int dimensioneTavoloRichiesta;
	private int dimensioneTavoloMassima;

	public Event(LocalDateTime time, EventType type, int dimensioneTavoloRichiesta) {
		this.time = time;
		this.type = type;
		this.dimensioneTavoloRichiesta = dimensioneTavoloRichiesta;
		this.dimensioneTavoloMassima = (int) (this.dimensioneTavoloRichiesta * 2);
	}

	public LocalDateTime getTime() {
		return time;
	}

	public EventType getType() {
		return type;
	}

	public int getDimensioneTavoloRichiesta() {
		return dimensioneTavoloRichiesta;
	}

	public int getDimensioneTavoloMassima() {
		return dimensioneTavoloMassima;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}

}
