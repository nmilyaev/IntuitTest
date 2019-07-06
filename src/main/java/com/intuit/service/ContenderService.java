package com.intuit.service;

import com.intuit.model.Citizen;
import com.intuit.model.Contender;
import com.intuit.model.Idea;
import com.intuit.model.IdeaRating;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContenderService {
    private Set<Contender> contenders = new HashSet<>();

    void checkAndAddFollower(IdeaRating rating) {
        if (rating.getRating() > 5) {
            Contender contender = rating.getIdea().getContender();
            contender.getFollowers().add(rating.getCitizen());
        }
    }

    public void addAsAContender(Citizen citizen){
        Contender contender = new Contender(citizen);
        contenders.add(contender);
    }

    public Set<Idea> getAllIdeas(){
        return contenders.stream().flatMap(c->c.getIdeas().stream()).collect(Collectors.toSet());
    }
}
