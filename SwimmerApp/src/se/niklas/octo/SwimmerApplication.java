package se.niklas.octo;

import java.util.ArrayList;
import java.util.List;

import se.niklas.octo.domain.Event;
import se.niklas.octo.domain.MedleyTeam;
import se.niklas.octo.domain.PersonalBest;
import se.niklas.octo.domain.Swimmer;
import se.niklas.octo.parse.OctoParser;
import se.niklas.octo.sort.MedleyTeamComparator;
import se.niklas.octo.sort.PersonalBestComparator;

public class SwimmerApplication {
	
	private static String swimmerUrl = "http://www.octoopen.se/index.php?r=swimmer/view&id=";
	
	private static String ELIAS = "297358";
	private static String OTTO_L = "301255";
	private static String OTTO_R = "295557";
	private static String WALTER = "300057";
	private static String FILIP = "298416";
	
	private List<Swimmer> swimmers = new ArrayList<>();

	public static void main(String[] args) {
		SwimmerApplication swimmApp = new SwimmerApplication();
		String[] swimmerIds = new String[] {ELIAS,OTTO_L,OTTO_R,FILIP,WALTER};
		swimmApp.load(swimmerIds);
		MedleyTeam medleyTeam = swimmApp.getBestMedleyTeam();
		System.out.println("Best medleyteam has a time of " + medleyTeam.getTime() + " and consists of:");
		System.out.println(medleyTeam.getBackstroke().getSwimmer().getName());
		System.out.println(medleyTeam.getButterfly().getSwimmer().getName());
		System.out.println(medleyTeam.getBreaststroke().getSwimmer().getName());
		System.out.println(medleyTeam.getFreestyle().getSwimmer().getName());
	}

	private void load(String[] swimmerIds) {
		OctoParser parser = new OctoParser(swimmerUrl);
		for (int i = 0; i < swimmerIds.length; i++) {
			Swimmer swimmer = parser.parse(swimmerIds[i]);
			swimmers.add(swimmer);
		}
	}
	
	public List<Swimmer> getSwimmers() {
		return swimmers;
	}
	
	public PersonalBest getBestTimeForEvent(Event event) {
		List<PersonalBest> bestTimesForEvent = getBestTimesForEvent(event);
		bestTimesForEvent.sort(new PersonalBestComparator());
		return bestTimesForEvent.get(0);
	}
	
	public List<PersonalBest> getBestTimesForEvent(Event event) {
		List<PersonalBest> bestTimes = new ArrayList<>();
		for (Swimmer swimmer : getSwimmers()) {
			PersonalBest personalBest = swimmer.getPersonalBest(event);
			if(personalBest != null) {
				bestTimes.add(personalBest);
			}
		}
		return bestTimes;
	}
	
	public MedleyTeam getBestMedleyTeam() {
		List<MedleyTeam> medleyTeams = getPossibleMedleyTeams();
		medleyTeams.sort(new MedleyTeamComparator());
		return medleyTeams.get(0);
	}

	private List<MedleyTeam> getPossibleMedleyTeams() {
		List<PersonalBest> backstroke = getBestTimesForEvent(Event.BACKSTROKE_50);
		List<PersonalBest> butterfly = getBestTimesForEvent(Event.BUTTERFLY_50);
		List<PersonalBest> breaststroke = getBestTimesForEvent(Event.BREASTSTROKE_50);
		List<PersonalBest> freestyle = getBestTimesForEvent(Event.FREESTYLE_50);
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
		return allTeams;
	}

}
