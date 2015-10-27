package se.niklas.octo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import se.niklas.octo.domain.Event;
import se.niklas.octo.domain.MedleyTeam;
import se.niklas.octo.domain.PersonalBest;
import se.niklas.octo.domain.Swimmer;
import se.niklas.octo.parse.OctoParser;
import se.niklas.octo.predicate.PredicateBuilder;
import se.niklas.octo.sort.MedleyTeamComparator;
import se.niklas.octo.sort.PersonalBestComparator;

public class SwimmerApplication {
	
	private static String swimmerResultUrl = "http://www.octoopen.se/index.php?r=swimmer/view&id=";
	private static String swimmerSearchUrl = "http://www.octoopen.se/index.php?r=swimmer/index&Swimmer[first_name]=afirstname&Swimmer[last_name]=alastname";
	
	private static String ELIAS = "297358";
	private static String OTTO_L = "301255";
	private static String OTTO_R = "295557";
	private static String WALTER = "300057";
	private static String FILIP = "298416";
	
	private Set<Swimmer> swimmers = new HashSet<>();

	public static void main(String[] args) {
		SwimmerApplication swimmApp = new SwimmerApplication();
		
		System.out.println("==== Search for swimmer with name David Kågström =========");
		List<Swimmer> swimmersResult = swimmApp.searchSwimmer("David", "Kågström");
		for (Swimmer swimmer : swimmersResult) {
			System.out.println(swimmer);
		}
		if(swimmersResult.size() == 1 ) {
			swimmApp.loadSwimmerFromOctoWeb(swimmersResult.get(0));
		}
		
		String[] swimmerIds = new String[] {ELIAS,OTTO_L,OTTO_R,FILIP,WALTER};
		swimmApp.loadSwimmersFromOctoWeb(swimmerIds);
		
		System.out.println("==== Get the top 5 Medley Teams (disregarding unavailable)=========");
		@SuppressWarnings("unchecked")
		List<MedleyTeam> topMedleyTeams = swimmApp.getAllMedleyTeams();
		for (int i = 0; i < 5; i++) {
			System.out.println(topMedleyTeams.get(i));;
		}
		
		System.out.println("==== Get the bottom 5 Medley Teams =========");
		@SuppressWarnings("unchecked")
		List<MedleyTeam> bottomMedleyTeams = swimmApp.getAllMedleyTeams();
		for (int i = bottomMedleyTeams.size() -1; i > bottomMedleyTeams.size() -6; i--) {
			System.out.println(bottomMedleyTeams.get(i));;
		}
		
		System.out.println("==== Search swimmers for Swimmer named Elias Skeppstedt =========");
		@SuppressWarnings("unchecked")
		List<Swimmer> swimmerByPredicate = swimmApp.getSwimmerByPredicate(PredicateBuilder.createSwimmerEqualsNamePredicate("Elias Skeppstedt"));
		for (Swimmer swimmer : swimmerByPredicate) {
			System.out.println("Found " + swimmer);
		}
		
		System.out.println("==== Get the best Medley Team regarding who is available=========");
		@SuppressWarnings("unchecked")
		List<Swimmer> availableSwimmers = swimmApp.getSwimmerByPredicate(PredicateBuilder.createSwimmerOctoIdIsAvailabelPredicate(Arrays.asList(swimmerIds)));
		@SuppressWarnings("unchecked")
		MedleyTeam medleyTeam = swimmApp.getBestMedleyTeam(PredicateBuilder.createSwimmerIsAvailabelPredicate(availableSwimmers));
		System.out.println(medleyTeam);

		System.out.println("==== Get the best Medley Team disregarding who is available=========");
		medleyTeam = swimmApp.getBestMedleyTeam();
		System.out.println(medleyTeam);
	}
	
