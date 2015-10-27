package se.niklas.octo.domain;

import java.util.ArrayList;
import java.util.List;

public class Swimmer {
	String name;
	String yearOfBirth;
	List<PersonalBest> personalBests;
	
	public Swimmer(String name, String yearOfBirth) {
		this.name = name;
		this.yearOfBirth = yearOfBirth;
		personalBests = new ArrayList<>();
	}
	
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

}