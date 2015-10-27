package se.niklas.octo.domain;

import java.time.Duration;

public class PersonalBest {
	private Event event;
	private Duration time;
	private Swimmer swimmer;
	private String competition;
	
	public PersonalBest(Event event, Duration time, String competition, Swimmer swimmer) {
		this.event = event;
		this.time = time;
		this.swimmer = swimmer;
		this.competition = competition;
	}
	
	public Duration getTime() {
		return time;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public Swimmer getSwimmer() {
		return swimmer;
	}
	
	public String getCompetition() {
		return competition;
	}

}