	public List<Swimmer> getSwimmerByPredicate(@SuppressWarnings("unchecked") Predicate<Swimmer>... predicates) {
		List<Swimmer> swimmers = new ArrayList<>();
		swimmers.addAll(getSwimmers());
		//Use predicates
		for (int i = 0; i < predicates.length; i++) {
			Predicate<Swimmer> predicate = predicates[i];
			swimmers = swimmers.stream().filter(predicate).collect(Collectors.toList());
		}
		return swimmers;
	}

	public void loadSwimmerFromOctoWeb(Swimmer swimmer) {
		loadSwimmersFromOctoWeb(new String[] {swimmer.getOctoId()} );
		
	}

	public void loadSwimmersFromOctoWeb(String[] swimmerIds) {
		OctoParser parser = new OctoParser(swimmerResultUrl);
		for (int i = 0; i < swimmerIds.length; i++) {
			Swimmer swimmer = parser.parseSwimmer(swimmerIds[i]);
			swimmers.add(swimmer);
		}
	}
	
	public List<Swimmer> searchSwimmer(String firstName, String lastName) {
		String adjustedSearchUrl;
		try {
			adjustedSearchUrl = swimmerSearchUrl.replace("afirstname", URLEncoder.encode(firstName, "UTF-8"));
			adjustedSearchUrl = adjustedSearchUrl.replace("alastname", URLEncoder.encode(lastName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.err.println(e);
			return Collections.emptyList();
		}
		OctoParser parser = new OctoParser(adjustedSearchUrl);
		List<Swimmer> swimmers = new ArrayList<>();
		swimmers.addAll(parser.parseSearchResult());
		return swimmers;
				
	}
	
	public Set<Swimmer> getSwimmers() {
		return swimmers;
	}
	
	@SuppressWarnings("unchecked")
	public List<PersonalBest> getBestTimesForEvent(Event event, Predicate<Swimmer>... predicates) {
		List<PersonalBest> bestTimes = new ArrayList<>();
		List<Swimmer> swimmers = new ArrayList<>();
		swimmers.addAll(getSwimmers());
		//Use predicates
		for (int i = 0; i < predicates.length; i++) {
			Predicate<Swimmer> predicate = predicates[i];
			swimmers = swimmers.stream().filter(predicate).collect(Collectors.toList());
		}
		for (Swimmer swimmer : swimmers) {
			PersonalBest personalBest = swimmer.getPersonalBest(event);
			if(personalBest != null) {
				bestTimes.add(personalBest);
			}
		}
		bestTimes.sort(new PersonalBestComparator());
		return bestTimes;
	}
	
	@SafeVarargs
	public final MedleyTeam getBestMedleyTeam(Predicate<Swimmer>... predicates) {
		List<MedleyTeam> medleyTeams = getAllMedleyTeams(predicates);
		return medleyTeams.get(0);
	}

	public List<MedleyTeam> getAllMedleyTeams(@SuppressWarnings("unchecked") Predicate<Swimmer>... predicates) {
		List<PersonalBest> backstroke = getBestTimesForEvent(Event.BACKSTROKE_50, predicates);
		List<PersonalBest> butterfly = getBestTimesForEvent(Event.BUTTERFLY_50, predicates);
		List<PersonalBest> breaststroke = getBestTimesForEvent(Event.BREASTSTROKE_50, predicates);
		List<PersonalBest> freestyle = getBestTimesForEvent(Event.FREESTYLE_50, predicates);
		List<MedleyTeam> allTeams = new ArrayList<>();
		for (PersonalBest backstrokeBest : backstroke) {
			for (PersonalBest butterflyBest : butterfly) {
				for (PersonalBest breaststrokeBest : breaststroke) {
					for (PersonalBest freestyleBest : freestyle) {
						MedleyTeam medleyTeam = new MedleyTeam(backstrokeBest, butterflyBest, breaststrokeBest, freestyleBest);
						if(medleyTeam.isValid()) {
							allTeams.add(medleyTeam);
						}
					}
				}
			}
		}
		allTeams.sort(new MedleyTeamComparator());
		return allTeams;
	}

}
