package com.intuit.service;

import com.intuit.model.Citizen;
import com.intuit.model.Contender;
import com.intuit.model.Idea;
import com.intuit.model.IdeaRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
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
        if (!citizenService.isExist(citizen) || !ideaService.isExist(idea)){
            return null;
        }
        IdeaRating ideaRating = ideaRatingService.add(citizen, idea, rating);
        if (ideaRating == null) {
            return null;
        }
        checkContenderStatus(ideaRating, idea.getContender());
        contenderService.checkAndAddFollower(ideaRating);
        return ideaRating;
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
