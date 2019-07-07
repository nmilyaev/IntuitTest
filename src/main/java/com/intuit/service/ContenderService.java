package com.intuit.service;

import com.intuit.model.Citizen;
import com.intuit.model.Contender;
import com.intuit.model.Idea;
import com.intuit.model.IdeaRating;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ContenderService {
    private Set<Contender> contenders = new HashSet<>();

    public Citizen checkAndAddFollower(IdeaRating rating) {
        if (rating.getRating() >= 5) {
            Contender contender = rating.getIdea().getContender();
            contender.getFollowers().add(rating.getCitizen());
            return rating.getCitizen();
        }
        return null;
    }

    public Set<Idea> getIdeasForContender(Contender contender) {
        return contender.getIdeas();
    }

    public void addIdeasToContender(Contender contender, Idea... ideas) {
        contender.addIdeas(ideas);
    }

    public Contender addAsAContender(Citizen citizen) {
        Contender contender = new Contender(citizen);
        contenders.add(contender);
        return contender;
    }
}
