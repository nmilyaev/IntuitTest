package com.intuit.service;

import com.intuit.model.Citizen;
import com.intuit.model.Contender;
import com.intuit.model.Idea;
import com.intuit.model.IdeaRating;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Getter
public class ElectionService {
    private Set<Contender> eligibleContenders = new HashSet<>();

    @Autowired
    ContenderService contenderService;

    @Autowired
    IdeaRatingService ideaRatingService;

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private IdeaService ideaService;

    //Create/update rating
    public IdeaRating acceptRating(Citizen citizen, Idea idea, Integer rating) {
        // Check that citizen & idea exist
        if (!citizenService.isExist(citizen) || !ideaService.isExist(idea)) {
            return null;
        }
        IdeaRating ideaRating = ideaRatingService.addRating(citizen, idea, rating);
        if (ideaRating == null) {
            return null;
        }
        checkContenderStatus(ideaRating, idea.getContender());
        contenderService.checkAndAddFollower(ideaRating);
        return ideaRating;
    }

    /*
    I assume there is only 1 winner. Getting a collection of winners is also possible, but
    will be more complex. I leave this scenarion out for now.
     */
    public Contender runElection() {
        Map<Contender, Double> scoresForContenders = new HashMap<>();
        for (Contender contender : eligibleContenders) {
            Set<Idea> ideasOfContender = contender.getIdeas();
            double score = 0;
            for (Idea idea : ideasOfContender) {
                score += ideaRatingService.getAverageRating(idea);
            }
            scoresForContenders.put(contender, score);
        }
        double max = 0;
        Contender winner = null;
        for (Map.Entry<Contender, Double> entry : scoresForContenders.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                winner = entry.getKey();
            }
        }
        return winner;
    }

    /*
    If contender doesn't have any ideas which is rated less than 5 by more than 3 voters, we attempt to reinstate
    the contender;
    If contender have any ideas which is rated less than 5 by more than 3 voters, we eliminate them
     */
    private void checkContenderStatus(IdeaRating rating, Contender contender) {
        //Get ideas of contender
        Set<Idea> ideasForContender = contender.getIdeas();
        //For each idea: check if it doesn't have more than 3 votes < 5
        for (Idea idea : ideasForContender) {
            // get list of votes for the idea
            long countOfIdeasWithPoorRating = ideaRatingService.getRatingsForIdea(rating.getIdea()).stream()
                    .filter(r -> r.getRating() < 5).count();
            if (countOfIdeasWithPoorRating > 3) {
                eligibleContenders.remove(contender);
                return;
            }
        }
        eligibleContenders.add(contender);
    }
}
