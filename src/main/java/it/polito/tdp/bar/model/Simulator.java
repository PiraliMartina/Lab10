package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {

	public Simulator() {
	}

	// CODA DEGLI EVENTI
	private PriorityQueue<Event> coda = new PriorityQueue<Event>();

	// PARAMETRI DI SIMULAZIONE
	private Map<Integer, Integer> mappaTavoli = new TreeMap<Integer, Integer>(); // chiave = num posti; value = num
																					// tavoli disponibili

	private void popolaTavoli() {
		mappaTavoli.put(10, 2);
		mappaTavoli.put(8, 4);
		mappaTavoli.put(6, 4);
		mappaTavoli.put(4, 5);
	}

	private int totEventi = 2000;

	private int intervalloMin = 1;
	private int intervalloMax = 10;

	private int personeMin = 15;
	private int personeMax = 20;

	private int durataMin = 60;
	private int durataMax = 120;

	private double tolleranzaMin = 0.0;
	private double tolleranzaMax = 0.9;

	// MODELLO DEL MONDO
	// aggiorno la mappa dei tavoli

	// VALORI DA CALCOLARE
	private int numeroTotaleClienti;
	private int numeroClientiSoddisfatti;
	private int numeroClientiInsoddisfatti;

	// METODI PER RESTITUIRE I RISULTATI
	public int getNumeroTotaleClienti() {
		return numeroTotaleClienti;
	}

	public int getNumeroClientiSoddisfatti() {
		return numeroClientiSoddisfatti;
	}

	public int getNumeroClientiInsoddisfatti() {
		return numeroClientiInsoddisfatti;
	}

	// SIMULAZIONE VERA E PROPRIA
	public void run() {
		popolaTavoli();

		this.numeroTotaleClienti = 0;
		this.numeroClientiSoddisfatti = 0;
		this.numeroClientiInsoddisfatti = 0;

		this.coda.clear();

		LocalDateTime ora = LocalDateTime.now();
		Event e = new Event(ora, EventType.NEW_CLIENT, getNumPersone());
		this.coda.add(e);

		do {
			processEvent(this.coda.poll());
			ora = ora.plus(getIntervallo());
			Event nuovo = new Event(ora, EventType.NEW_CLIENT, getNumPersone());
			this.coda.add(nuovo);
		} while (numeroTotaleClienti != totEventi);
	}

	private void processEvent(Event e) {
		switch (e.getType()) {
		case NEW_CLIENT:
			numeroTotaleClienti++;
//			LocalDateTime arrivo = e.getTime().plus(getIntervallo());
//			Event nuovoo = new Event(arrivo, EventType.NEW_CLIENT, getNumPersone());
//			this.coda.add(nuovoo);
			// Tavolo libero?
			int tavoloOccupato = tavoloLibero(e.getDimensioneTavoloMassima(), e.getDimensioneTavoloRichiesta());
			if (tavoloOccupato > 0) { // -1 se non assegno il tavolo
				numeroClientiSoddisfatti++;
				LocalDateTime fine = e.getTime().plus(getDurata());
				Event nuovo = new Event(fine, EventType.CLIENT_SERVED, tavoloOccupato);
				this.coda.add(nuovo);
			}
			// Bancone?
			else {
				if (banconeOK())
					numeroClientiSoddisfatti++;
				else
					numeroClientiInsoddisfatti++;
			}
			break;
		case CLIENT_SERVED:
			// libero il tavolo
			int dimensioneTavolo = e.getDimensioneTavoloRichiesta();
			int tavoliAttualmenteLiberi = mappaTavoli.get(dimensioneTavolo) + 1;
			mappaTavoli.remove(dimensioneTavolo);
			mappaTavoli.put(dimensioneTavolo, tavoliAttualmenteLiberi);
			break;
		}

	}

	private boolean banconeOK() {
		if (Math.random() <= getTolleranza())
			return true;
		return false;
	}

	private int tavoloLibero(int dimensioneMax, int dimesioneRichiesta) {
		for (int i = 1; i <= dimensioneMax; i++) {
			if (mappaTavoli.get(i) != null && mappaTavoli.get(i) > 0) {
				int tavoliLiberi = mappaTavoli.get(i) - 1;
				mappaTavoli.remove(i);
				mappaTavoli.put(i, tavoliLiberi);
				return i;
			}

		}
		return -1;
	}

	public Duration getIntervallo() {
		double num = (intervalloMax - intervalloMin + 1) * Math.random();
		Duration durata = Duration.of((int) (num + intervalloMin), ChronoUnit.MINUTES);
		return durata;
	}

	public int getNumPersone() {
		double num = (personeMax - personeMin + 1) * Math.random();
		return (int) num + personeMin;
	}

	public Duration getDurata() {
		double num = (durataMax - durataMin + 1) * Math.random();
		Duration durata = Duration.of((int) (num + durataMin), ChronoUnit.MINUTES);
		return durata;
	}

	public double getTolleranza() {
		double num = -1.00;
		while (num > tolleranzaMax || num < tolleranzaMin)
			num = Math.random();
		return num;
	}
}
