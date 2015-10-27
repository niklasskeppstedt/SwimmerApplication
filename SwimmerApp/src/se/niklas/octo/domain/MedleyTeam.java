package se.niklas.octo.domain;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.niklas.octo.format.FormatHelper;

public class MedleyTeam {
	List<PersonalBest> relays = new ArrayList<>();
	Distance distance = null;
	
	public MedleyTeam(PersonalBest backstrokeBest, PersonalBest butterflyBest,
			PersonalBest breaststrokeBest, PersonalBest freestyleBest) {
		relays.add(backstrokeBest);
		relays.add(butterflyBest);
		relays.add(breaststrokeBest);
		relays.add(freestyleBest);
		distance = backstrokeBest.getEvent().getDistance();
	}
	
	public boolean isValid() {
		Set<Swimmer> swimmers = new HashSet<>();
		for (PersonalBest personalBest : relays) {
			swimmers.add(personalBest.getSwimmer());
		}
		return swimmers.size() == 4;
	}

	public Duration getTime() {
		Duration time = Duration.ZERO;
		for (PersonalBest personalBest : relays) {
			time = time.plus(personalBest.getTime());
		}
		return time;
	}
	
	@Override
	public String toString() {
		String lineSeparator = System.getProperty("line.separator");
		StringBuilder result = new StringBuilder();
		result.append(String.format("%-36s %-10s" + lineSeparator, "Medley Relay " + "4x" + distance + " with total time of" , FormatHelper.formatDuration(getTime())));
		for (PersonalBest personalBest : relays) {
			result.append(String.format("%15s %-10s" + lineSeparator, personalBest, personalBest.getSwimmer().getName()));
		}
		return result.toString();
	}

}