package se.niklas.octo.domain;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class MedleyTeam {
	PersonalBest breaststroke;
	PersonalBest butterfly;
	PersonalBest backstroke;
	PersonalBest freestyle;
	
	public MedleyTeam(PersonalBest backstrokeBest, PersonalBest butterflyBest,
			PersonalBest breaststrokeBest, PersonalBest freestyleBest) {
		breaststroke = breaststrokeBest;
		butterfly = butterflyBest;
		backstroke = backstrokeBest;
		freestyle = freestyleBest;
	}
	
	public boolean isValid() {
		Set<Swimmer> swimmers = new HashSet<>();
		swimmers.add(breaststroke.getSwimmer());
		swimmers.add(butterfly.getSwimmer());
		swimmers.add(backstroke.getSwimmer());
		swimmers.add(freestyle.getSwimmer());
		return swimmers.size() == 4;
	}

	public Duration getTime() {
		return breaststroke.getTime().plus(butterfly.getTime()).plus(backstroke.getTime()).plus(freestyle.getTime());
	}
	
	public PersonalBest getBreaststroke() {
		return breaststroke;
	}
	
	public PersonalBest getButterfly() {
		return butterfly;
	}
	
	public PersonalBest getBackstroke() {
		return backstroke;
	}
	
	public PersonalBest getFreestyle() {
		return freestyle;
	}

}