package se.niklas.octo.predicate;

import java.util.List;
import java.util.function.Predicate;

import se.niklas.octo.domain.Swimmer;

public class PredicateBuilder {
	
	public static Predicate<Swimmer> createSwimmerEqualsNamePredicate(String name) {
		return swimmer -> swimmer.getName().equalsIgnoreCase(name);
	}

	public static Predicate<Swimmer> createSwimmerEqualsOctoIdPredicate(String octoId) {
		return swimmer -> swimmer.getOctoId().equalsIgnoreCase(octoId);
	}
	
	public static Predicate<Swimmer> createSwimmerOctoIdIsAvailabelPredicate(List<String> availableOctoIds) {
		return swimmer -> availableOctoIds.contains(swimmer.getOctoId());
	}

	public static Predicate<Swimmer> createSwimmerIsAvailabelPredicate(List<Swimmer> availableSwimmers) {
		return swimmer -> availableSwimmers.contains(swimmer);
	}

}
