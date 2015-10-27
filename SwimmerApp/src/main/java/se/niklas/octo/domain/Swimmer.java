package se.niklas.octo.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import se.niklas.octo.format.FormatHelper;

public class Swimmer {
	private String name;
	private String yearOfBirth;
	private List<PersonalBest> personalBests;
	private String octoId;
	private String swimmingClub;

	public Swimmer(String name, String yearOfBirth, String octoId, String swimmingClub) {
		this.name = name;
		this.yearOfBirth = yearOfBirth;
		this.octoId = octoId;
		this.swimmingClub = swimmingClub;
		this.personalBests = new ArrayList<>();
	}
	
	public String getOctoId() {
		return octoId;
	}
	
	public String getSwimmingClub() {
		return swimmingClub;
	};

	public String getName() {
		return name;
	}
	
	public String getYearOfBirth() {
		return yearOfBirth;
	}
	
	public void addPersonalBest(PersonalBest personalBest) {
		personalBests.add(personalBest);
	}

	public List<PersonalBest> getPersonalBests() {
		return personalBests;
	}

	public PersonalBest getPersonalBest(Event event) {
		for (PersonalBest personalBest : personalBests) {
			if(personalBest.getEvent().equals(event)) {
				return personalBest;
			}
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(super.equals(obj)) {
			return true; //Same object
		}
		if(obj == null || !(obj instanceof Swimmer)) {
			return false;
		}
		return this.octoId.equals(((Swimmer)obj).getOctoId());
	}
	
	@Override
	public String toString() {
		return String.format("%-20s %-4s %-20s", name, yearOfBirth, swimmingClub);
	}

}