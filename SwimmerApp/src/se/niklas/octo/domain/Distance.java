package se.niklas.octo.domain;

public enum Distance {
	TWENTY_FIVE("25"),
	FIFTY("50"),
	ONE_HUNDRED("50"),
	TWO_HUNDRED("50"),
	FOUR_HUNDRED("50"),
	EIGHT_HUNDRED("50"),
	FIFTEEN_HUNDRED("50");
	
	private String distance;
	private Distance(String distance) {
		this.distance = distance;
	}
	
	public String getDistance() {
		return distance;
	}
	
	@Override
	public String toString() {
		return distance;
	}
}
