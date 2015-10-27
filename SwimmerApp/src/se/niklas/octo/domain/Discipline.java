package se.niklas.octo.domain;

public enum Discipline {
	BREASTSTROKE("Bröstsim"),
	BACKSTROKE("Ryggsim"),
	BUTTERFLY("Fjärilssim"),
	FREESTYLE("Frisim"), 
	MEDLEY("Medley");
	
	private String discipline;
	private Discipline(String distance) {
		this.discipline = distance;
	}
	
	public String getDiscipline() {
		return discipline;
	}
	
	@Override
	public String toString() {
		return discipline;
	}
	
}
