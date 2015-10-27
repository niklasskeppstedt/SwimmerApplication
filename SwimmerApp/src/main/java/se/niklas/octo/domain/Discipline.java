package se.niklas.octo.domain;

public enum Discipline {
	BREASTSTROKE("Br�stsim"),
	BACKSTROKE("Ryggsim"),
	BUTTERFLY("Fj�rilssim"),
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
